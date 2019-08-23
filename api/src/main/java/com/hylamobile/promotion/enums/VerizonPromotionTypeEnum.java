package com.hylamobile.promotion.enums;

import java.util.Arrays;

public enum VerizonPromotionTypeEnum {
    //only FRP, BOGO, BUY_MORE_SAVE_MORE are in DetailBogoInfo API
    FRP("TR","No Contract/Full Retail"), BOGO("BG"), BUY_MORE_SAVE_MORE("BM"), DPP("DPP", "DPP"), TWO_YEAR("TWO_YEAR", "2 Years");
    private String type;
    private String contractType;

    VerizonPromotionTypeEnum(String type){
        this.type = type;
    }

    VerizonPromotionTypeEnum(String type, String contractType){
        this.type = type;
        this.contractType = contractType;
    }

    public String getType() {
        return type;
    }

    public static VerizonPromotionTypeEnum getPromotionTypeByValue(String type){
        return Arrays.stream(VerizonPromotionTypeEnum.values()).filter(p->p.getType().equals(type)).findFirst().orElse(null);
    }

    public static VerizonPromotionTypeEnum getPromotionTypeByContractType(String contractType){
        return Arrays.stream(VerizonPromotionTypeEnum.values()).filter(p->contractType!=null && contractType.equals(p.contractType)).findFirst().orElse(null);
    }

}
