package me.codetalk.flow.proxy;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import me.codetalk.mesg.KeyedMessages;
import me.codetalk.param.checker.IParamChecker;
import me.codetalk.util.JsonUtils;

/**
 * 
 * @author guobxu
 *
 */
public abstract class BaseProxy {

	@Autowired
	protected KeyedMessages km;

	@Autowired
	protected IParamChecker paramChecker;
	
	protected String errorWithKey(String key) {
		return String.format(Constants.RESPONSE_ERROR_MSG, km.get(key));
	}
	
	public String errorWithCodeKey(int errcode, String key) {
		return String.format(Constants.RESPONSE_ERROR_CODE_MSG, errcode, km.get(key));
	}
	
	protected String errorWithMsg(String errMsg) {
		return String.format(Constants.RESPONSE_ERROR_MSG, errMsg);
	}
	
	public String successWithData(Object data) {
    	Map<String, Object> rtObj = new HashMap<String, Object>();
    	rtObj.put(Constants.KEY_CODE, Constants.CODE_SUCCESS);
    	rtObj.put(Constants.KEY_DATA, data);
    	
    	return JsonUtils.toJson(rtObj);
    }
	
}
