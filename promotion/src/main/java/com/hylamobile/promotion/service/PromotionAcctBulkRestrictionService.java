package com.hylamobile.promotion.service;

import com.hylamobile.promotion.domain.Promotion;

public interface PromotionAcctBulkRestrictionService {
    boolean isImeiValidForPromotionTac(Promotion promotion, String tac);
    boolean isValidMtnForPromotion(Promotion promotion, String mtn);
    boolean isValidNewDeviceModelForPromotion(Promotion promotion, String newDeviceModel);
}
