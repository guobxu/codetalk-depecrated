package me.codetalk.messaging.rabbit.aspect;

//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//
//import me.codetalk.messaging.Message;
//import me.codetalk.messaging.rabbit.aspect.annotation.PostMessage;
//import me.codetalk.util.JsonUtils;

//@Aspect
//@Configuration
public class PostMessageAspect {

//	@Autowired
//	private RabbitTemplate rabbitTemplate;
//	
//	@AfterReturning(value = "execution(@me.codetalk.amqp.aspect.annotation.PostMessage * *(..)) "
//			+ "&& @annotation(annot)", 
//			returning = "ret")
//	public void postSendMsg(PostMessage annot, Object ret) {
//		Message mesg = new Message();
//		mesg.setApp(annot.app());
//		mesg.setModule(annot.module());
//		mesg.setData(JsonUtils.toMap(ret));
//		
//		rabbitTemplate.convertAndSend(annot.value(), null, mesg);
//	}
	
}
