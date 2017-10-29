package me.codetalk.flow.auth.service;

import java.util.Map;

import me.codetalk.flow.auth.pojo.User;
import me.codetalk.flow.auth.pojo.UserLogin;

/**
 * 
 * @author guobxu
 * 
 */
public interface IAuthService {

	/************************ HTTP ************************/
	
	public String doPost(String uri, Map<String, Object> data);
	
	/************************ RPC  ************************/
	
	public boolean isLoggedIn(String userLogin, String accessToken);
	
	public UserLogin getUserLogin(String login, String accessToken);

	public User getUserByLogin(String login);
	
	// 根据login 获取用户ID, -1表示未找到该用户
	public Integer getUserIdByLogin(String userLogin);
	
}




