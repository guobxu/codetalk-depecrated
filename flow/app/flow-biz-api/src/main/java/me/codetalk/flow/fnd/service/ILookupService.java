package me.codetalk.flow.fnd.service;

import java.util.List;

import me.codetalk.flow.fnd.pojo.Lookup;

public interface ILookupService {

	public List<Lookup> getByCategory(String category);
	
}
