package me.codetalk.flow.auth;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by guobxu on 18/7/2017.
 */
public class Constants {

    public static final String ENCODING_UTF8 = "UTF-8";

    public static final int CONST_N = 0; 		// 0 表示否 
	public static final int CONST_Y = 1;		// 1 表示是
    
    // Request
 	public static final String PARAM_USER_LOGIN = "user_login";
 	public static final String PARAM_ACCESS_TOKEN = "access_token";
    
    public static final String CIPHER_AES = "AES";
    public static final String CIPHER_AES_CFB_NOPADDING = "AES/CFB/NoPadding";

    public static final String IV_SPEC_KEY = "O09&YA)We3qMI5EX";
    
    // 密码变换后加密密钥
    public static final String PWD_AES_KEY = "72F3897670FE064B0C9A7BAE669F0772";

    // 统一服务密钥
    public static final String UNI_SERVICE_KEY = "C9F6D2C4BD97A9004185278B47399872";
    
    // key len = 32
    public static final int CIPHER_KEY_LEN = 32;
    
    // cipher separator 
    public static final String CIPHER_STR_SEP = "/";
    
    // remember me?
    public static final int LOGIN_REM_YES = 1;
    public static final int LOGIN_REM_NO = 0;
    
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
    // exc: 添加用户
// 	public static final String EXC_USER_ADD = "ssc-auth-ex-user-add";
 	
 	// queue: 添加用户 - 发送邮件
// 	public static final String QUEUE_USER_ADD_SENDMAIL = "ssc-auth-queue-user-add-sendmail";
    
    // App & Module
    public static final String FLOW_APP = "flow";
    public static final String FLOW_MOD_AUTH = "auth";
    
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
    
    // 用户联系方式是否已验证
    public static final int USER_CONTACT_VERIFIED_NO = 0;
    public static final int USER_CONTACT_VERIFIED_YES = 1;
    
    // 添加用户
    public static final int USER_ADD_SUCCESS = 1; 	// 成功
    public static final int USER_DUP_LOGIN = 2; 	// 失败: 重复的login
    public static final int USER_DUP_MAIL = 3; 		// 失败: 重复的邮箱
    public static final int USER_DUP_MOBILE = 4; 	// 失败: 重复的手机号码
    
    public static final int USER_ADD_DUP = 5; 		// 失败: 重复的login, 邮箱或者手机号码

}






