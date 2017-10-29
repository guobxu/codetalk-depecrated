package me.codetalk.flow.pofo.mapper;

import java.util.List;

import me.codetalk.flow.pofo.pojo.PostImg;

public interface PostImgMapper {

	public void insertImg(PostImg img);
	
	public void insertImgList(List<PostImg> imgList);
	
}
