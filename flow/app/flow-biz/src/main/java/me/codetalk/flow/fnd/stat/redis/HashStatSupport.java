package me.codetalk.flow.fnd.stat.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import me.codetalk.cache.service.ICacheService;
import me.codetalk.flow.fnd.Constants;
import me.codetalk.flow.fnd.stat.StatSupport;

/**
 * 基于redis的统计支持
 * @author guobxu
 *
 */
public class HashStatSupport implements StatSupport {

	@Autowired
	private ICacheService cacheService;
	
	// 获取hash范围, 默认10000
	public Integer getHashRange(String statType) {
		return Constants.CACHE_STAT_HASH_RANGE;
	}
	
	@Override
	public void incrStatBy(Long entityId, String statType, long delta) {
		String cacheKey = getStatCacheKey(statType, entityId);
		
		String field = String.valueOf(entityId);
		cacheService.hIncrBy(cacheKey, field, delta);
	}

	@Override
	public void decrStatBy(Long entityId, String statType, long delta) {
		String cacheKey = getStatCacheKey(statType, entityId);
		
		String field = String.valueOf(entityId);
		cacheService.hIncrBy(cacheKey, field, -1 * delta);
	}

	protected String getStatCacheKey(String statType, Long entityId) {
		Integer range = getHashRange(statType);
		
		long tmp = entityId / range;
		Long low = range * tmp + 1, high = range * ( tmp + 1 );

		String cacheKey = statType + low + "-" + high;
		
		return cacheKey;
	}
	
	@Override
	public Long getStat(Long entityId, String statType) {
		String cacheKey = getStatCacheKey(statType, entityId);
		
		Object num =  cacheService.hGet(cacheKey, String.valueOf(entityId));
		
		return num == null ? 0 : Long.valueOf(num.toString());
	}
	
	public Map<Long, Long> getStats(List<Long> entityIdList, String statType) {
		Map<String, List<String>> idPartions = new HashMap<String, List<String>>();
		for(Long entityId : entityIdList) {
			String cacheKey = getStatCacheKey(statType, entityId);
			List<String> idPartion = idPartions.get(cacheKey);
			if(idPartion == null) {
				idPartion = new ArrayList<String>();
				idPartions.put(cacheKey, idPartion);
			}
			
			idPartion.add(String.valueOf(entityId));
		}
		
		// result
		Map<Long, Long> statMap = new HashMap<Long, Long>();
		for(Map.Entry<String, List<String>> kv : idPartions.entrySet()) {
			String cacheKey = kv.getKey();
			List<String> pidPartion = kv.getValue();
			List statNums = (List)cacheService.hMGet(cacheKey, pidPartion);
			
			for(int i = 0; i < pidPartion.size(); i++) {
				String entityId = pidPartion.get(i);
				Object num = statNums.get(i);
				statMap.put(Long.valueOf(entityId), num == null ? 0 : Long.valueOf(num.toString()));
			}
		}
		
		return statMap;
	}

}










