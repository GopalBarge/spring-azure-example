package com.sal.prompt.web;

import com.sal.prompt.web.client.rest.RestClient;
import com.sal.prompt.web.client.soap.SoapClient;
import com.sal.prompt.web.model.POLookupEnum;
import com.sal.prompt.web.model.POTypesEnum;
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
        context.getBean(SoapClient.class).getSupplierDetails(SupplierEnum.INVOICE_880.name());
        context.getBean(SoapClient.class).getSupplierDetails(SupplierEnum.HANDHELD_INVOICE.name());
        context.getBean(SoapClient.class).getOpenDetails(POTypesEnum.AS400);
        context.getBean(SoapClient.class).getOpenDetails(POTypesEnum.DSD_RECPT);
        context.getBean(SoapClient.class).getOpenDetails(POTypesEnum.HANDHELD);


    }


}
