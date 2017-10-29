package me.codetalk.flow.fnd.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import me.codetalk.flow.fnd.pojo.Notice;

/**
 * 通知消息mapper
 * @author guobxu
 *
 */
public interface NoticeMapper {

	/**************************** APP ****************************/
	
	public void insertNotice(Notice notice);
	
	public void insertNoticeList(List<Notice> noticeList);
	
	public List<Map<String, Integer>> countAppUnreadPerType(@Param("userId") Integer userId);
	
	public Integer countAppUnread(@Param("userId") Integer userId);
	
	public List<Notice> selectAppNoticeList(@Param("userId") Integer userId, @Param("begin") int begin, @Param("count") int count);
	
	public void updateAppReadByUser(@Param("userId") Integer userId);
	
}












