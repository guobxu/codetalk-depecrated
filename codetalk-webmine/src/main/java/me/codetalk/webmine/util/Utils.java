package me.codetalk.webmine.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import me.codetalk.webmine.Constants;

public final class Utils {

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
	
	public static Document getDocument(String url) throws IOException {
		HttpConnectionManager cm = new MultiThreadedHttpConnectionManager(); 
		HttpClient httpClient = new HttpClient(cm); 
		httpClient.getParams().setParameter( HttpMethodParams.USER_AGENT, Constants.HTTP_USER_AGENT);

		GetMethod getMethod = new GetMethod(url);
		httpClient.executeMethod(getMethod);
		
		try( InputStream in = getMethod.getResponseBodyAsStream() ) {
			String html = IOUtils.toString(in);
//			LOGGER.info("Html returned: " + html);
			
        	Document doc = Jsoup.parse(html);
        	doc.setBaseUri(Utils.extractUrl(url)[0]);
        	getMethod.releaseConnection();
        	
        	return doc;
        }
	}
	
}



















