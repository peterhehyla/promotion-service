package com.hylamobile.promotion.enums;

public enum RetrieveOffersReturnEnum {
    ERROR(false), NO_RESPONSE(false), HAS_DDP_PROMOTION(false), NO_DPP_PROMOTION(true), MISSING_REQUEST_DATA(false);
    private boolean spoEligible;

    RetrieveOffersReturnEnum(boolean spoEligible){
        this.spoEligible = spoEligible;
    }

    public boolean isSpoEligible() {
        return spoEligible;
    }

}
