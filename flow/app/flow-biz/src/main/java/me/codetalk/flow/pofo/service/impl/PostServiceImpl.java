package me.codetalk.flow.pofo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.codetalk.cache.service.ICacheService;
import me.codetalk.messaging.kafka.aspect.annotation.KafkaAfter;
import me.codetalk.flow.fnd.stat.redis.HashStatSupport;
import me.codetalk.flow.pofo.mapper.PostImgMapper;
import me.codetalk.flow.pofo.mapper.PostMapper;
import me.codetalk.flow.pofo.pojo.Post;
import me.codetalk.flow.pofo.pojo.PostImg;
import me.codetalk.flow.pofo.pojo.PostVO;
import me.codetalk.flow.pofo.service.IPostService;

@Service("postService")
public class PostServiceImpl extends HashStatSupport implements IPostService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PostServiceImpl.class);
	
	@Autowired
	private PostMapper postMapper;
	
	@Autowired
	private PostImgMapper postImgMapper;
	
	@Autowired
	private ICacheService cacheService;
	
	// Cache
	private static final String CACHE_STAT_POST_LIKE = "STAT-POST-LIKE-";	// 点赞
	private static final String CACHE_STAT_POST_VIEW = "STAT-POST-VIEW-";	// 查看
	private static final String CACHE_STAT_POST_REFER = "STAT-POST-REFER-";	// 转发
	private static final String CACHE_STAT_POST_COMMENT = "STAT-POST-COMMENT-";	// 评论
	
	@Transactional
	@KafkaAfter(value = "flow-post-create", app = "flow", module = "pofo")
	public Post addPost(Post post) {
		postMapper.insertPost(post);
		
		List<PostImg> imgList = post.getImgList();
		if(imgList != null && imgList.size() > 0) {
			for(PostImg img : imgList) {
				img.setPostId(post.getId());
			}
			
			postImgMapper.insertImgList(imgList);
		}
		
		return post;
	}

	// 用户发表的帖子列表
	@Override
	public List<PostVO> listUserPost(Integer userId, int begin, int count) {
		List<PostVO> posts = postMapper.selectUserPost(userId, begin, count);
		
		if(posts.isEmpty()) return posts;
		
		setStats(posts);
		
		return posts;
	}

	// 用户帖子聚合列表
	@Override
	public List<PostVO> listReadByUser(Integer userId, int begin, int count) {
		List<PostVO> posts =  postMapper.listReadByUser(userId, begin, count);
		
		if(posts.isEmpty()) return posts;
		
		setStats(posts);
		
		return posts;
	}

	@Override
	public PostVO getPostVOById(Long postId) {
		PostVO post = postMapper.selectPostVOById(postId);
		if(post == null) return null;
		
		setStats(post);
		
		// 增加查看数
		incrView(postId);
		
		return post;
	}

	private void setStats(PostVO post) {
		List<PostVO> posts = new ArrayList<PostVO>();
		posts.add(post);
		
		setStats(posts);
	}
	
	private void setStats(List<PostVO> posts) {
		// id list
		List<Long> postIdList = new ArrayList<Long>();
		for(PostVO post : posts) {
			postIdList.add(post.getId());
		}
		
		Map<Long, Long> postLikes = getLikes(postIdList); // likes
		Map<Long, Long> postRefers = getRefers(postIdList); // refers
		Map<Long, Long> postViews = getViews(postIdList); // views
		Map<Long, Long> postComments = getComments(postIdList); // comments
		
		for(PostVO post : posts) {
			post.setPostLikes(postLikes.get(post.getId()).intValue());
			post.setPostRefers(postRefers.get(post.getId()).intValue());
			post.setPostViews(postViews.get(post.getId()).intValue());
			post.setPostComments(postComments.get(post.getId()).intValue());
		}
	}
	
	// no stats
	@Override
	public Post getPostById(Long postId) {
		return postMapper.selectPostById(postId);
	}

	// stat 
	
	@Override
	public Map<Long, Long> getLikes(List<Long> postIdList) {
		assert postIdList != null && postIdList.size() > 0;
		
		return getStats(postIdList, CACHE_STAT_POST_LIKE);
	}
	
	@Override
	public Map<Long, Long> getRefers(List<Long> postIdList) {
		assert postIdList != null && postIdList.size() > 0;
		
		return getStats(postIdList, CACHE_STAT_POST_REFER);
	}

	@Override
	public Map<Long, Long> getComments(List<Long> postIdList) {
		assert postIdList != null && postIdList.size() > 0;
		
		return getStats(postIdList, CACHE_STAT_POST_COMMENT);
	}

	@Override
	public Map<Long, Long> getViews(List<Long> postIdList) {
		assert postIdList != null && postIdList.size() > 0;
		
		return getStats(postIdList, CACHE_STAT_POST_VIEW);
	}
	
	@Override
	public void incrLike(Long postId) {
		incrStatBy(postId, CACHE_STAT_POST_LIKE, 1);
	}

	@Override
	public void decrLike(Long postId) {
		decrStatBy(postId, CACHE_STAT_POST_LIKE, 1);
	}
	
	@Override
	public void incrRefer(Long postId) {
		incrStatBy(postId, CACHE_STAT_POST_REFER, 1);
	}

	@Override
	public void decrRefer(Long postId) {
		decrStatBy(postId, CACHE_STAT_POST_REFER, 1);
	}

	@Override
	public void incrComment(Long postId) {
		incrStatBy(postId, CACHE_STAT_POST_COMMENT, 1);
	}

	@Override
	public void decrComment(Long postId) {
		decrStatBy(postId, CACHE_STAT_POST_COMMENT, 1);
	}

	@Override
	public void incrView(Long postId) {
		incrStatBy(postId, CACHE_STAT_POST_VIEW, 1);
	}
	
//	@Override
//	public Integer getHashRange(String statType) {
//		return Constants.CACHE_STAT_POST_RANGE;
//	}
	
	// MQ listeners
//	@RabbitListener(queues = Constants.QUEUE_POST_ADD_HASHTAG)
//	public void msgPostAdd_hashtag(Message message) {
//		LOGGER.info("In msgPostAdd_hashtag...Receive mesg data = " + message.getData());
//    }
//	
//	@RabbitListener(queues = Constants.QUEUE_POST_ADD_NOTIF)
//    public void msgPostAdd_notif(Message message) {
//		LOGGER.info("In msgPostAdd_notif...Receive mesg data = " + message.getData());
//    }

	// TODO kakfa listeners

}
