package com.hylamobile.promotion.repository;

import com.hylamobile.promotion.dto.PromotionSearchDTO;
import com.hylamobile.promotion.domain.Promotion;

import java.util.List;

public interface PromotionRepository {
    List<Promotion> getAllValidPromotions(PromotionSearchDTO searchBean);
}
