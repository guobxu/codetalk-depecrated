package me.codetalk.flow.pofo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.codetalk.mesg.KeyedMessages;
import me.codetalk.flow.pofo.exception.PofoServiceException;
import me.codetalk.flow.pofo.mapper.CommentMapper;
import me.codetalk.flow.pofo.pojo.Comment;
import me.codetalk.flow.pofo.pojo.CommentVO;
import me.codetalk.flow.pofo.pojo.Post;
import me.codetalk.flow.pofo.service.ICommentService;
import me.codetalk.flow.pofo.service.IPostService;
import me.codetalk.util.StringUtils;

@Service("commentService")
public class CommentServiceImpl implements ICommentService {

	@Autowired
	private CommentMapper cmntMapper;
	
	@Autowired
	private IPostService postService;
	
	@Autowired
	private KeyedMessages km;
	
	@Transactional
	public void addPostComment(Comment cmnt) {
		Long postId = cmnt.getPostId();
//		assert cmnt != null && postId != null && cmnt.getCommentReply() == null;
		
		// post exists
//		Post post = postService.getPostById(cmnt.getPostId());
//		if(post == null) {
//			throw new PofoServiceException(km.get("pofo_err_postnotfound"));
//		}
		
		// thread
		cmnt.setThread(StringUtils.uuid());
		
		int rows = cmntMapper.insertComment(cmnt);
		if(rows > 0) {
			postService.incrComment(postId);
		}
		
		// TODO msg
		
	}
	
	@Transactional
	public void addThreadComment(Comment cmnt) throws PofoServiceException {
		Long cmntReplyId = cmnt.getCommentReply();
//		assert cmnt != null && cmnt.getPostId() == null && cmntReplyId != null;
		
		// comment exists
		Comment cmntReply = getComment( cmntReplyId );
		if(cmntReply == null) {
			throw new PofoServiceException(km.get("pofo_err_cmntnotfound"));
		}
		cmnt.setThread(cmntReply.getThread());
		Long postId = cmntReply.getPostId();
		cmnt.setPostId(postId);
		
		int rows = cmntMapper.insertComment(cmnt);
		if(rows > 0) {
			postService.incrComment(postId);
		}
		
		// TODO msg
	}

	@Override
	public Comment getComment(Long cmntId) {
		return cmntMapper.selectComment(cmntId);
	}

	@Override
	public List<CommentVO> listCommentByPost(Long postId, int begin, int count) {
		return cmntMapper.listCommentByPost(postId, begin, count);
	}

	@Override
	public List<CommentVO> listCommentByThread(String thread, int begin, int count) {
		return cmntMapper.listCommentByThread(thread, begin, count);
	}

}
