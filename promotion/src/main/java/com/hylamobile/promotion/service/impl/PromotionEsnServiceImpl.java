package com.hylamobile.promotion.service.impl;

import com.hylamobile.promotion.repository.PromotionEsnRepository;
import com.hylamobile.promotion.service.PromotionEsnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PromotionEsnServiceImpl implements PromotionEsnService {
    private final static Logger LOGGER = LoggerFactory.getLogger(PromotionEsnServiceImpl.class);
    @Resource
    private PromotionEsnRepository promotionEsnRepository;
    @Override public boolean isValidEsnForPromotion(Long promotionId, String esn) {
        return promotionEsnRepository.isValidEsnForPromotion(promotionId, esn);
    }

}
