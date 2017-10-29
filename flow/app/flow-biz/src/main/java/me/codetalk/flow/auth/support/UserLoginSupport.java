package me.codetalk.flow.auth.support;

import org.springframework.stereotype.Component;

import me.codetalk.flow.auth.pojo.UserLogin;

@Component("userLoginSupport")
public class UserLoginSupport {

	// 当前登录用户
	private ThreadLocal<UserLogin> userLocal = new ThreadLocal<UserLogin>();
		
	public void setUserLogin(UserLogin userLogin) {
		userLocal.set(userLogin);
	}
	
	// 返回当前登录用户
	public UserLogin getUserLogin() {
		return userLocal.get();
	}	
	
}
