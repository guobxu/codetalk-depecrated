package me.codetalk.flow.fnd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import me.codetalk.cache.service.ICacheService;
import me.codetalk.messaging.Message;
import me.codetalk.flow.fnd.mapper.TagMapper;
import me.codetalk.flow.fnd.pojo.Tag;
import me.codetalk.flow.fnd.pojo.TagVO;
import me.codetalk.flow.fnd.service.ITagService;

@Service
public class TagServiceImpl implements ITagService {

	private static Logger LOGGER = LoggerFactory.getLogger(TagServiceImpl.class);
	
	@Autowired
	private TagMapper tagMapper;

	// cache
	private static final String CACHE_STAT_TAG_HIT = "STAT-TAG-HITS-";	// tag hits
	
	private static final String CACHE_TAGS = "TAGS";
	
	@Autowired
	private ICacheService cacheService;
	
//	@Override
//	public List<TagVO> getAllTags() {
//		List<TagVO> allTags = tagMapper.selectAllTags(); // assume: ordered & not empty
//		
//		return allTags;
//	}
	
	@Override
	public List<TagVO> getTagsByPosType(Integer posType) {
		List<TagVO> posTags = tagMapper.selectTagsByPos(posType); // assume: ordered & not empty
		
		return posTags;
	}
	
	@Override
	public List<Integer> getTagIdByText(List<String> tagList, boolean ignoreCase) {
		List<Tag> tags = findAllTags();
		
		// as map
		Map<String, Integer> tagMap = new HashMap<String, Integer>();
		for(Tag tag : tags) {
			String tagText = tag.getText();
			tagMap.put(ignoreCase ? tagText.toLowerCase() : tagText, tag.getId());
		}
		
		List<Integer> tagIdList = new ArrayList<Integer>();
		for(String tag : tagList) {
			Integer tagId = tagMap.get(ignoreCase ? tag.toLowerCase() : tag);
			if(tagId != null) tagIdList.add(tagId);
		}
		
		return tagIdList;
	}
	
	private List<Tag> findAllTags() {
		List<Tag> allTags = (List<Tag>)cacheService.get(CACHE_TAGS);
		
		if(allTags == null) {
			allTags = tagMapper.selectAllSimple();
			cacheService.set(CACHE_TAGS, allTags);
		}
		
		return allTags;
	}
	
	// Kafka listeners
	
	@KafkaListener(topics = "ssc-quest-create", groupId = "ssc-quest-create-fnd-tag-incr-group")
    public void msgIncrHit(Message message) {
		LOGGER.info("In incrHit...Receive mesg data = " + message.getData());
		
		List<Integer> tags = (List<Integer>)( (Map<String, Object>)message.getData() ).get("tags");
		incrHits(tags);
    }
	
	
	private void incrHits(List<Integer> tags) {
		String[] tagKeys = new String[tags.size()];
		for(int i = 0; i < tags.size(); i++) {
			tagKeys[i] = CACHE_STAT_TAG_HIT + tags.get(i);
		}
		
		cacheService.mIncr(tagKeys);
	}

}












