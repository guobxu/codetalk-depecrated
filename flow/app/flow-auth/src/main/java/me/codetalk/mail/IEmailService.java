package me.codetalk.mail;

import java.util.Map;

import javax.mail.MessagingException;

public interface IEmailService {

	public void sendMail(String to, String template, Map<String, String> params) throws Exception; 
	
	public void sendResetPwd(String to, Map<String, String> params) throws Exception;
	
}
