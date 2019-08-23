package com.hylamobile.promotion.repository;

import com.hylamobile.promotion.domain.ModelPromotion;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface ModelPromotionRepository {
    List<ModelPromotion> findModelPromotionsByPromotionIdsOrderByPromotionId(Set<Long> promotionIds, Date promotionDate);
}
