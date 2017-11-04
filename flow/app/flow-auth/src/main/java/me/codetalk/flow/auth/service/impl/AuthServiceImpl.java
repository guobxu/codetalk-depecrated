package me.codetalk.flow.auth.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.codetalk.cache.service.ICacheService;
import me.codetalk.mail.IEmailService;
import me.codetalk.mesg.KeyedMessages;
import me.codetalk.flow.auth.Constants;
import me.codetalk.flow.auth.exception.AuthServiceException;
import me.codetalk.flow.auth.pojo.User;
import me.codetalk.flow.auth.pojo.UserLogin;
import me.codetalk.flow.auth.service.IAuthService;
import me.codetalk.flow.auth.service.ICipherService;
import me.codetalk.flow.auth.service.IUserLoginService;
import me.codetalk.flow.auth.service.IUserService;
import me.codetalk.util.JsonUtils;
import me.codetalk.util.StringUtils;


/**
 * 
 * @author guobxu
 *
 * Created: 04/14
 *
 */
@Service("authService")
public class AuthServiceImpl implements IAuthService {

	private static Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IUserLoginService loginService;
	
	@Autowired
	private ICipherService cipherService;

	@Autowired
	private KeyedMessages km;
	
	@Autowired 
	private IEmailService emailService;
	
	@Autowired
	private ICacheService cacheService;
	
	// Cache Constants
	private static final String CACHE_RESETPWD_PREFIX = "CODE-RESETPWD-";
	private static final long CACHE_RESETPWD_EXPIRE = 5 * 60; // 5分钟
	
	// URI
	static final String URI_AUTH_SIGNUP = "/flow/auth/signup";
	static final String URI_AUTH_LOGIN = "/flow/auth/login";
	static final String URI_AUTH_FORGETPWD = "/flow/auth/forgetpwd";
	static final String URI_AUTH_RESETPWD = "/flow/auth/resetpwd";
	
	@Transactional
	public String doPost(String uri, Map<String, Object> data) {
		if(URI_AUTH_SIGNUP.equals(uri)) {
			return signup(data);
		} else if(URI_AUTH_LOGIN.equals(uri)) {
			return login(data);
		} else if(URI_AUTH_FORGETPWD.equals(uri)) {
			return forgetPwd(data);
		} else if(URI_AUTH_RESETPWD.equals(uri)) {
			return resetPwd(data);
		}
		
		return errorWithKey("sys_uri_notfound");
	}
	
	@Override
	public boolean isLoggedIn(String userLogin, String accessToken) {
		UserLogin loginInfo = loginService.getUserLogin(userLogin, accessToken); 
		
		return loginInfo != null;
	}
	
	@Override
	public UserLogin getUserLogin(String login, String accessToken) {
		return loginService.getUserLogin(login, accessToken);
	}
	
	@Override
	public User getUserByLogin(String login) {
		return userService.getUserByLogin(login);
	}
	
	@Override
	public Integer getUserIdByLogin(String userLogin) {
		return userService.getUserIdByLogin(userLogin);
	}
	
	/**
	 {
	   "login_name": "xx" // 登录名
	   "user_mail": "xx" // 邮箱
	   "passwd_str": "xx" // 用户密码
	   "position_type": 0 // 职责类型
	 }
	 */
	private String signup(Map<String, Object> data) {
		LOGGER.info("In signup... Params = " + data);
		
		String loginParam = data.get("login_name").toString(),
				mailParam = data.get("user_mail").toString(), 
				pwdParam = data.get("passwd_str").toString();
		
		// 添加用户
		User user = new User();
		user.setLogin(loginParam);
		user.setMail(mailParam);
		
		Map<String, String> attrs = new HashMap<String, String>();
		attrs.put("position_type", data.get("position_type").toString());
		user.setAttrs(attrs);
		
		try {
			String realPwd = cipherService.decipher(pwdParam, Constants.PWD_AES_KEY);
			user.setPasswd(realPwd);
		} catch(Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			
			return errorWithKey("auth_err_cipher");
		}
		
		try {
			userService.addUser(user);
			
			return Constants.RESPONSE_SUCCESS;
		} catch(AuthServiceException ex) {
			LOGGER.error(ex.getMessage(), ex);
			
			return errorWithMsg(ex.getMessage());
		}
	}

	/**
	 Param:
	 {
	   "user_name": "xx" // 用户名
	   "login_auth_str": "xx" // 认证登录密文
	   "remember_me": 0 // 可选，是否保持登录 0 否 1 是 仅用于Web端
	 }
	 
	 Return:
	 {
		"ret_code":0 //返回码
	  	"ret_msg":"xx" //可选，返回错误时的错误描述
	  	"ret_data": //响应数据
	  	{
	    	"user_login":"xxx" //用户登录名
	    	"auth_ret_str":"xx" //认证结果密文串
	  	}
	 }
	 */
	private String login(Map<String, Object> data) {
		LOGGER.info("In login... Params = " + data);
		
		String userNameParam = data.get("user_name").toString(),
				authStrParam = data.get("login_auth_str").toString(); // 32 Client Key + src type / user name
		
		Object remObj = data.get("remember_me");
		boolean rememberMe = ( remObj == null ? false : Constants.LOGIN_REM_YES == Integer.parseInt(remObj.toString()) ? true : false ); 

		User user = userService.getUserByInput(userNameParam);
		if(user == null) {
			return errorWithKey("auth_err_usernotfound");
		}
		
		String clearText = null;
		try {
			clearText = cipherService.decipher(authStrParam, user.getPasswd());
		} catch(Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			
			return errorWithKey("auth_err_cipher");
		}
				
		// parse
		List<String> strList = parseLoginAuthClear(clearText);
		if(strList == null || strList.size() != 3) {
			return errorWithKey("auth_err_authstr");
		}
		
		String clientKey = strList.get(0), srcType = strList.get(1), userName = strList.get(2);
		if( !userNameParam.equals(userName) || !Constants.SOURCE_TYPE_SET.contains(srcType) ) {
			return errorWithKey("auth_err_authstr");
		}
		
		// TODO: remember me logic
		
		UserLogin login = new UserLogin();
		login.setAccessToken(StringUtils.uuid());
		login.setUserId(user.getId());
		login.setUserLogin(user.getLogin());
		login.setSrcType(srcType);
		login.setCreateDate(System.currentTimeMillis());
		
		loginService.setUserLogin(login);
		
		// 认证结果串： access token + user name param
		String authRetStr = null;
		try {
			authRetStr = cipherService.cipher(login.getAccessToken() + userNameParam, clientKey);
		} catch(Exception ex) {
			
		}
		Map<String, Object> retData = new HashMap<String, Object>();
		retData.put("user_login", user.getLogin());
		retData.put("auth_ret_str", authRetStr);
		
		return successWithData(retData);
	}

	private List<String> parseLoginAuthClear(String clearText) {
		if(clearText.length() <= Constants.CIPHER_KEY_LEN) return null;
		
		List<String> strList = new ArrayList<String>();
		strList.add(clearText.substring(0, Constants.CIPHER_KEY_LEN));
		
		String suffix = clearText.substring(Constants.CIPHER_KEY_LEN);
		String[] suffArr = suffix.split(Constants.CIPHER_STR_SEP);
		for(String suff : suffArr) {
			strList.add(suff);
		}
		
		return strList;
	}
	
	/**
	 * Param:
	 	{
		  "user_mail":"xx" //用户邮箱
		}
	 *
	 * Return: 
  	    {
		  "ret_code":0 //返回码
		  "ret_msg":"xx" //可选，返回错误时的错误描述
		}
	 * @param data
	 * @return
	 */
	private String forgetPwd(Map<String, Object> data) {
		String mail = data.get("user_mail").toString();
		
		User user = userService.getUserByMail(mail);
		if(user == null) {
			return errorWithKey("auth_err_usernotfound");
		}
		
		// 生成验证码
		String verifyCode = StringUtils.randomNum8();
		cacheService.set(CACHE_RESETPWD_PREFIX + user.getId(), verifyCode, CACHE_RESETPWD_EXPIRE);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("verifyCode", verifyCode);
		try {
			emailService.sendResetPwd(mail, params);
		} catch(Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			
			return errorWithKey("auth_err_sendmail");
		}
		
		return Constants.RESPONSE_SUCCESS;
	}

	/**
	 * Param:
		{
		  "user_mail":"xx" //邮箱
		  "new_passwd":"xx" //新密码
		  "verify_code": "xxx" //验证码
		}

	 * Return:
		{
		  "ret_code":0 //返回码
		  "ret_msg":"xx" //可选，返回错误时的错误描述
		}
	 * @param data
	 * @return
	 */
	private String resetPwd(Map<String, Object> data) {
		String mailParam = data.get("user_mail").toString(), 
				newPwdParam = data.get("new_pwd").toString(),
				verifyCodeParam = data.get("verify_code").toString();
		
		User user = userService.getUserByMail(mailParam);
		if(user == null) {
			return errorWithKey("auth_err_usernotfound");
		}
		
		// 对比验证码
		String cacheKey = CACHE_RESETPWD_PREFIX + user.getId();
		String verifyCode = (String)cacheService.get(cacheKey);
		if(!verifyCodeParam.equals(verifyCode)) {
			return errorWithKey("auth_err_verifycode");
		}
		
		// 密码
		String newPwd = null;
		try {
			newPwd = cipherService.decipher(newPwdParam, Constants.PWD_AES_KEY);
		} catch(Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			
			return errorWithKey("auth_err_cipher");
		}
		
		// 更新密码
		userService.updateUserPwd(user.getId(), newPwd);
		
		// 删除验证码
		cacheService.delete(cacheKey);
		
		return Constants.RESPONSE_SUCCESS;
	}
	
	public String errorWithMsg(String msg) {
		return String.format(Constants.RESPONSE_ERROR_MSG, msg);
	}
	
	public String errorWithKey(String messageKey) {
		return String.format(Constants.RESPONSE_ERROR_MSG, km.get(messageKey));
	}
    
    public String errorWithCodeKey(int errcode, String key) {
		return String.format(Constants.RESPONSE_ERROR_CODE_MSG, errcode, km.get(key));
	}
    
    public String successWithData(Object data) {
    	Map<String, Object> rtObj = new HashMap<String, Object>();
    	rtObj.put(Constants.KEY_CODE, Constants.CODE_SUCCESS);
    	rtObj.put(Constants.KEY_DATA, data);
    	
    	return JsonUtils.toJson(rtObj);
    }

}

























