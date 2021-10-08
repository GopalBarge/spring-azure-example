package com.sal.prompt.web.utils;

public enum InterfaceEnum {
    AS400_PO("SAL-FIN-I-180"),
    AS400_RECEIPT("SAL-FIN-I-181"),
    AS400_INVOICE("SAL-FIN-I-182"),
    DSD_PO("SAL-FIN-I-183"),
    DSD_RECEIPT("SAL-FIN-I-184"),
    DSD_HANDHELD_INVOICE("SAL-FIN-I-188");

    private String name;
    InterfaceEnum(String s) {
        this.name = s;
    }
}
