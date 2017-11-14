package me.codetalk.webmine.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import me.codetalk.webmine.Constants;

public final class StringUtils {

	public static String[] extractUrl(String url) {
		String[] rt = new String[2];
		
		int pos = -1;
		if(url.startsWith(Constants.PROTO_HTTP)) {
			pos = url.indexOf("/", Constants.PROTO_HTTP.length());
		} else if(url.startsWith(Constants.PROTO_HTTPS)) {
			pos = url.indexOf("/", Constants.PROTO_HTTPS.length());
		} else {
			pos = url.indexOf("/");
		}
		
		rt[0] = url.substring(0, pos);
		rt[1] = url.substring(pos);
		
		return rt;
	}
	
}



















