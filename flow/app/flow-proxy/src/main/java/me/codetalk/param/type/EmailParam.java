package me.codetalk.param.type;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailParam extends AbstractParam {

	static Pattern EMAIL_PTRN = Pattern.compile("^\\s*?(.+)@(.+?)\\s*$");
	
	public EmailParam(String name) {
		this(name, true);
	}
	
	public EmailParam(String name, boolean required) {
		super(name, required);
	}
	
	@Override
	public boolean isList() {
		return false;
	}
	
	@Override
	public boolean isValid(Object obj) {
		if(required && obj == null) return false;
		if(!required && obj == null) return true;
		
		if(!(obj instanceof String)) return false;
		
		String s = (String)obj;
		Matcher matcher = EMAIL_PTRN.matcher(s);

		return matcher.matches();
	}

}
