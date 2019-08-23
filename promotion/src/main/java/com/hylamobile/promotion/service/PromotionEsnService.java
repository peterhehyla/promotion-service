package com.hylamobile.promotion.service;


public interface PromotionEsnService {
    boolean isValidEsnForPromotion(Long promotionId, String esn);
}
