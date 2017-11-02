package me.codetalk.flow.miner.runner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import me.codetalk.flow.miner.Constants;
import me.codetalk.flow.miner.mapper.WebEntityMapper;
import me.codetalk.flow.miner.pojo.WebEntityVO;
import me.codetalk.messaging.kafka.IMessagingService;

/**
 * Send web entities to kafka broker 
 * 
 * @author guobxu
 *
 */
//@Component
public class KafkaMsgRunner implements CommandLineRunner {

	@Autowired 
	private WebEntityMapper wemapper;
	
	@Autowired
	private IMessagingService messagingService;
	
	@Override
	public void run(String... args) throws Exception {
		for(int i = 0; ; i++) {
			int begin = i * 100, count = 100;
			List<WebEntityVO> entityList = wemapper.selectEntity(begin, count);
			
			if(entityList.isEmpty()) return;
			
			for(WebEntityVO entity : entityList) {
				String topic = getTopic(entity);
				
				messagingService.sendMessage(topic, entity);
			}
		}
	}
	
	private String getTopic(WebEntityVO we) {
		return Constants.KAFKA_TOPIC_PREFIX + we.getSite() + "-" + we.getEntityType();
	}

	
	
}
