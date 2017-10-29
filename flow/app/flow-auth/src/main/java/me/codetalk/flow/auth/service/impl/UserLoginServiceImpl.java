package me.codetalk.flow.auth.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import me.codetalk.cache.service.ICacheService;
import me.codetalk.flow.auth.pojo.UserLogin;
import me.codetalk.flow.auth.service.IUserLoginService;

@Component("userLoginService")
public class UserLoginServiceImpl implements IUserLoginService {

	private static Logger LOGGER = LoggerFactory.getLogger(UserLoginServiceImpl.class);
	
	public static final String CACHE_USER_LOGIN = "USER_LOGIN";
	
	@Autowired
	private ICacheService cacheService;

	@Override
	public void setUserLogin(UserLogin login) {
		LOGGER.info("In setUserLogin... token = " + login.getAccessToken());
		
		cacheService.hSet(CACHE_USER_LOGIN, login.getAccessToken(), login);
	}

	@Override
	public UserLogin getUserLogin(String accessToken) {
		LOGGER.info("In getUserLogin... token = " + accessToken);
		
		UserLogin login = (UserLogin)cacheService.hGet(CACHE_USER_LOGIN, accessToken);
		
		return login;
	}
	
	// 如果返回null, 表示未登录
	@Override
	public UserLogin getUserLogin(String login, String accessToken) {
		UserLogin loginInfo = getUserLogin(accessToken);
		
		return loginInfo != null && loginInfo.getUserLogin().equals(login) ? loginInfo : null;
	}
	
	@Override
	public boolean isLoggedIn(String userLogin, String accessToken) {
		UserLogin loginInfo = getUserLogin(userLogin, accessToken); 
		
		return loginInfo != null;
	}
	

}
