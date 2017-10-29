package me.codetalk.flow.fnd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.codetalk.flow.fnd.mapper.LookupMapper;
import me.codetalk.flow.fnd.pojo.Lookup;
import me.codetalk.flow.fnd.service.ILookupService;

@Service("lookupService")
public class LookupServiceImpl implements ILookupService {

	@Autowired
	private LookupMapper lkpMapper;
	
	@Override
	public List<Lookup> getByCategory(String category) {
		return lkpMapper.selectByCategory(category);
	}

}
