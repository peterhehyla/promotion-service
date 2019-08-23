package com.hylamobile.promotion.service.impl;

import com.hylamobile.promotion.domain.Promotion;
import com.hylamobile.promotion.domain.PromotionAcctBulkRestriction;
import com.hylamobile.promotion.enums.CriterionCode;
import com.hylamobile.promotion.repository.PromotionAcctBulkRestrictionRepository;
import com.hylamobile.promotion.service.PromotionAcctBulkRestrictionService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class PromotionAcctBulkAcctBulkRestrictionServiceImpl implements PromotionAcctBulkRestrictionService {
    private final static Logger LOGGER = LoggerFactory.getLogger(PromotionAcctBulkAcctBulkRestrictionServiceImpl.class);
    @Resource
    private PromotionAcctBulkRestrictionRepository promotionRestrictionRepository;
    @Override
    public boolean isImeiValidForPromotionTac(Promotion promotion, String tac) {
        if(StringUtils.isEmpty(tac) || tac.length() < 8){
            return false;
        }

        // TAC is first 8 digits of IMEI
        String trimValue = tac.substring(0, 8);
        return promotionRestrictionRepository.isImeiValidForPromotionTac(promotion, trimValue);
    }

    @Override public boolean isValidMtnForPromotion(Promotion promotion, String mtn) {
        Objects.requireNonNull(promotion, "Promotion can't be null");
        if (!Boolean.TRUE.equals(promotion.getMtnValidationRequired())) {
            return true;
        }
        LOGGER.debug("isValidMtnForPromotion: mtn {}", mtn);
        return promotionRestrictionRepository.hasValueForPromotion(promotion.getPromotionId(), mtn, CriterionCode.MTN);
    }

    @Override public boolean isValidNewDeviceModelForPromotion(Promotion promotion, String newDeviceModel) {
        List<PromotionAcctBulkRestriction> restrictions = promotionRestrictionRepository.findActivePromotionAcctBulkRestrictionForPromotion(promotion.getPromotionId(), CriterionCode.NEW_DEVICE_MODEL);
        LOGGER.debug("findActivePromotionAcctBulkRestrictionForPromotion: {}", restrictions);
        if(CollectionUtils.isNotEmpty(restrictions)){
            return StringUtils.isNotBlank(newDeviceModel)?restrictions.stream().anyMatch(r->r.getValue().equals(newDeviceModel)):false;
        }
        return true;
    }
}
