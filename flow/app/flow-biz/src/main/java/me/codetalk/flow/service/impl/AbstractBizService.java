package me.codetalk.flow.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import me.codetalk.mesg.KeyedMessages;
import me.codetalk.flow.auth.pojo.UserLogin;
import me.codetalk.flow.auth.service.IAuthService;
import me.codetalk.flow.auth.support.UserLoginSupport;
import me.codetalk.flow.pofo.Constants;
import me.codetalk.flow.service.IBizService;
import me.codetalk.util.JsonUtils;
import me.codetalk.util.StringUtils;

public abstract class AbstractBizService implements IBizService {

	@Autowired
	private KeyedMessages km;
	
	@Autowired
	private IAuthService authService;
	
	@Autowired
	private UserLoginSupport ulSupport;
	
	// 需要验证身份的URI集合
	protected Set<String> securedUriSet = new HashSet<String>();
	
	// doPost0
	protected abstract String doPost0(String uri, Map<String, Object> data);
	
	// doGet0
	protected abstract String doGet0(String uri, Map<String, String[]> params);
	
	@Transactional
	public String doPost(String uri, Map<String, Object> data) {
		if( isUriSecured(uri) ) {
			UserLogin userLogin = requestAuth((String)data.get(Constants.PARAM_USER_LOGIN), 
					(String)data.get(Constants.PARAM_ACCESS_TOKEN));
			
			if(userLogin == null) {
				return errorWithKey("auth_err_nologin");
			} else {
				ulSupport.setUserLogin(userLogin);
			}
		}
		
		return doPost0(uri, data);
	}
	
	public String doGet(String uri, Map<String, String[]> params) {
		if( isUriSecured(uri) ) {
			String[] userLoginParam = params.get(Constants.PARAM_USER_LOGIN),
					accessTokenParam = params.get(Constants.PARAM_ACCESS_TOKEN);
			if(userLoginParam == null || accessTokenParam == null) {
				return errorWithKey("auth_err_nologin");
			}
			
			UserLogin userLogin = requestAuth(userLoginParam[0], accessTokenParam[0]);
			if(userLogin == null) {
				return errorWithKey("auth_err_nologin");
			} else {
				ulSupport.setUserLogin(userLogin);
			}
		}
		
		return doGet0(uri, params);
	}
	
	public boolean isUriSecured(String uri) {
		return securedUriSet.contains(uri);
	}
	
	private UserLogin requestAuth(String login, String accessToken) {
		if(StringUtils.isNull(login) || StringUtils.isNull(accessToken)) return null;
		
		return authService.getUserLogin(login, accessToken);
	}
	
	public String errorWithKey(String messageKey) {
		return String.format(Constants.RESPONSE_ERROR_MSG, km.get(messageKey));
	}
    
	protected UserLogin getUserLogin() {
		return ulSupport.getUserLogin();
	}
	
	public String errorWithMsg(String msg) {
		return String.format(Constants.RESPONSE_ERROR_MSG, msg);
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
