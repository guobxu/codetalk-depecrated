package me.codetalk.util;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * Created by guobxu on 17/7/2017.
 */
public final class StringUtils {

	private static SecureRandom rand = new SecureRandom();
	
	protected static final char[] HEX_ARRAY= {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	
	
    // 如果null 或 为空, 则返回true
    public static boolean isNull(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotNull(String str) {
        return str != null && str.length() > 0;
    }

    public static String toString(Object obj, boolean nullAsEmpty) {
        return obj == null ?
                (nullAsEmpty ? "" : null) : obj.toString();
    }

    public static String replaceNoRegex(String str, String target, String replacement) {
        int i = str.indexOf(target);
        if(i == -1) return str;

        return str.substring(0, i) + replacement + str.substring(i + target.length());
    }
    
    public static String uuid() {
    	return UUID.randomUUID().toString();
    }
    
    public String randomKey32() {
		byte[] bytes = new byte[16];
		rand.nextBytes(bytes);
		
		return bytesToHex(bytes);
	}
    
    private String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = HEX_ARRAY[v >>> 4];
			hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
		}
		
		return new String(hexChars);
	}
    
    public static String randomNum(int len) {
    	int min = (int)Math.pow(10, len - 1), max = min * 10 -1;
    	
    	Random r = new Random();
		int rand = r.nextInt((max - min) + 1) + min;
		
		return String.valueOf(rand);
    }
    
    /**
     * 随机数字 长度6
     * 
     * @return
     */
    public static String randomNum6() {
		return randomNum(6);
	}
    
    /**
     * 随机数字 长度8
     * 
     * @return
     */
    public static String randomNum8() {
    	return randomNum(8);
	}
    
}