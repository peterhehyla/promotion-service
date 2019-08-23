package com.hylamobile.promotion.enums;

public enum CriterionCode {PLAN, CONTRACT_TERM, LOAN_NUMBER, ORDER_REQUEST, FEATURE, CONTRACT_TYPE, UPGRADE_REASON, NEW_DEVICE_MODEL(10), PORT_IN, TAC(100), MTN(101);
    private long criterionId;
    private CriterionCode(){

    }
    private CriterionCode(int criterionId){
        this.criterionId = criterionId;
    }

    public long getCriterionId() {
        return criterionId;
    }
}
