package me.codetalk.flow.fnd.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.codetalk.mesg.KeyedMessages;
import me.codetalk.messaging.Message;
import me.codetalk.messaging.kafka.aspect.annotation.KafkaAfter;
import me.codetalk.flow.fnd.Constants;
import me.codetalk.flow.fnd.exception.FndServiceException;
import me.codetalk.flow.fnd.mapper.FndUserMapper;
import me.codetalk.flow.fnd.mapper.UserTagMapper;
import me.codetalk.flow.fnd.pojo.FndUser;
import me.codetalk.flow.fnd.service.IFndUserService;
import me.codetalk.util.JsonUtils;
import me.codetalk.util.StringUtils;

@Service("fndUserService")
public class FndUserServiceImpl implements IFndUserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FndUserServiceImpl.class);
	
	@Autowired
	private FndUserMapper userMapper;

	@Autowired
	private UserTagMapper userTagMapper;
	
	@Autowired
	private KeyedMessages km;
	
	@Override
	public void addUser(FndUser user) {
		userMapper.insertUser(user);
	}

	@Override
	public FndUser getUserById(Integer userId) {
		return userMapper.selectUserById(userId);
	}

	@Transactional
	@KafkaAfter(value = "ssc-user-update", app = "ssc", module = "fnd")
	public FndUser updateUser(FndUser user) throws FndServiceException {
		FndUser userInDb = userMapper.selectUserById(user.getUserId());
		if(userInDb == null) {
			throw new FndServiceException(km.get("fnd_err_user_missing"));
		}
		
		String newProfile = user.getUserProfile();
		if(StringUtils.isNotNull(newProfile) && !newProfile.equals(userInDb.getUserProfile())) {
			user.setProfileStatus(Constants.PROFILE_STATUS_NOTVERIFED);
		} else {
			user.setProfileStatus(userInDb.getProfileStatus());
		}
		
		userMapper.updateUser(user);
		
		return user;
	}
	
	@Override
	public void setUserTags(Integer userId, List<Integer> tagList) {
		userTagMapper.deleteUserTags(userId);
		userTagMapper.insertUserTags(userId, tagList);
	}
	
	// Kakfa listeners
	
	// 添加用户, 创建FND用户记录
	@KafkaListener(topics = "ssc-user-signup", groupId = "ssc-user-signup-fnd-creation-group")
    public void msgUserAdd_create(String msgstr) {
		LOGGER.info("In msgUserAdd_create...Receive mesg data = " + msgstr);
		
		Message message = (Message)JsonUtils.fromJson(msgstr, Message.class);
		Map<String, Object> data = (Map<String, Object>)message.getData();
		
		FndUser user = new FndUser();
		user.setUserId(Integer.parseInt(data.get("id").toString()));
		
		String userLogin = data.get("login").toString();
		user.setUserLogin(userLogin);
		user.setUserName(userLogin);
		
		Map<String, String> attrs = (Map<String, String>)data.get("attrs");
		user.setPositionType(Integer.parseInt(attrs.get("position_type")));
		
		user.setProfileStatus(Constants.PROFILE_STATUS_VERIFED);
		
		addUser(user);
    }
	
}
