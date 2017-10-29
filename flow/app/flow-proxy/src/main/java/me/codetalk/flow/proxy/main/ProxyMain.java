package me.codetalk.flow.proxy.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@SpringBootApplication
@ComponentScan(value = {
		"me.codetalk.mesg",
		"me.codetalk.storage.service.impl",
		"me.codetalk.param.checker.impl",
		"me.codetalk.flow.proxy.impl"
})
@ImportResource("classpath:dubbo-config.xml")
public class ProxyMain {
	
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ProxyMain.class, args);
    }
    
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:locale/messages");
        messageSource.setCacheSeconds(3600); //refresh cache once per hour
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
    
}
