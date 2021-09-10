package com.sal.prompt.web;

import com.sal.prompt.web.client.soap.stub.RunReport;
import com.sal.prompt.web.service.ReferenceDataService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableCaching
public class ApplicationRunner {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ApplicationRunner.class);
		context.getBean(ReferenceDataService.class).getPOLookup();
//		context.getBean(ReferenceDataService.class).getSupplierDetails();

	}


}
