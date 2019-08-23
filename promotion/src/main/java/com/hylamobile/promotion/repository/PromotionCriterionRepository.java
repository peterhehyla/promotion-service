package com.hylamobile.promotion.repository;

import com.hylamobile.promotion.domain.PromotionCriterion;

import java.util.Collection;
import java.util.List;

public interface PromotionCriterionRepository {
    List<PromotionCriterion> findByCriterionDisplayTypeNotNullAndActive(boolean active);
    List<PromotionCriterion> getPromotionCriteria();
}
