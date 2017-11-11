package me.codetalk.flow.solv.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.codetalk.flow.solv.Constants;
import me.codetalk.flow.solv.elastic.DocQuest;
import me.codetalk.flow.solv.elastic.repos.QuestRepository;
import me.codetalk.flow.solv.mapper.ExtQuestMapper;
import me.codetalk.flow.solv.pojo.ExtQuest;
import me.codetalk.flow.solv.service.IExtQuestService;
import me.codetalk.messaging.Message;
import me.codetalk.util.JsonUtils;
import me.codetalk.util.StringUtils;

@Service("extQuestService")
public class ExtQuestServiceImpl implements IExtQuestService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExtQuestServiceImpl.class); 
	
	@Autowired
	private ExtQuestMapper eqmapper;
	
	@Autowired
	private QuestRepository questRepo;
	
	@Override
	@Transactional
	public void addExtQuest(ExtQuest quest) {
		LOGGER.info("Add ext quest, uuid=" + quest.getUuid());
		
		String quid = quest.getUuid();
		if(eqmapper.selectOne(quid) != null) {
			LOGGER.info("Ext quest with [uuid = " + quid + "] already exists!");
			
			return;
		}
		
		quest.setIndexed(Constants.CONST_YES);
		eqmapper.insertExtQuest(quest);

		questRepo.save(extQuest2Doc(quest));
	}

	@Override
	public void updateExtQuestIndexed(String id) {
		eqmapper.updateExtQuestIndexed(id);
	}
	
	// KAFKA
	
	@KafkaListener(topics = "webminer-stackoverflow-quest", groupId = "flow-quest-stackoverflow-quest-migrate")
	public void soQuestMigrate(String msgstr) {
		LOGGER.info("In soQuestMigrate...Receive mesg data = " + msgstr);
		
		Message mesg = (Message)JsonUtils.fromJson(msgstr, Message.class);
		Map<String, Object> data = (Map<String, Object>)mesg.getData();
		
		// attr list -> map
		List attrs = (List)data.get("attrs");
		Map<String, String> attrsMap = new HashMap<>();
		for(Object attrObj : attrs) {
			Map<String, Object> attrMap = (Map<String, Object>)attrObj;
			
			String key = (String)attrMap.get("key"), val = (String)attrMap.get("val");
			// ignore quest without answer
			if("quest_top_reply".equals(key) && StringUtils.isNull(val)) return;
			
			// ignore quest with vote < 0
			if("quest_votes".equals(key) && Integer.parseInt(val) < 0) return;
			
			attrsMap.put(key, val);
		}

		ExtQuest quest = new ExtQuest();
		quest.setUuid(mesg.getKey());
		quest.setSite((String)data.get("site"));
		quest.setUrl((String)data.get("pageUrl"));
		quest.setTitle(attrsMap.get("quest_title"));
		quest.setContent(attrsMap.get("quest_content"));
		
		String questAns = attrsMap.get("quest_answer");
		quest.setAnswer(questAns == null ? attrsMap.get("quest_top_reply") : questAns);
		
		quest.setAnswerAccept("accepted".equals(attrsMap.get("quest_accepted")) ? 1 : 0);
		
		quest.setTags(attrsMap.get("quest_tags"));
		
		quest.setVotes(Integer.parseInt(attrsMap.get("quest_votes")));
		
		addExtQuest(quest);
	}
	
	/**
	 * 转换为Doc Quest作为索引
	 * 
	 * @param eq
	 * @return
	 */
	private DocQuest extQuest2Doc(ExtQuest eq) {
		DocQuest dq = new DocQuest();
		dq.setQuestId(eq.getId());
		dq.setUuid(eq.getUuid());
		dq.setTitle(eq.getTitle());
		dq.setContent(eq.getContent());
		dq.setTags(eq.getTags());
		dq.setVotes(eq.getVotes());
		dq.setExtSite(eq.getSite());
		dq.setExtUrl(eq.getUrl());
		dq.setAccepted(eq.getAnswerAccept());
		dq.setCreateDate(new Timestamp(System.currentTimeMillis()));
		
		return dq;
	}

}





























