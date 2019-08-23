package com.hylamobile.promotion.repository;

import com.hylamobile.promotion.domain.CategoryQuestionResponsePromotion;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

public interface CategoryQuestionResponsePromotionRepository {
    Map<Long, SortedSet<CategoryQuestionResponsePromotion>> findByPromotionIdsOrderByPromotionId(Set<Long> promotionIds);
}
