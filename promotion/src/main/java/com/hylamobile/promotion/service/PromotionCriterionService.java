package com.hylamobile.promotion.service;

import com.hylamobile.promotion.domain.PromotionCriterion;
import com.hylamobile.promotion.domain.PromotionCriterionValue;
import com.hylamobile.promotion.dto.CustomerDTO;
import com.hylamobile.promotion.dto.PromotionQualifierDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PromotionCriterionService {
    Map<PromotionCriterion, Set<PromotionCriterionValue>> getDisplayablePromotionCriteriaValuesByCriterion();

    Map<PromotionCriterion, Set<PromotionCriterionValue>> getPromotionCriteriaValuesByCriterion();

    List<PromotionCriterion> getDisplayablePromotionCriteriaValues();
    Set<PromotionQualifierDTO> findPromotionQualifier(CustomerDTO customer);
}
