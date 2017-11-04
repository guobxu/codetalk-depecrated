package me.codetalk.flow.pofo;

public class Constants {

	public static final int CONST_N = 0; 		// 0 表示否 
	public static final int CONST_Y = 1;		// 1 表示是
	
	// Stat Cache
	public static final int CACHE_STAT_POST_RANGE = 10000; // POST id范围
	
	// Request
	public static final String PARAM_USER_LOGIN = "user_login";
	public static final String PARAM_ACCESS_TOKEN = "access_token";
	
	// Post Types
	// 帖子类型    1、帖子（原创）    2、转帖
	public static final int POST_TYPE_NEW = 1;
	public static final int POST_TYPE_REFER = 2;
	
	// App & Module
    public static final String FLOW_APP = "flow";
    public static final String FLOW_MOD_POFO = "flow-pofo";
    
    // Exchanges & Queues
    // exc: 发表帖子
// 	public static final String EXC_POST_ADD = "ssc-pofo-ex-post-add";
 	
    // exc: 提交评论
//  	public static final String EXC_COMMENT_ADD = "ssc-pofo-ex-cmnt-add";
 	
 	// queue: 发表帖子 - 生成hashtag
// 	public static final String QUEUE_POST_ADD_HASHTAG = "ssc-pofo-queue-post-add-hashtag";
 	// queue: 发表帖子 - 发送通知
// 	public static final String QUEUE_POST_ADD_NOTIF = "ssc-pofo-queue-post-add-notif";
    
 	// queue: 发表评论 - 发送通知
// 	public static final String QUEUE_CMNT_ADD_NOTIF = "ssc-pofo-queue-cmnt-add-notif";
 	
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
    
    // follow actions
    public static final int ACTION_USER_FOLLOW = 1; // 关注
    public static final int ACTION_USER_UNFOLLOW = 2; // 取消关注
    
    // like actions
    public static final int ACTION_POST_LIKE = 1; // 点赞
    public static final int ACTION_POST_UNLIKE = 2; // 取消
	
}
