package me.codetalk.flow.miner.main;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "me.codetalk.flow.miner.runner",
        "me.codetalk.messaging.kafka.impl"
})
@MapperScan(value = {"me.codetalk.flow.miner.mapper"})
public class MinerMain {

	private static Logger LOGGER = LoggerFactory.getLogger(MinerMain.class);
	
	public static void main(String[] args) throws Exception {
        SpringApplication.run(MinerMain.class, args);

        LOGGER.debug("Miner server started...");

        System.in.read();
    }
	
}
