package me.codetalk.flow.solv.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.codetalk.flow.fnd.pojo.FndUser;
import me.codetalk.flow.solv.Constants;
import me.codetalk.flow.solv.mapper.ExtQuestMapper;
import me.codetalk.flow.solv.pojo.ExtQuest;
import me.codetalk.flow.solv.service.IExtQuestService;
import me.codetalk.messaging.Message;
import me.codetalk.util.JsonUtils;

@Service("extQuestService")
public class ExtQuestServiceImpl implements IExtQuestService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExtQuestServiceImpl.class); 
	
	@Autowired
	private ExtQuestMapper eqmapper;
	
	@Override
	@Transactional
	public void addExtQuest(ExtQuest quest) {
		LOGGER.info("Add ext quest, id=" + quest.getId());
		
		quest.setIndexed(Constants.CONST_YES);
		eqmapper.insertExtQuest(quest);
		
		// TODO
	}

	@Override
	public void updateExtQuestIndexed(String id) {
		eqmapper.updateExtQuestIndexed(id);
	}
	
	// KAFKA
	
	@KafkaListener(topics = "webminer-stackoverflow-quest", groupId = "flow-quest-stackoverflow-quest-migrate")
	public void soQuestMigrate(String msgstr) {
		LOGGER.info("In soQuestMigrate...Receive mesg data = " + msgstr);
		
		Message message = (Message)JsonUtils.fromJson(msgstr, Message.class);
		Map<String, Object> data = (Map<String, Object>)message.getData();
		
		// TODO
	}

}
