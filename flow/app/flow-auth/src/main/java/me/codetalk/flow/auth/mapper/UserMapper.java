package me.codetalk.flow.auth.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import me.codetalk.flow.auth.pojo.User;

/**
 * 
 * @author guobxu
 *
 */
public interface UserMapper {

	public void insertUser(User user);
	
	public User selectUserByInput(String input);
	
	public List<User> selectUserByOr(@Param("login") String login, 
							   		 @Param("mail") String mail, 
							   		 @Param("mobile") String mobile);
	
	public User selectUserByLogin(@Param("login") String login);
	public User selectUserByMail(@Param("mail") String mail);
	public User selectUserByMobile(@Param("mobile") String login);
	
	public void updateUserPwd(@Param("userId") Integer userId, @Param("newPwd") String newPwd);
	
}
