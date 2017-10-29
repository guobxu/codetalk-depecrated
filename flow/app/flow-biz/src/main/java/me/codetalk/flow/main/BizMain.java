package me.codetalk.flow.main;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@SpringBootApplication
@ComponentScan(basePackages = {
        "me.codetalk.mesg",
        "me.codetalk.cache.config",
		"me.codetalk.cache.service.impl",
        "me.codetalk.flow.*.service.impl",
        "me.codetalk.flow.auth.support"
})
@MapperScan(value = {"me.codetalk.flow.*.mapper"})
@ImportResource(locations = {
		"classpath:dubbo-config.xml",
		"classpath:aspectj-config.xml"
})
public class BizMain {

	private static Logger LOGGER = LoggerFactory.getLogger(BizMain.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(BizMain.class, args);

        LOGGER.debug("Biz server started...");

        System.in.read();
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

