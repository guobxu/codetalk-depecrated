package me.codetalk.flow.pofo.service;

import java.util.List;

import me.codetalk.flow.pofo.exception.PofoServiceException;
import me.codetalk.flow.pofo.pojo.Comment;
import me.codetalk.flow.pofo.pojo.CommentVO;

public interface ICommentService {

	public void addPostComment(Comment cmnt);
	
	public void addThreadComment(Comment cmnt) throws PofoServiceException;
	
	public Comment getComment(Long cmntId);
	
	public List<CommentVO> listCommentByPost(Long postId, int begin, int count);
	
	public List<CommentVO> listCommentByThread(String thread, int begin, int count);
	
}
