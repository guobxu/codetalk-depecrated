package me.codetalk.flow.fnd.enums;

/**
 * 
 * 通知消息类型
 * 
 * @author guobxu
 *
 */
public enum NoticeType {

	// Quest 
	
	QMENTION(101, "问题@"),
	QREPLY(102, "问题答复"),
	QQCMNT(104, "问题评论"),
	QRCMNT(105, "答复评论"),
	QRVOTEUP(106, "答复voteup"),
	QRVOTEDOWN(107, "答复votedown"),
	
	// Pofo
	PMENTION(201, "帖子@"),
	PCMNT(202, "帖子评论"), 
	PCM(203, "帖子评论@"), // cmnt mention
	PCR(204, "帖子评论回复"), // cmnt reply
	PCRM(205, "帖子评论回复@"), // cmnt reply @
	PLIKE(206, "帖子like"), 
	PFOLLOW(207, "follow");
	
	
	private int code;
	private String name;
	
	private NoticeType(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
	
}
