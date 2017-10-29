package me.codetalk.param.type;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractListParam extends AbstractParam {

	private boolean allowEmpty = false;
	
	public AbstractListParam(String name, boolean required) {
		this(name, required, false);
	}
	
	public AbstractListParam(String name, boolean required, boolean allowEmpty) {
		super(name, required);
		
		this.allowEmpty = allowEmpty;
	}

	@Override
	public boolean isList() {
		return true;
	}
	
	public boolean isValid(Object obj) {
		if (required && obj == null)
			return false;
		if (!required && obj == null)
			return true;

		if (!(obj instanceof ArrayList))
			return false;
		
		List<Object> objList = (List<Object>) obj;
		
		if(!allowEmpty && objList.size() == 0) return false;

		return true;
	}
	
}

