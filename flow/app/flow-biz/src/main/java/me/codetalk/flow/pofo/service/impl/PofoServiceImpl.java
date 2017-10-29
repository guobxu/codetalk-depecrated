package me.codetalk.flow.pofo.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.codetalk.flow.auth.pojo.User;
import me.codetalk.flow.auth.pojo.UserLogin;
import me.codetalk.flow.auth.service.IAuthService;
import me.codetalk.flow.pofo.Constants;
import me.codetalk.flow.pofo.exception.PofoServiceException;
import me.codetalk.flow.pofo.pojo.Comment;
import me.codetalk.flow.pofo.pojo.CommentVO;
import me.codetalk.flow.pofo.pojo.Post;
import me.codetalk.flow.pofo.pojo.PostImg;
import me.codetalk.flow.pofo.pojo.PostVO;
import me.codetalk.flow.pofo.service.ICommentService;
import me.codetalk.flow.pofo.service.IFollowService;
import me.codetalk.flow.pofo.service.IPofoService;
import me.codetalk.flow.pofo.service.IPostLikeService;
import me.codetalk.flow.pofo.service.IPostService;
import me.codetalk.flow.service.impl.AbstractBizService;
import me.codetalk.util.JsonUtils;

/**
 * Pofo 接口服务实现
 * @author guobxu
 *
 */
@Service("pofoService")
public class PofoServiceImpl extends AbstractBizService implements IPofoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PofoServiceImpl.class);

	@Autowired
	private IPostService postService;
	
	@Autowired 
	private IAuthService authService;
	
	@Autowired
	private IFollowService followService;
	
	@Autowired
	private IPostLikeService postLikeService;
	
	@Autowired
	private ICommentService commentService;
	
	// URI POST
	private static final String URI_POST_CREATE = "/ssc/pofo/post/create";
	private static final String URI_USER_FOLLOW = "/ssc/pofo/follow";
	private static final String URI_POST_LIKE = "/ssc/pofo/post/like";
	private static final String URI_POST_CMNT = "/ssc/pofo/comment/create";
	
	// URI GET
	private static final String URI_POST_LIST = "/ssc/pofo/post/list";
	private static final String URI_POST_DTL = "/ssc/pofo/post";
	private static final String URI_CMNT_LIST = "/ssc/pofo/comment/list";
	
	
	@PostConstruct
	public void setUriSecured() {
		securedUriSet.addAll(Arrays.asList(new String[] {
				// POST
				URI_POST_CREATE, 
				URI_USER_FOLLOW,
				URI_POST_LIKE,
				URI_POST_CMNT,
				
				// GET
				URI_POST_LIST,
				URI_POST_DTL,
				URI_CMNT_LIST
		}));
	}
	
	protected String doPost0(String uri, Map<String, Object> data) {
		if(URI_POST_CREATE.equals(uri)) {
			return createPost(data);
		} else if(URI_USER_FOLLOW.equals(uri)) {
			return follow(data);
		} else if(URI_POST_LIKE.equals(uri)) {
			return like(data);
		} else if(URI_POST_CMNT.equals(uri)) {
			return comment(data);
		}
		
		return errorWithKey("sys_uri_notfound");
	}
	
	protected String doGet0(String uri, Map<String, String[]> params) {
		if(URI_POST_LIST.equals(uri)) {
			return listPost(params);
		} else if(URI_POST_DTL.equals(uri)) {
			return postDetail(params);
		} else if(URI_CMNT_LIST.equals(uri)) {
			return listComment(params);
		}
		
		return errorWithKey("sys_uri_notfound");
	}

	/**
	 * Param
		{
		"user_login":"xx"
		"access_token":"xx"
		"user_login_follow": "xx" // 被关注用户
		"action_type":0 // 1 关注 2 取消关注
		}

	 * Return
		{
		  "ret_code":0 //返回码
		  "ret_msg":"xx" //可选，返回错误时的错误描述
		}
	 * 
	 * @param data
	 * @return
	 */
	private String follow(Map<String, Object> data) {
		String userFollowedParam = data.get("user_login_follow").toString();
		Integer actionTypeParam = (Integer)data.get("action_type");
		
		UserLogin userLogin = getUserLogin();
		User userFollow = authService.getUserByLogin(userFollowedParam);
		if(userFollow == null) {
			return errorWithKey("auth_err_user_missing");
		}
		if(userLogin.getUserId().equals(userFollow.getId())) {
			return errorWithKey("pofo_err_followself");
		}
		
		if(Constants.ACTION_USER_FOLLOW == actionTypeParam) {
			followService.follow(userLogin.getUserId(), userFollow.getId());
		} else if(Constants.ACTION_USER_UNFOLLOW == actionTypeParam) {
			followService.unfollow(userLogin.getUserId(), userFollow.getId());
		}
		
		return Constants.RESPONSE_SUCCESS;
	}

	/**
	 * 请求参数:
	 	{
			"access_token":"xx"
			"post_content": "xx" // 帖子内容，可选
			"post_id_refer": 0L // 转帖引用的帖子
			"comment_id_refer": 0L // 引用评论id
			"post_imgs": [
				"xx"
				...
			  ]
			"notify_users": [
				"xx"   // user_login列表
			    ...
			  ]
		}
	 * 
	 * 返回结果:
	 	{
		  	"ret_code":0 //返回码
		  	"ret_msg":"xx" //可选，返回错误时的错误描述
			"ret_data": {
				"post_id": 0 // 返回帖子id
		  	}
		}

	 */
	private String createPost(Map<String, Object> data) {
		Object postContentObj = data.get("post_content"), postReferObj = data.get("post_id_refer"), 
				cmntReferObj = data.get("comment_id_refer"), postImgObj = data.get("post_imgs"), 
				notifUserObj = data.get("notify_users");
		
		if(postContentObj == null && postReferObj == null && cmntReferObj == null) {
			return errorWithKey("pofo_err_emptycontent");
		}
		
		// add post
		Post post = new Post();
		
		UserLogin userLogin  = getUserLogin();
		post.setUserId(userLogin.getUserId());
		if(postContentObj != null) post.setContent(postContentObj.toString());
		
		if(postReferObj != null) {
			post.setReferPost(Long.parseLong(postReferObj.toString()));
			post.setType(Constants.POST_TYPE_REFER);
		} else if(cmntReferObj != null) {
			post.setReferComment(Long.parseLong(cmntReferObj.toString()));
			post.setType(Constants.POST_TYPE_REFER);
		} else {
			post.setType(Constants.POST_TYPE_NEW);
		}
		
		if(postImgObj != null) {
			List<String> imgUrlList = (List<String>)postImgObj;
			List<PostImg> postImgList = new ArrayList<PostImg>();
			for(int i = 0; i < imgUrlList.size(); i++) {
				String imgUrl = imgUrlList.get(i);
				
				PostImg postImg = new PostImg();
				postImg.setUrl(imgUrl);
				postImg.setSeq((i + 1) * 10);
				
				postImgList.add(postImg);
			}
			
			post.setImgList(postImgList);
		}
		
		if(notifUserObj != null) post.setNotifyUsers(JsonUtils.toJson(notifUserObj));
		
		postService.addPost(post);
		
		// ret
		Map<String, Object> rtData = new HashMap<String, Object>();
		rtData.put("post_id", post.getId());
		
		return successWithData(rtData);
	}
	
	/**
	 请求参数:
		user_login=xx
		access_token=xx
		q=xx // 查询关键字，可选
		user_login_get="xx" // 用户登录名，可选
		show_img=0 // 是否显示图片 0 不显示 1 显示
		begin=0 // 起止
		count=20 // 条数
	 返回:
	 	"post_id": 0L // 帖子id
		"user_name": "xx" // 用户名
		"user_login": "xx" // 登录名
		"user_profile": "xx" // 头像
		"post_type": 0 // 1 帖子 2 转帖 3 引用帖子
		"post_content": "xx" // 帖子内容
		"post_imgs": [ "xx"... ] // 帖子图片，可选
		"post_refers": 0 // 转帖数
		"post_likes": 0 // 点赞 
		"post_comments": 0 // 帖子评论数
		"create_date": 0L // 帖子发表日期
		// 转帖类型为帖子
		"post_id_refer": 0 // 引用的帖子id
		"user_name_refer": "xx" // 引用帖子用户名
		"user_login_refer": "xx" // 引用帖子登录名
		"post_content_refer": "xx" // 引用帖子的内容
		// 转帖类型为评论
		"comment_id_refer": 0// 引用的评论id
		"comment_user_reply": "xx"// 评论回复的用户
	 */
	private String listPost(Map<String, String[]> params) {
		String queryParam = ( params.get("q") == null ? null : params.get("q")[0] ), // TODO: 支持搜索 
				userLoginGet = ( params.get("user_login_get") == null ? null : params.get("user_login_get")[0] );
		
		int begin = Integer.parseInt(params.get("begin")[0]),
				count = Integer.parseInt(params.get("count")[0]);
		
		List<PostVO> postList = null;
		if(userLoginGet != null) { // 查看对方 或者 自己的帖子列表
			Integer userId = authService.getUserIdByLogin(userLoginGet);
			if(userId == -1) return errorWithKey("auth_err_user_missing");
			
			postList = postService.listUserPost(userId, begin, count);
		} else {	// 查看聚合列表
			UserLogin userLogin = getUserLogin();
			postList = postService.listReadByUser(userLogin.getUserId(), begin, count);
		}
		
		String rtData = successWithData(postList);
		// LOGGER.info("listPost return: " + rtData);
		
		return rtData;
	}
	
	/**
	 * 参数：
		user_login=xx
		access_token=xx
		post_id=0L
	 * 
	 * @param params
	 * @return
	 */
	private String postDetail(Map<String, String[]> params) {
		Long postId = Long.parseLong(params.get("post_id")[0]);
		
		PostVO post = postService.getPostVOById(postId);
		
		if(post == null) {
			return errorWithKey("pofo_err_postnotfound");
		} else {
			return successWithData(post);
		}
	}
	
	/**
	 * Param
		{
		"user_login":"xx"
		"access_token":"xx"
		"post_id": 0 // 帖子id
		"action_type":0 // 1 点赞 2 取消
		}
 	 
 	 * Return
		{
		  "ret_code":0 //返回码
		  "ret_msg":"xx" //可选，返回错误时的错误描述
		}
	 * @param data
	 * @return
	 */
	private String like(Map<String, Object> data) {
		Long postIdParam = Long.parseLong(data.get("post_id").toString());
		Integer actionTypeParam = (Integer)data.get("action_type");
		
		UserLogin userLogin = getUserLogin();
		Post post = postService.getPostById(postIdParam);
		if(post == null) {
			return errorWithKey("pofo_err_postnotfound");
		}
		
		if(Constants.ACTION_POST_LIKE == actionTypeParam) {
			postLikeService.like(postIdParam, userLogin.getUserId());
		} else if(Constants.ACTION_POST_UNLIKE == actionTypeParam) {
			postLikeService.unlike(postIdParam, userLogin.getUserId());
		}
		
		return Constants.RESPONSE_SUCCESS;
	}
	
	/**
	 * Param
		{
		"user_login":"xx"
		"access_token":"xx"
		"post_id": 0 // 帖子id
		"comment_id_reply": 0 //回复的comment id
		"comment_content": "xx":  //评论内容
		"notify_users": [
		"xx"   // user_login列表
		    ...
		  ]
		}

	 * Return
		{
		  "ret_code":0 //返回码
		  "ret_msg":"xx" //可选，返回错误时的错误描述
		}

	 * 
	 * @param data
	 * @return
	 */
	private String comment(Map<String, Object> data) {
		Object postId = data.get("post_id");
		Object cmntReply = data.get("comment_id_reply");
		String content = data.get("comment_content").toString();
		Object notifUserObj = data.get("notify_users");
		
		// add comment
		Comment cmnt = new Comment();
		cmnt.setUserId(getUserLogin().getUserId());
		if(cmntReply != null) cmnt.setCommentReply(Long.valueOf(cmntReply.toString()));
		cmnt.setContent(content);
		
		if(notifUserObj != null) cmnt.setNotifyUsers(JsonUtils.toJson(notifUserObj));
		
		if(postId != null) {
			cmnt.setPostId(Long.valueOf(postId.toString()));
			commentService.addPostComment(cmnt);
		} else if(cmntReply != null) {
			cmnt.setCommentReply(Long.valueOf(cmntReply.toString()));
			
			try {
				commentService.addThreadComment(cmnt);
			} catch(PofoServiceException ex) {
				LOGGER.error(ex.getMessage(), ex);
				
				return errorWithMsg(ex.getMessage());
			}
		} else {
			return errorWithKey("pofo_err_cmnt_both_null");
		}
		
		return Constants.RESPONSE_SUCCESS;
	}

	/**
	 * Param:
		{
		"user_login":"xx"
		"access_token":"xx"
		"post_id": 0 // 帖子id
		"comment_thread": "xxx" // 评论thread
		"begin": 0
		"count": 0
		}

	 * Return:
		{
		  	"ret_code":0 //返回码
		  	"ret_msg":"xx" //可选，返回错误时的错误描述
			"ret_data": [
			{
			"comment_id": 0 // 评论id
			"user_name": "xx" // 评论用户名
			"user_login": "xx" // 评论登录名
			"user_profile": "xx" // 评论头像
			"comment_content": "xx" // 评论内容
			"notify_users": ["xx1"...] // 提醒用户
			"create_date": 0L // 评论日期
			}
		}
	 *
	 *  
	 * @return
	 */
	private String listComment(Map<String, String[]> params) {
		Integer begin = Integer.parseInt(params.get("begin")[0]),
				count = Integer.parseInt(params.get("count")[0]);

		String[] postIdParam = params.get("post_id"), threadParam =  params.get("comment_thread");
		Long postId = null;
		String thread = null;
		
		if(postIdParam != null) postId = Long.valueOf(postIdParam[0]);
		if(threadParam != null) thread = threadParam[0];
		
		if(postId == null && thread == null) {
			return errorWithKey("pofo_err_listcmnt_both_null");
		}
		
		List<CommentVO> rtData = null;
		if(postId != null) {
			rtData = commentService.listCommentByPost(postId, begin, count);
		} else {
			rtData = commentService.listCommentByThread(thread, begin, count);
		}
		
		return successWithData(rtData); 
	}
	
	
}





















