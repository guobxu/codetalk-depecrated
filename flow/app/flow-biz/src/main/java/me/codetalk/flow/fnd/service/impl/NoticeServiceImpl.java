package me.codetalk.flow.fnd.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.codetalk.mesg.KeyedMessages;
import me.codetalk.flow.fnd.enums.NoticeMode;
import me.codetalk.flow.fnd.mapper.NoticeMapper;
import me.codetalk.flow.fnd.pojo.Notice;
import me.codetalk.flow.fnd.service.INoticeService;

/**
 * 通知服务实现
 * @author guobxu
 *
 */
@Service("noticeService")
public class NoticeServiceImpl implements INoticeService {
	
	@Autowired
	private NoticeMapper noticeMapper;
	
	@Autowired
	private KeyedMessages km;
	
	@Override
	public void addNotice(Notice notice, int mode) {
		initNotice(notice, mode);
		
		noticeMapper.insertNotice(notice);
	}
	
	@Transactional
	public void addNoticeList(List<Notice> noticeList, int mode) {
		for(Notice notice : noticeList) {
			initNotice(notice, mode);
		}
		
		noticeMapper.insertNoticeList(noticeList);
	}
	
	@Override
	public void updateAppReadByUser(Integer userId) {
		noticeMapper.updateAppReadByUser(userId);
	}
	
	// 初始化Notice属性: 内容, 通知类型等
	private void initNotice(Notice notice, int mode) {
		String content = getNoticeContent(notice);
		notice.setContent(content);
		
		notice.setIsApp( (mode & NoticeMode.APP) > 0 ? 1 : 0 );
		notice.setIsPush( (mode & NoticeMode.PUSH) > 0 ? 1 : 0 );
	}
	
	private String getNoticeContent(Notice notice) {
		String mesgKey = "notice_fmt_" + notice.getType();
		
		Integer subType = notice.getSubType();
		if(subType != null) mesgKey = mesgKey + "_" + subType;
		
		Map<String, String> dataMap = notice.getData(); 
		
		return km.getWithParams(mesgKey, dataMap);
	}
	
	@Override
	public Integer countAppUnread(Integer userId) {
		return noticeMapper.countAppUnread(userId);
	}

	@Override
	public List<Notice> getAppNoticeList(Integer userId, int begin, int count) {
		return noticeMapper.selectAppNoticeList(userId, begin, count);
	}
	
	

}
















