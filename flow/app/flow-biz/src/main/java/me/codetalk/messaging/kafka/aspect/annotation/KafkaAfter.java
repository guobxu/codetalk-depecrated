package me.codetalk.messaging.kafka.aspect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface KafkaAfter {

	@AliasFor("topic")
	String value() default "";
	
	int partition() default -1; // -1 表示不指定分区
	
	// application
	String app() default "";
	
	// module
	String module() default "";
	
}
