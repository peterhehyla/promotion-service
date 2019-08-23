package com.hylamobile.promotion.service;

import com.hylamobile.promotion.bean.CalculatedAmountBean;
import com.hylamobile.promotion.domain.Promotion;
import com.hylamobile.promotion.dto.PromotionSearchDTO;

import java.util.Collection;
import java.util.List;

public interface PromotionPostDBFilterService {
    //post filter after Database Search getValidPromotions
    List<CalculatedAmountBean<Promotion>> retrieveAvailablePromotions(PromotionSearchDTO searchBean,
            Collection<Promotion> allAvailablePromotions);
}
