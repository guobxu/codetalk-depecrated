package me.codetalk.flow.fnd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import me.codetalk.flow.fnd.pojo.Lookup;

public interface LookupMapper {

	public List<Lookup> selectByCategory(@Param("category") String category);
	
}
