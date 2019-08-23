package com.hylamobile.promotion.service.impl;

import com.hylamobile.promotion.domain.PromotionTypeDomain;
import com.hylamobile.promotion.enums.PromotionType;
import com.hylamobile.promotion.repository.PromotionTypeRepository;
import com.hylamobile.promotion.service.PromotionTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
@Service
public class PromotionTypeServiceImpl implements PromotionTypeService {
    private final static Logger LOGGER = LoggerFactory.getLogger(PromotionTypeServiceImpl.class);
    @Resource
    private PromotionTypeRepository promotionTypeRepository;
    @Override
    public List<PromotionTypeDomain> findActivePromotionTypes() {
        LOGGER.debug("call findActivePromotionTypes");
        return promotionTypeRepository.findByActive(true);
    }

    @Override
    public PromotionTypeDomain findPromotionByCode(PromotionType code) {
        LOGGER.debug("call findPromotionByCode: code{}", code.name());
        List<PromotionTypeDomain> types = promotionTypeRepository.findByCode(code.name());
        if(CollectionUtils.isEmpty(types)){
            return null;
        }else{
            return types.get(0);
        }
    }

}
