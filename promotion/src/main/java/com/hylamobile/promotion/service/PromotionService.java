package com.hylamobile.promotion.service;

import com.hylamobile.promotion.bean.CalculatedAmountBean;
import com.hylamobile.promotion.dto.PromotionSearchDTO;
import com.hylamobile.promotion.domain.Promotion;

import java.util.Collection;
import java.util.List;

public interface PromotionService {
    List<Promotion> getValidPromotions(final PromotionSearchDTO searchBean);
    //post filter after getValidPromotions
    List<CalculatedAmountBean<Promotion>> retrieveAvailablePromotions(PromotionSearchDTO searchBean);
}
