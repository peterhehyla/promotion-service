package com.hylamobile.promotion.enums;

public enum VerizonGlobalServiceEnum {
    EQUIPMENT_PROMISED_VALUE("equipmentPromisedValue"),
    SPO_MAINTENANCE_COMPOSITE("serviceProductOfferMaintenanceComposite"),
    INSTALLMENT_LOAN_MAINTENANCE("installmentLoanMaintenance"),
    RETRIEVE_OFFERS("retrieveOffers"),
    EQUIPMENT_UPGRADE_REASON_CODE("equipmentUpgradeSearch"),
    PROMOTION_CREDIT_MAINTENANCE("promotionCreditMaintenance");
    private String serviceName;

    VerizonGlobalServiceEnum(String serviceName){
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }
}

