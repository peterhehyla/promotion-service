package com.hylamobile.promotion.repository;

public interface PromotionEsnRepository {
    boolean isValidEsnForPromotion(final Long promotionId, final String esn);
}
