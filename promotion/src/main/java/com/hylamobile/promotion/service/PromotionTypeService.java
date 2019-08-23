package com.hylamobile.promotion.service;

import com.hylamobile.promotion.domain.PromotionTypeDomain;
import com.hylamobile.promotion.enums.PromotionType;

import java.util.List;

public interface PromotionTypeService {
    List<PromotionTypeDomain> findActivePromotionTypes();
    PromotionTypeDomain findPromotionByCode(PromotionType code);
}
