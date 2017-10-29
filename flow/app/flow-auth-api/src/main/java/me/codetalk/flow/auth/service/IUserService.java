package me.codetalk.flow.auth.service;

import me.codetalk.flow.auth.exception.AuthServiceException;
import me.codetalk.flow.auth.pojo.User;

/**
 * 
 * @author guobxu
 *
 */
public interface IUserService {

	/**
	 * 添加用户
	 * @param user
	 * @return 添加成功后的User对象
	 */
	public User addUser(User user) throws AuthServiceException;
	
	public User getUserByInput(String input);
	
	public User getUserByLogin(String login);
	public User getUserByMobile(String mobile);
	public User getUserByMail(String mail);
	
	// 根据login 获取用户ID, -1表示未找到该用户
	public Integer getUserIdByLogin(String userLogin);
	
	public void updateUserPwd(Integer userId, String newPwd);
	
}
