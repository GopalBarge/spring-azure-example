package com.sal.prompt.web;

import com.sal.prompt.web.client.rest.RestClient;
import com.sal.prompt.web.client.soap.SoapClient;
import com.sal.prompt.web.model.SupplierEnum;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableCaching
public class ApplicationRunner {

    public static void main(String[] args) {
//		System.out.println(" = " + getGroupId("AS400_PO_Oracle_BatchID.zip"));
        ConfigurableApplicationContext context = SpringApplication.run(ApplicationRunner.class);
        //Load caches
        context.getBean(RestClient.class).getLookups();
        context.getBean(SoapClient.class).getSupplierDetails(SupplierEnum.RECEIPT.name());
        context.getBean(SoapClient.class).getSupplierDetails(SupplierEnum.PO.name());
        context.getBean(SoapClient.class).getSupplierDetails(SupplierEnum.INVOICE.name());
        context.getBean(SoapClient.class).getOpenPODetails();


    }


}
