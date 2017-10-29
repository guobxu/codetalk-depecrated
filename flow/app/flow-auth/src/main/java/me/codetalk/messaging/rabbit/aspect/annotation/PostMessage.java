package me.codetalk.messaging.rabbit.aspect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PostMessage {

	// exchange
	@AliasFor("exchange")
	String value() default "";
	
	// application
	String app() default "";
	
	// module
	String module() default "";
	
}
