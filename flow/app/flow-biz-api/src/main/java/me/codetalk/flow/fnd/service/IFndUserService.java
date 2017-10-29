package me.codetalk.flow.fnd.service;

import java.util.List;

import me.codetalk.flow.fnd.exception.FndServiceException;
import me.codetalk.flow.fnd.pojo.FndUser;

public interface IFndUserService {

	public void addUser(FndUser user);
	
	public FndUser getUserById(Integer userId);
	
	public FndUser updateUser(FndUser user) throws FndServiceException;
	
	public void setUserTags(Integer userId, List<Integer> tagList);
	
}
