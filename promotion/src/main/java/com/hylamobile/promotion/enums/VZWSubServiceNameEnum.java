package com.hylamobile.promotion.enums;

import java.util.Arrays;

public enum VZWSubServiceNameEnum {
    SPO_ADD(VerizonGlobalServiceEnum.SPO_MAINTENANCE_COMPOSITE, "add"), SPO_DELETE(VerizonGlobalServiceEnum.SPO_MAINTENANCE_COMPOSITE,"delete"),
    EQUIPMENT_PROMISED_VALUE_INSERT_UPDATE(VerizonGlobalServiceEnum.EQUIPMENT_PROMISED_VALUE, "insertUpdate"),
    DETAIL_BOGO_INFO(VerizonGlobalServiceEnum.INSTALLMENT_LOAN_MAINTENANCE, "detailBOGOInfo"),
    DETAILS_INQUIRY(VerizonGlobalServiceEnum.INSTALLMENT_LOAN_MAINTENANCE, "detailsInquiry"),
    BUY_GET_LOAN_CREATE(VerizonGlobalServiceEnum.INSTALLMENT_LOAN_MAINTENANCE, "buyGetLoanCreate"),
    END_PROMOTION_CREDITS(VerizonGlobalServiceEnum.PROMOTION_CREDIT_MAINTENANCE, "endPromoCredits");

    private String subServiceName;
    //this is as indicator. which service using this subservice name
    private VerizonGlobalServiceEnum verizonGlobalServiceEnum;

    VZWSubServiceNameEnum(VerizonGlobalServiceEnum verizonGlobalServiceEnum, String subServiceName){
        this.verizonGlobalServiceEnum = verizonGlobalServiceEnum;
        this.subServiceName = subServiceName;
    }

    public String getSubServiceName() {
        return subServiceName;
    }

    public static VZWSubServiceNameEnum getSubServiceNameByName(String subServiceName){
        return Arrays.stream(VZWSubServiceNameEnum.values()).filter(p->p.getSubServiceName().equals(subServiceName)).findFirst().orElse(null);
    }
}

