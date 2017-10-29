package me.codetalk.messaging.kafka.aspect;

import java.util.UUID;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.kafka.core.KafkaTemplate;

import me.codetalk.messaging.Message;
import me.codetalk.messaging.kafka.aspect.annotation.KafkaAfter;
import me.codetalk.util.JsonUtils;

@Aspect
public class KafkaAfterAspect {

	private KafkaTemplate<String, String> kafkaTemplate;
	
	@AfterReturning(value = "execution(@me.codetalk.messaging.kafka.aspect.annotation.KafkaAfter * *(..)) "
			+ "&& @annotation(annot)", returning = "ret")
	public void mesgAfter(KafkaAfter annot, Object ret) {
		Message mesg = new Message();
		
		String key = UUID.randomUUID().toString();
		mesg.setKey(key);
		
		mesg.setApp(annot.app());
		mesg.setModule(annot.module());
		mesg.setData(JsonUtils.toMap(ret));
		
		String topic = annot.value();
		int partition = annot.partition();
		if(partition == -1) {
			kafkaTemplate.send(topic, key, JsonUtils.toJson(mesg));
		} else {
			kafkaTemplate.send(topic, partition, key, JsonUtils.toJson(mesg));
		}
	}
	
	public void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
}
