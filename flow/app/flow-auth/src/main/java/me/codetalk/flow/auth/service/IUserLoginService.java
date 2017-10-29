package me.codetalk.flow.auth.service;

import me.codetalk.flow.auth.pojo.UserLogin;

/**
 * 
 * 认证Cache接口
 * 
 * @author guobxu
 *
 */
public interface IUserLoginService {

	// 添加login info
	public void setUserLogin(UserLogin login);
	
	// 获取login info
	public UserLogin getUserLogin(String accessToken);
	
	// 如果返回null, 表示未登录
	public UserLogin getUserLogin(String login, String accessToken);
	
	// 是否已登录
	public boolean isLoggedIn(String userLogin, String accessToken);
	
}


