package me.codetalk.flow.auth.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.codetalk.mesg.KeyedMessages;
import me.codetalk.messaging.kafka.aspect.annotation.KafkaAfter;
import me.codetalk.flow.auth.exception.AuthServiceException;
import me.codetalk.flow.auth.mapper.UserMapper;
import me.codetalk.flow.auth.pojo.User;
import me.codetalk.flow.auth.service.IUserLoginService;
import me.codetalk.flow.auth.service.IUserService;

/**
 * 
 * @author guobxu
 *
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private IUserLoginService authCache;
	
	@Autowired
	private KeyedMessages km;
	
	@Transactional
	@KafkaAfter(value = "flow-user-signup", app = "flow", module = "auth")
	public User addUser(User user) throws AuthServiceException {
		String login = user.getLogin(), mail = user.getMail(), mobile = user.getMobile(); 
		
		List<User> userList = userMapper.selectUserByOr(
				login == null ?  null : login.toLowerCase(), 
				mail == null ? null : mail.toLowerCase(), 
				mobile == null ? null : mobile.toLowerCase());
		
		if(userList.size() > 0) {
			throw new AuthServiceException(km.get("user_dup_lmm"));
		} else {
			if(login != null) user.setLoginLower(login.toLowerCase());
			if(mail != null) user.setMailLower(mail.toLowerCase());
			
			userMapper.insertUser(user);
			
			return user;
		}
		
	}

	// 根据用户登录名(login / 邮箱 / 手机)查询用户
	public User getUserByInput(String input) {
		String lower = input.toLowerCase();
		User user = userMapper.selectUserByInput(lower);
		
		return user;
	}

	@Override
	public User getUserByLogin(String login) {
		return userMapper.selectUserByLogin(login.toLowerCase());
	}

	@Override
	public User getUserByMobile(String mobile) {
		return userMapper.selectUserByMobile(mobile);
	}

	@Override
	public User getUserByMail(String mail) {
		return userMapper.selectUserByMail(mail.toLowerCase());
	}
	
	// 根据login 获取用户ID, -1表示未找到该用户
	@Override
	public Integer getUserIdByLogin(String userLogin) {
		User user = getUserByLogin(userLogin);
		
		return user == null ? -1 : user.getId();
	}

	@Override
	public void updateUserPwd(Integer userId, String newPwd) {
		userMapper.updateUserPwd(userId, newPwd);
	}
	
	// MQ listeners
//	@RabbitListener(queues = Constants.QUEUE_USER_ADD_SENDMAIL)
//    public void msgUserAdd_sendmail(Message message) {
//		LOGGER.info("In userSendMail...Receive mesg data = " + message.getData());
//    }
	
	// TODO kakfa listeners

}

















