package me.codetalk.flow.fnd.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.codetalk.flow.auth.pojo.User;
import me.codetalk.flow.auth.pojo.UserLogin;
import me.codetalk.flow.auth.service.IAuthService;
import me.codetalk.flow.fnd.Constants;
import me.codetalk.flow.fnd.exception.FndServiceException;
import me.codetalk.flow.fnd.pojo.FndUser;
import me.codetalk.flow.fnd.pojo.Lookup;
import me.codetalk.flow.fnd.pojo.Notice;
import me.codetalk.flow.fnd.pojo.TagVO;
import me.codetalk.flow.fnd.service.IFndService;
import me.codetalk.flow.fnd.service.IFndUserService;
import me.codetalk.flow.fnd.service.ILookupService;
import me.codetalk.flow.fnd.service.INoticeService;
import me.codetalk.flow.fnd.service.ITagService;
import me.codetalk.flow.service.impl.AbstractBizService;

@Service("fndService")
public class FndServiceImpl extends AbstractBizService implements IFndService {

	static final Logger LOGGER = LoggerFactory.getLogger(FndServiceImpl.class);
	
	@Autowired 
	private IAuthService authService;
	
	@Autowired
	private IFndUserService fndUserService;
	
	@Autowired
	private ITagService tagService;
	
	@Autowired
	private ILookupService lkpService;
	
	@Autowired
	private INoticeService noticeService;
	
	// URI POST
	private static final String URI_USER_UPDATE = "/ssc/fnd/user/update";
	private static final String URI_USER_SETTAG = "/ssc/fnd/user/settag";
	private static final String URI_NOTICE_READALL = "/ssc/fnd/notice/readall"; 
	
	// URI GET
	private static final String URI_USER_INFO = "/ssc/fnd/user";
	private static final String URI_TAGS_BYPOS = "/ssc/fnd/tags";
	private static final String URI_POS_TYPES = "/ssc/fnd/postypes";
	
	private static final String URI_NOTICE_NEWCOUNT = "/ssc/fnd/notice/newcount";
	private static final String URI_NOTICE_LIST = "/ssc/fnd/notice/list";
	
	@PostConstruct
	public void setUriSecured() {
		securedUriSet.addAll(Arrays.asList(new String[] {
				// POST
				URI_USER_UPDATE,
				URI_USER_SETTAG,
				URI_NOTICE_READALL,
				
				// GET
				URI_USER_INFO,
				URI_NOTICE_NEWCOUNT,
				URI_NOTICE_LIST,
		}));
	}
	
	protected String doGet0(String uri, Map<String, String[]> params) {
		if(URI_USER_INFO.equals(uri)) {
			return getUserInfo(params);
		} else if(URI_TAGS_BYPOS.equals(uri)) {
			return tags(params);
		} else if(URI_POS_TYPES.equals(uri)) {
			return posTypes();
		} else if(URI_NOTICE_NEWCOUNT.equals(uri)) {
			return newNoticeCount(params);
		} else if(URI_NOTICE_LIST.equals(uri)) {
			return listNotice(params);
		}
		
		return errorWithKey("sys_uri_notfound");
	}
	
	protected String doPost0(String uri, Map<String, Object> data) {
		if(URI_USER_UPDATE.equals(uri)) {
			return updateUser(data);
		} else if(URI_USER_SETTAG.equals(uri)) {
			return setTag(data);
		} else if(URI_NOTICE_READALL.equals(uri)) {
			return readAllNotice(data);
		}
		
		return errorWithKey("sys_uri_notfound");
	}
	
	/**
	 * Param:
	 * 参数列表：
		user_login="xx"
		access_token="xx"
	 * 
	 * @param params
	 * @return
	 */
	private String newNoticeCount(Map<String, String[]> params) {
		Integer userId = getUserLogin().getUserId();
		Integer count = noticeService.countAppUnread(userId);
		
		Map<String, Integer> data = new HashMap<String, Integer>();
		data.put("new_count", count);
		
		return successWithData(data);
	}

	/**
	 * 参数列表：
		user_login="xx"
		access_token="xx"
		begin=0 // 起止
		count=20 // 条数
	 * @param params
	 * @return
	 */
	private String listNotice(Map<String, String[]> params) {
		int begin = Integer.parseInt(params.get("begin")[0]), 
				count = Integer.parseInt(params.get("count")[0]);
		
		Integer userId = getUserLogin().getUserId();
		
		List<Notice> noticeList = noticeService.getAppNoticeList(userId, begin, count);
		
		return successWithData(noticeList);
	}
	
	/**
	 * Param:
	   {
		"user_login":"xxx"
		"access_token":"xxx"
		}
	 * @param data
	 * @return
	 */
	private String readAllNotice(Map<String, Object> data) {
		Integer userId = getUserLogin().getUserId();
		noticeService.updateAppReadByUser(userId);
		
		return Constants.RESPONSE_SUCCESS;
	}

	private String getUserInfo(Map<String, String[]> params) {
		String userLogin = params.get(Constants.PARAM_USER_LOGIN)[0],
				accessToken = params.get(Constants.PARAM_ACCESS_TOKEN)[0];
		
		String[] userLoginParam = params.get("user_login_get");
		String userLoginGet = null;
		if(userLoginParam != null) userLoginGet = userLoginParam[0];
		
		// login info
		UserLogin loginInfo = authService.getUserLogin(userLogin, accessToken); 
		if( loginInfo == null ) {
			return errorWithKey("auth_err_nologin");
		}
		
		User user = authService.getUserByLogin(userLoginGet == null ? userLogin : userLoginGet);
		if(user == null) {
			return errorWithKey("auth_err_user_missing");
		}
		
		FndUser fndUser = fndUserService.getUserById(user.getId());
		if(fndUser == null) {
			return errorWithKey("fnd_err_user_missing");
		}
		
		// rtData
		Map<String, Object> rtData = new HashMap<String, Object>();
		rtData.put("user_login", user.getLogin());
		rtData.put("user_mail", user.getMail());
		rtData.put("mail_verified", user.getMailVerified());
		rtData.put("user_status", user.getStatus());
		rtData.put("signup_date", user.getCreateDate().getTime());
		rtData.put("user_name", fndUser.getUserName());
		
		String userProfile = fndUser.getUserProfile();
		rtData.put("user_profile", userProfile == null ? "" : userProfile);
		rtData.put("profile_status", fndUser.getProfileStatus());
		
		return successWithData(rtData);
	}

	/**
	 * Params:
		{
			"user_login":xxx
			"access_token":xxx
			// 用户信息
			"user_name":"xxx" //用户名
			"user_profile":"xx" //用户头像 // 1. 待审核2. 审核通过3. 拒绝
		}
	 */
	private String updateUser(Map<String, Object> data) {
		String userLogin = data.get(Constants.PARAM_USER_LOGIN).toString(),
				accessToken = data.get(Constants.PARAM_ACCESS_TOKEN).toString();
		
		String nameParam = data.get("user_name").toString(),
				profileParam = data.get("user_profile").toString();
		
		// login info
		UserLogin loginInfo = authService.getUserLogin(userLogin, accessToken); 
		if( loginInfo == null ) {
			return errorWithKey("auth_err_nologin");
		}
		
		// update
		FndUser fndUser = new FndUser();
		fndUser.setUserId(loginInfo.getUserId());
		fndUser.setUserName(nameParam);
		fndUser.setUserProfile(profileParam);
		
		try {
			fndUserService.updateUser(fndUser);
			
			return Constants.RESPONSE_SUCCESS;
		} catch(FndServiceException ex) {
			LOGGER.error(ex.getMessage(), ex);
			
			return errorWithMsg(ex.getMessage());
		}
	}
	
	// position_type=0
	private String tags(Map<String, String[]> params) {
		Integer posType = Integer.parseInt(params.get("position_type")[0]);
		
		List<TagVO> tags = tagService.getTagsByPosType(posType);
		
		List<Map<String, Object>> rtData = new ArrayList<Map<String, Object>>();
		String groupTitle = null;
		Map<String, Object> tagGroup = null; 
		for(TagVO tag : tags) {
			if(groupTitle == null || !groupTitle.equals( tag.getGroupTitle() )) {
				if(tagGroup != null) rtData.add(tagGroup);
				
				groupTitle = tag.getGroupTitle();
				tagGroup = new HashMap<String, Object>();
				tagGroup.put("tag_group", groupTitle);
				tagGroup.put("tag_list", new ArrayList<TagVO>());
			}
			
			List<TagVO> tagList = (List<TagVO>)tagGroup.get("tag_list");
			tagList.add(tag);
		}
		
		if(tagGroup != null) rtData.add(tagGroup); // add last
		
		return successWithData(rtData);
	}
	
	/**
	 * Param:
		{
			"user_login":xxx
			"access_token":xxx
			"tag_list":[xxx] // 标签ID列表
		}
	 * 
	 * @param data
	 * @return
	 */
	private String setTag(Map<String, Object> data) {
		List<Integer> tagList = (List<Integer>)data.get("tag_list");
		
		Integer userId = getUserLogin().getUserId();
		fndUserService.setUserTags(userId, tagList);
		
		return Constants.RESPONSE_SUCCESS;
	}
	
	/**
	 * 获取职责类型
	 * 
	 * Return:
	 	{
		  "ret_code":0 //返回码
		  "ret_data":"xx" //可选，返回错误时的错误描述
		  "ret_data": [
		    {"code": "1", "value": "开发"},
		    {"code": "2", "value": "测试"},
		    // ...
		  ]
		}

	 * @return
	 */
	private String posTypes() {
		List<Lookup> lkpList = lkpService.getByCategory(Constants.LKP_POSITION_TYPE);
		
		return successWithData(lkpList);
	}
	
	
	
}






