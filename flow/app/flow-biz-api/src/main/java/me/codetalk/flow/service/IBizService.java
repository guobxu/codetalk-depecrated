package me.codetalk.flow.service;

import java.util.Map;

public interface IBizService {

	/************************ HTTP ************************/
	
	public String doPost(String uri, Map<String, Object> data);
	
	public String doGet(String uri, Map<String, String[]> params);
	
}
