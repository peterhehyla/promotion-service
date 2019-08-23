package com.hylamobile.promotion.service.impl;

import com.hylamobile.promotion.bean.CalculatedAmountBean;
import com.hylamobile.promotion.dto.PromotionSearchDTO;
import com.hylamobile.promotion.domain.Promotion;
import com.hylamobile.promotion.repository.PromotionRepository;
import com.hylamobile.promotion.search.PromotionSearchBeanBuilder;
import com.hylamobile.promotion.service.PromotionPostDBFilterService;
import com.hylamobile.promotion.service.PromotionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PromotionServiceImpl implements PromotionService {

    private Logger LOGGER = LoggerFactory.getLogger(PromotionServiceImpl.class);
    @Resource
    private PromotionRepository promotionRepository;
    @Resource
    private PromotionSearchBeanBuilder promotionSearchBeanBuilder;
    @Resource
    private PromotionPostDBFilterService promotionPostDBFilterService;

    @Override
    public List<CalculatedAmountBean<Promotion>> retrieveAvailablePromotions(PromotionSearchDTO searchBean) {
        List<Promotion> promotions = getValidPromotions(searchBean);
        return promotionPostDBFilterService.retrieveAvailablePromotions(searchBean, promotions);
    }

    @Override
    public List<Promotion> getValidPromotions(PromotionSearchDTO searchBean) {
        LOGGER.debug("getValidPromotions condition: {}", searchBean);
        searchBean = promotionSearchBeanBuilder.finalPreparePromotionSearchBean(searchBean);
        return promotionRepository.getAllValidPromotions(searchBean);
    }


}
