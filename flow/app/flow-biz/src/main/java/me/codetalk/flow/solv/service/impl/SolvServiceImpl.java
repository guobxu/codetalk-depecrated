package me.codetalk.flow.solv.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.codetalk.flow.fnd.service.ITagService;
import me.codetalk.flow.service.impl.AbstractBizService;
import me.codetalk.flow.solv.Constants;
import me.codetalk.flow.solv.exception.SolvServiceException;
import me.codetalk.flow.solv.pojo.Comment;
import me.codetalk.flow.solv.pojo.CommentVO;
import me.codetalk.flow.solv.pojo.Quest;
import me.codetalk.flow.solv.pojo.QuestVO;
import me.codetalk.flow.solv.pojo.Reply;
import me.codetalk.flow.solv.service.ICommentService;
import me.codetalk.flow.solv.service.IQuestService;
import me.codetalk.flow.solv.service.IReplyService;
import me.codetalk.flow.solv.service.ISolvService;
import me.codetalk.util.JsonUtils;

@Service("solvService")
public class SolvServiceImpl extends AbstractBizService implements ISolvService {

	private static Logger LOGGER = LoggerFactory.getLogger(SolvServiceImpl.class);
	
	@Autowired
	private IQuestService questService;
	
	@Autowired
	private IReplyService replyService;
	
	@Autowired
	private ICommentService cmntService;
	
	@Autowired
	private ITagService tagService; // TODO
	
	// URI POST
	private static final String URI_QUEST_PUBLISH = "/flow/solv/quest/publish";
	private static final String URI_REPLY_ADD = "/flow/solv/reply";
	private static final String URI_COMMENT_ADD = "/flow/solv/comment";
	private static final String URI_QUEST_ACCEPT = "/flow/solv/accept";
	
	private static final String URI_REPLY_VOTE = "/flow/solv/reply/vote";
	
	// URI GET
	private static final String URI_QUEST_LIST = "/flow/solv/quest/list";
	private static final String URI_QUEST_DTL = "/flow/solv/quest";
	private static final String URI_COMMENT_LIST = "/flow/solv/comment/list";
	
	// ret template
	// 发布问题成功
	public static final String QUEST_PUBLISH_SUCCESS_TMPL = "{\"ret_code\":1, "
															+ "\"ret_data\": {"
																	+ "\"quest_id\": %d"
															+  "}"
														+ "}";
	
	// 发布回复成功
	public static final String REPLY_ADD_SUCCESS_TMPL = "{\"ret_code\":1, "
														+ "\"ret_data\": {"
																+ "\"reply_id\": %d"
														+  "}"
													+ "}";
	
	// 发布评论成功
	public static final String COMMENT_ADD_SUCCESS_TMPL = "{\"ret_code\":1, "
														+ "\"ret_data\": {"
																+ "\"comment_id\": %d"
														+  "}"
													+ "}";
	
	@PostConstruct
	public void setUriSecured() {
		securedUriSet.addAll(Arrays.asList(new String[] {
				// POST
				URI_QUEST_PUBLISH,
				URI_REPLY_ADD,
				URI_COMMENT_ADD,
				URI_QUEST_ACCEPT,
				URI_REPLY_VOTE,
				
				// GET
				
		}));
	}
	
	@Override
	protected String doPost0(String uri, Map<String, Object> data) {
		if(URI_QUEST_PUBLISH.equals(uri)) {
			return publishQuest(data);
		} else if(URI_REPLY_ADD.equals(uri)) {
			return reply(data);
		} else if(URI_COMMENT_ADD.equals(uri)) {
			return comment(data);
		} else if(URI_QUEST_ACCEPT.equals(uri)) {
			return accept(data);
		} else if(URI_REPLY_VOTE.equals(uri)) {
			return voteReply(data);
		}
		
		return errorWithKey("sys_uri_notfound");
	}

	@Override
	protected String doGet0(String uri, Map<String, String[]> params) {
		if(URI_QUEST_LIST.equals(uri)) {
			return questList(params);
		} else if(URI_QUEST_DTL.equals(uri)) {
			return questDtl(params);
		} else if(URI_COMMENT_LIST.equals(uri)) {
			return commentList(params);
		}

		return errorWithKey("sys_uri_notfound");
	}

	/**
	 * Param:
	 * qid=0 // 问题ID, 可选
	 * rid=0 // 答复id, 可选
	 * 
	 * @param params
	 * @return
	 */
	private String commentList(Map<String, String[]> params) {
		String[] qidArrObj = params.get("qid"), ridArrObj = params.get("rid");

		List<CommentVO> cmnts = null;
		if(qidArrObj != null) {
			Long qid = Long.parseLong(qidArrObj[0]);
			cmnts = cmntService.listCommentVOByQuest(qid);
		} else if(ridArrObj != null) {
			Long rid = Long.parseLong(ridArrObj[0]);
			cmnts = cmntService.listCommentVOByReply(rid);
		} else {
			return errorWithKey("solv_err_qid_rid_null");
		}
		
		return successWithData(cmnts);
	}

	/**
	 * Param: 
		{
		"user_login":"xx"
		"access_token":"xx"
		"quest_title": "xx" // 问题标题
		  "quest_content": "xx" // 问题内容
		"quest_tags": [
		    "xx1",
		    ...
		  ]
		"notify_users": [
		"xx"   // user_login列表
		    ...
		  ]
		}

	 * Return:
		{
		  "ret_code":0 //返回码
		  "ret_msg":"xx" //可选，返回错误时的错误描述
		"ret_data": {
		"quest_id": 0 // 返回问题id
		  }
		}
	 * 
	 * @param data
	 * @return
	 */
	private String publishQuest(Map<String, Object> data) {
		String title = data.get("quest_title").toString(),
				content = data.get("quest_content").toString();
		
		List<Integer> tags = (List<Integer>)data.get("quest_tags");
		Object notifUserObj = data.get("notify_users");
		
		if(tags == null || tags.size() == 0) {
			return errorWithKey("solv_err_quest_notag");
		}
		
		// quest builder
		Quest quest = new Quest();
		
		Integer userId = getUserLogin().getUserId();
		quest.setUserId(userId);
		quest.setUpdateBy(userId);
		
		quest.setTitle(title);
		quest.setContent(content);
		
		if(notifUserObj != null) quest.setNotifyUsers(JsonUtils.toJson(notifUserObj));
		
		quest.setStatus(Constants.QUEST_STATUS_PUBLISHED);
		
		questService.publishQuest(quest, tags);
		
		return String.format(QUEST_PUBLISH_SUCCESS_TMPL, quest.getId());
	}
	
	/**
 	 * Param:
		{
		"user_login":"xx"
		"access_token":"xx"
		"quest_id": 0L //问题id
		  "reply_content": "xx" // 答复内容
		"notify_users": [
		"xx"   // user_login列表
		    ...
		  ]
		}
		
 	 * Return: 
		{
		  "ret_code":0 //返回码
		  "ret_msg":"xx" //可选，返回错误时的错误描述
			"ret_data": {
			"reply_id": 0 // 返回答复id
		  }
		}
	 * 
	 * @param data
	 * @return
	 */
	private String reply(Map<String, Object> data) {
		Long qid = Long.valueOf(data.get("quest_id").toString());
		String content = data.get("reply_content").toString();
		
		Object notifUserObj = data.get("notify_users");

		Reply reply = new Reply();
		reply.setQuestId(qid);
		
		Integer userId = getUserLogin().getUserId();
		reply.setUserId(userId);
		reply.setUpdateBy(userId);
		reply.setContent(content);
		
		if(notifUserObj != null) reply.setNotifyUsers(JsonUtils.toJson(notifUserObj));
		
		replyService.addReply(reply);
		
		return String.format(REPLY_ADD_SUCCESS_TMPL, reply.getId());
	}
	
	/**
	 * Param:
		{
		"user_login":"xx"
		"access_token":"xx"
		"quest_id": 0L // 问题id
		  "reply_id": 0L // 答复id
		"comment_reply": 0L //评论id
		"comment_content": "xx" // 评论内容
		"notify_users": [
		"xx"   // user_login列表
		    ...
		  ]
		}
	 * Return:
		{
		  "ret_code":0 //返回码
		  "ret_msg":"xx" //可选，返回错误时的错误描述
			"ret_data": {
			"comment_id": 0 // 返回评论id
		  }
		}
	 * 
	 * @param data
	 * @return
	 */
	private String comment(Map<String, Object> data) {
		Object questIdObj = data.get("quest_id"), 
				replyIdObj = data.get("reply_id"),
				cmntReplyObj = data.get("comment_reply");
		
		Object notifUserObj = data.get("notify_users");
		
		String content = data.get("comment_content").toString();
		
		// comment
		Comment cmnt = new Comment();
		
		Integer userId = getUserLogin().getUserId();
		cmnt.setUserId(userId);
		cmnt.setUpdateBy(userId);
		cmnt.setContent(content);
		
		if(notifUserObj != null) cmnt.setNotifyUsers(JsonUtils.toJson(notifUserObj));
		
		try {
			if(questIdObj != null && replyIdObj == null && cmntReplyObj == null) { // 问题评论
				cmnt.setQuestId(Long.valueOf(questIdObj.toString())); 
				cmntService.addQuestComment(cmnt);
			} else if(replyIdObj != null && questIdObj == null && cmntReplyObj == null) { // 答复评论
				cmnt.setReplyId(Long.valueOf(replyIdObj.toString()));
				cmntService.addReplyComment(cmnt);
			} else if(questIdObj != null && cmntReplyObj != null && replyIdObj == null) { // 问题评论的评论
				cmnt.setQuestId(Long.valueOf(questIdObj.toString()));
				cmnt.setCommentReply(Long.valueOf(cmntReplyObj.toString()));
				cmntService.addCoComment(cmnt, true);
			} else if(replyIdObj != null && cmntReplyObj != null && questIdObj == null) { // 答复评论的评论
				cmnt.setReplyId(Long.valueOf(replyIdObj.toString()));
				cmnt.setCommentReply(Long.valueOf(cmntReplyObj.toString()));
				cmntService.addCoComment(cmnt, false);
			} else {
				return errorWithKey("solv_err_cmnt_all_null");
			}
		} catch(SolvServiceException ex) {
			LOGGER.error(ex.getMessage(), ex);
			
			return errorWithMsg(ex.getMessage());
		}
		
		return String.format(COMMENT_ADD_SUCCESS_TMPL, cmnt.getId());
	}
	
	/**
	 * Param:
	    tag="xx" // 标签，可选
		begin=0 // 起止
		count=20 // 条数
		
	 * Return:
		{
		  "ret_code":0 //返回码
		  "ret_msg":"xx" //可选，返回错误时的错误描述
			"ret_data": [
			{
			"quest_id":0L // 提问id
			"user_login":"xx" // 提问者
			"quest_title":"xx" // 问题标题
			"quest_tags": [
			"xx1",
			...
			]
			"quest_plus ":0 // 同样问题的人数
			"quest_answers":0 // 问题答复数
			"quest_replies":0 // 问题评论数
			"quest_accepted":0 // 是否有答案
			}
		  ]
		}
	 * 
	 * @param params
	 * @return
	 */
	private String questList(Map<String, String[]> params) {
		String[] tags = params.get("tag");
	
		Integer begin = Integer.parseInt(params.get("begin")[0]), count = Integer.parseInt(params.get("count")[0]);
		
		List<QuestVO> questList = null;
		if(tags == null) {
			questList = questService.listQuest(begin, count);
		} else {
			List<Integer> tagIdList = tagService.getTagIdByText(Arrays.asList(tags), true);
			questList = questService.listQuestByTag(tagIdList, begin, count);
		}
		
		return successWithData(questList);
	}
	
	/**
	 * Param:
	 * qid = ?
	 * 
	 * TODO: 已登录
	 * @param params
	 * @return
	 */
	private String questDtl(Map<String, String[]> params) {
		Long qid = Long.parseLong(params.get("qid")[0]);
		
		QuestVO quest = questService.getQuestVOById(qid);
		
		if(quest == null) return errorWithKey("solv_err_quest_notfound");
		
		return successWithData(quest);
	}
	
	/**
	 * Param:
	   {
		"user_login":"xx"
		"access_token":"xx"
		"reply_id": 0L // 答复id
	   }
	 * 
	 * @param data
	 * @return
	 */
	private String accept(Map<String, Object> data) {
		Long replyId = Long.parseLong(data.get("reply_id").toString());
		
		try {
			questService.acceptReply(getUserLogin().getUserId(), replyId);
		} catch(SolvServiceException ex) {
			return errorWithMsg(ex.getMessage());
		}
		
		return Constants.RESPONSE_SUCCESS;
	}
	
	/**
	 * Param:
	 * {
		"user_login":"xx"
		"access_token":"xx"
		"reply_id": 0L
		"action_type": 0 // 1 up 2 down 3 取消
		}
     *
	 * @param data
	 * @return
	 */
	private String voteReply(Map<String, Object> data) {
		Long rid = Long.parseLong(data.get("reply_id").toString());
		Integer actionType = Integer.parseInt(data.get("action_type").toString());
		
		Integer userId = getUserLogin().getUserId();
		try {
			replyService.voteReply(userId, rid, actionType);
		} catch(SolvServiceException ex) {
			LOGGER.error(ex.getMessage(), ex);
			
			return errorWithMsg(ex.getMessage());
		}
		
		return Constants.RESPONSE_SUCCESS;
	}
	
}






















