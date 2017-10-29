package me.codetalk.param.checker;

import java.util.Map;

public interface IParamChecker {

	/**
	 * 检查POST请求参数
	 * @param uri
	 * @param params
	 * @return
	 */
	public ParamCheckResult checkPost(String uri, Map<String, Object> params);
	
	/**
	 * 检查GET请求参数
	 * @param uri
	 * @param params
	 * @return
	 */
	public ParamCheckResult checkGet(String uri, Map<String, String[]> params);
	
}
