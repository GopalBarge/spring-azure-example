package com.sal.prompt.web;

import com.sal.prompt.web.client.rest.RestClient;
import com.sal.prompt.web.client.soap.SoapClient;
import com.sal.prompt.web.client.soap.stub.RunReport;
import com.sal.prompt.web.model.SupplierEnum;
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
		//Load caches
		context.getBean(RestClient.class).getLookup();
		context.getBean(SoapClient.class).getSupplierDetails(SupplierEnum.RECEIPT.name());
		context.getBean(SoapClient.class).getSupplierDetails(SupplierEnum.PO.name());
		context.getBean(SoapClient.class).getSupplierDetails(SupplierEnum.INVOICE.name());


	}


}
