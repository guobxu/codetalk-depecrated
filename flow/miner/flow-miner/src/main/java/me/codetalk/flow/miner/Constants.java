package me.codetalk.flow.miner;

public final class Constants {

	// topic prefix
	public static final String KAFKA_TOPIC_PREFIX = "webminer-";
	
	// page status
	public static final int PAGE_STATUS_NOT_FETCHED = 1; 	// 未抓取
	public static final int PAGE_STATUS_FETCHED = 2; 		// 未抓取
	public static final int PAGE_STATUS_ERR = 3; 			// 抓取出错
	
	// list type
	public static final int LIST_TYPE_HTML = 1;				// html - jsoup
	public static final int LIST_TYPE_JSON = 2;				// json
	public static final int LIST_TYPE_HTML_HTTPCLIENT = 3; 	// html - http client
	public static final int LIST_TYPE_JSON_HTTPCLIENT = 4; 	// json - http client
	
	
	
	
	
}
