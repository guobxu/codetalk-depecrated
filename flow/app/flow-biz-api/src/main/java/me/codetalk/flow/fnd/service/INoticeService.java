package me.codetalk.flow.fnd.service;

import java.util.List;

import me.codetalk.flow.fnd.pojo.Notice;

/**
 * 通知消息服务
 * @author guobxu
 *
 */
public interface INoticeService {

	public void addNotice(Notice notice, int mode);
	
	public void addNoticeList(List<Notice> noticeList, int mode);
	
	public Integer countAppUnread(Integer userId);

	public List<Notice> getAppNoticeList(Integer userId, int begin, int count);
	
	public void updateAppReadByUser(Integer userId);

	
}
