package com.mbooks.microservicebooks;

import com.mbooks.microservicebooks.batch.TaskOne;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class MicroserviceBooksApplication implements CommandLineRunner {
	/*@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job job;*/

	@Autowired
	TaskOne taskOne;
	public static void main(String[] args) {
		SpringApplication.run(
				MicroserviceBooksApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception
	{
		taskOne.checkAndSet();
		/*JobParameters params = new JobParametersBuilder()
				.addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		jobLauncher.run(job, params);*/
	}


}
