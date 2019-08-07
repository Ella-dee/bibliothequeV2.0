package com.mmailing.microservicemailing;

import com.mmailing.microservicemailing.batch.TaskOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
public class MicroserviceMailingApplication implements CommandLineRunner {
	@Autowired
	TaskOne taskOne;

	public static void main(String[] args) {
		SpringApplication.run(
				MicroserviceMailingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		taskOne.checkAndSend();
	}


}
