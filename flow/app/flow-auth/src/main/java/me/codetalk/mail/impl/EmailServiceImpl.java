package me.codetalk.mail.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import me.codetalk.mail.IEmailService;
import me.codetalk.util.StringUtils;

@Component
public class EmailServiceImpl implements IEmailService {

	static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);
	
	static final String MAIL_TMPL_DIR = "mailconfig/";
	
	private Map<String, Properties> DATA_TMPL = new HashMap<String, Properties>();
	
	// content type
	private static final String CONTENT_TYPE_PLAIN = "plain";
	private static final String CONTENT_TYPE_HTML = "html";
	
	// mail templates
	private static final String TMPL_RESET_PWD = "resetpwd";
	
	@Autowired 
	private JavaMailSender sender;
	
	public void sendSimpleMessage(String from, String to, String subject, String content) throws Exception {
        MimeMessage mail = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, false);
        helper.setTo(to);
        helper.setFrom(from);
        helper.setSubject(subject);
        helper.setText(content);
        
        sender.send(mail);
    }

	@Override
	public void sendMail(String to, String template, Map<String, String> params) throws Exception {
		Properties props = DATA_TMPL.get(template);
		if(props == null) {
			props = loadProperties(template);
		}
		
		// send
		MimeMessage mail = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, false);
        helper.setFrom(props.getProperty("from"));
        helper.setTo(to);
        helper.setSubject(props.getProperty("subject"));
        
        String contentType = props.getProperty("contentType"), content = props.getProperty("content");
        String formatted = formatContent(content, params);
        if(CONTENT_TYPE_HTML.equals(contentType)) {
        	helper.setText(formatted, true);
        } else {
        	helper.setText(formatted);
        }
        
        sender.send(mail);
	}
	
	@Override
	public void sendResetPwd(String to, Map<String, String> params) throws Exception {
		sendMail(to, TMPL_RESET_PWD, params);
	}
	
	private Properties loadProperties(String tmpl) throws IOException {
		Properties props = new Properties();
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(
				this.getClass().getClassLoader().getResourceAsStream(MAIL_TMPL_DIR + tmpl + ".properties")))) {
			props.load(reader);
		}
		
		return props;
	}
	
	private String formatContent(String content, Map<String, String> params) {
		
		for(Map.Entry<String, String> entry : params.entrySet()) {
			content = StringUtils.replaceNoRegex(content, "{" + entry.getKey() + "}", entry.getValue());
		}
		
		return content;
		
	}

}




