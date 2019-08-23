package com.hylamobile.promotion.repository;

import com.hylamobile.promotion.domain.Promotion;
import com.hylamobile.promotion.domain.PromotionAcctBulkRestriction;
import com.hylamobile.promotion.enums.CriterionCode;

import java.util.List;

public interface PromotionAcctBulkRestrictionRepository {
    boolean isImeiValidForPromotionTac(Promotion promotion, String tac);
    boolean hasValueForPromotion(long promotionId, String value, CriterionCode code);
    List<PromotionAcctBulkRestriction> findActivePromotionAcctBulkRestrictionForPromotion
            (Long promotionId, CriterionCode code);
}
