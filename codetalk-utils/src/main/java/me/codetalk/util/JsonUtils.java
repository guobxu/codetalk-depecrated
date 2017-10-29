package me.codetalk.util;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

	private static ObjectMapper MAPPER = new ObjectMapper();
	
	public static String toJson(Object obj) {
		try {
			String json = MAPPER.writeValueAsString(obj);
			
			return json;
		} catch(JsonProcessingException ex) {
			ex.printStackTrace();
			
			return null;
		}
	}
	
	public static Map<String, Object> toMap(Object obj) {
		Map<String, Object> map = MAPPER.convertValue(obj, new TypeReference<Map<String, Object>>() {});
		
		return map;
	}
	
	public static Object fromJson(String json, Class clazz) {
		try {
			Object obj = MAPPER.readValue(json, clazz);
			
			return obj;
		} catch(Exception ex) {
			ex.printStackTrace();
			
			return null;
		}
	}
	
}
