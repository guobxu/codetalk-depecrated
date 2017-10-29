package me.codetalk.flow.fnd.stat;

import java.util.List;
import java.util.Map;

// 统计支持
public interface StatSupport {

	public void incrStatBy(Long entityId, String statType, long delta);
	
	public void decrStatBy(Long entityId, String statType, long delta);
	
	public Long getStat(Long entityId, String statType);
	
	public Map<Long, Long> getStats(List<Long> entityIdList, String statType);
	
}
