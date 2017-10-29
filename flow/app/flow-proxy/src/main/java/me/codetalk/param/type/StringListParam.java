package me.codetalk.param.type;

import java.util.List;

public class StringListParam extends AbstractListParam {

	public StringListParam(String name, boolean required) {
		super(name, required);
	}
	
	public StringListParam(String name, boolean required, boolean allowEmpty) {
		super(name, required, allowEmpty);
	}

	public boolean isValid(Object obj) {
		if(!super.isValid(obj)) return false;

		if(obj == null) return true;
		
		List<Object> objList = (List<Object>) obj;
		for (Object o : objList) {
			if (o instanceof String) {
				continue;
			}
			return false;
		}

		return true;
	}
	
}
