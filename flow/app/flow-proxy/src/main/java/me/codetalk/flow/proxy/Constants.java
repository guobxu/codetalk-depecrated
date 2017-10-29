package me.codetalk.flow.proxy;

/**
 * 常量 - proxy
 * @author guobxu
 *
 */
public class Constants {

	public static final int CONST_N = 0; 		// 0 表示否 
	public static final int CONST_Y = 1;		// 1 表示是
	
	// Request
	public static final String PARAM_USER_LOGIN = "user_login";
	public static final String PARAM_ACCESS_TOKEN = "access_token";
	
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
    
}
