package me.codetalk.flow.fnd;

import java.util.HashSet;
import java.util.Set;

public class Constants {

    public static final String ENCODING_UTF8 = "UTF-8";

    public static final int CONST_N = 0; 		// 0 表示否 
	public static final int CONST_Y = 1;		// 1 表示是
    
	// Lookups
	public static final String LKP_POSITION_TYPE = "POSITION_TYPE";
	
	// Stat Cache
	public static final int CACHE_STAT_HASH_RANGE = 10000; // 默认ID区间
	
    // Request
 	public static final String PARAM_USER_LOGIN = "user_login";
 	public static final String PARAM_ACCESS_TOKEN = "access_token";
    
    // profile status 1. 待审核 2. 审核通过    3. 拒绝
    public static final int PROFILE_STATUS_NOTVERIFED = 1;
    public static final int PROFILE_STATUS_VERIFED = 2;
    public static final int PROFILE_STATUS_REJECT = 3;
    
    // Source Type
    public static final String SOURCE_TYPE_WEB = "Web";
    public static final String SOURCE_TYPE_IOS = "iOS";
    public static final String SOURCE_TYPE_ANDROID = "Android";
    
    public static final Set<String> SOURCE_TYPE_SET = new HashSet<String>();
    static {
    	SOURCE_TYPE_SET.add(SOURCE_TYPE_WEB);
    	SOURCE_TYPE_SET.add(SOURCE_TYPE_IOS);
    	SOURCE_TYPE_SET.add(SOURCE_TYPE_ANDROID);
    }
    
    // Exchanges & Queues
    // exc 更新fnd user
    public static final String EXC_USER_UPDATE = "ssc-fnd-ex-user-update";

    // queue: 添加用户 - 创建Fnd User
 	public static final String QUEUE_USER_ADD_CREATE = "ssc-fnd-queue-user-add-create";
 	
 	// queue: 更新FndUser - 验视图片
 	public static final String QUEUE_USER_UPDATE_VERIFY_PROFILE = "ssc-fnd-queue-user-update-verify-profile";
    
 	// queue: 增加tag hit
 	public static final String QUEUE_TAG_INCR_HIT = "ssc-fnd-queue-tag-incr-hit";
 	
 	public static final String MSG_OLD = "old";
 	public static final String MSG_NEW = "new";
 	
    // App & Module
    public static final String SSC_APP = "ssc";
    public static final String SSC_MOD_FND = "ssc-fnd";
    
    // Response 
    public static final int CODE_SUCCESS = 1;
    public static final int CODE_ERROR = 2;
    
    public static final String RESPONSE_SUCCESS = "{\"ret_code\": 1}";
    public static final String RESPONSE_ERROR = "{\"ret_code\": 2}";
    
    public static final String RESPONSE_SUCCESS_DATA = "{\"ret_code\": 1, \"ret_data\":\"%s\"}";
    
    public static final String RESPONSE_ERROR_MSG = "{\"ret_code\":2, \"ret_msg\":\"%s\"}";
    public static final String RESPONSE_SUCCESS_MSG = "{\"ret_code\":1, \"ret_msg\":\"%s\"}";
    
    public static final String RESPONSE_ERROR_CODE_MSG = "{\"ret_code\": %d, \"ret_msg\":\"%s\"}";
    
    public static final String KEY_CODE = "ret_code";
    public static final String KEY_MSG = "ret_msg";
    public static final String KEY_DATA = "ret_data";
    
    // enums
    // 消息类型: 生产 or 消费
    public static final int MSG_TYPE_SEND = 1;	// 生产
    public static final int MSG_TYPE_RECV = 2;	// 消费
    

    
}
