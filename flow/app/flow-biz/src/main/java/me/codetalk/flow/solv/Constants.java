package me.codetalk.flow.solv;

public class Constants {

	public static final int CONST_NO = 0; 		// 0 表示否 
	public static final int CONST_YES = 1;		// 1 表示是
	
	// Cache
	public static final int CACHE_STAT_QUEST_RANGE = 10000; // QUEST id范围
	
	// Request
	public static final String PARAM_USER_LOGIN = "user_login";
	public static final String PARAM_ACCESS_TOKEN = "access_token";
	
	// App & Module
    public static final String SSC_APP = "ssc";
    public static final String SSC_MOD_POFO = "ssc-solv";
    
    // Exchanges & Queues
    // exc: 发布问题
// 	public static final String EXC_QUEST_PUB = "ssc-solv-ex-quest-pub";
 	
 	// exc: 发布回复
//  	public static final String EXC_REPLY_ADD = "ssc-solv-ex-reply-add";
 	
 	// queue: 发布问题 - 通知
// 	public static final String QUEUE_QUEST_PUB_NOTIF = "ssc-solv-queue-quest-pub-notif";
    
 	// queue: 发布回复 - 通知
// 	public static final String QUEUE_REPLY_ADD_NOTIF = "ssc-solv-queue-reply-add-notif";
 	
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
    
    // quest status
    public static final int QUEST_STATUS_DRAFT = 1; // 草稿
    public static final int QUEST_STATUS_PUBLISHED = 2; // 已发布
    public static final int QUEST_STATUS_FROZEN = 3; // 冻结(不允许回复)
    public static final int QUEST_STATUS_SOLVED = 4; // 已解决
	
    // vote actions
    public static final int ACTION_VOTE_UP = 1;
    public static final int ACTION_VOTE_DOWN = 2;
    public static final int ACTION_VOTE_CANCEL = 3;
    
}












