package com.hylamobile.promotion.search;

import com.hylamobile.promotion.domain.PromotionTypeDomain;
import com.hylamobile.promotion.dto.PromotionSearchDTO;
import com.hylamobile.promotion.enums.PromotionType;
import com.hylamobile.promotion.repository.CompanyRepository;
import com.hylamobile.promotion.service.PromotionTypeService;
import com.hylamobile.promotion.utils.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

@Service
public class PromotionSearchBeanBuilder {
    private final static Logger LOGGER = LoggerFactory.getLogger(PromotionSearchBeanBuilder.class);
    @Resource
    private CompanyRepository companyRepository;
    @Resource
    private PromotionTypeService promotionTypeService;

    public PromotionSearchDTO finalPreparePromotionSearchBean(PromotionSearchDTO searchBean){
        if (searchBean.getActive() == null && searchBean.getDate() == null) {
            searchBean.setActive(Boolean.TRUE);
        }

        if (StringUtils.isBlank(searchBean.getTimezone())) {
            searchBean.setTimezone(companyRepository.findById(searchBean.getAffectedCompanyId()).get().getState().getTimeZone());
        }

        applyUserTimezone(searchBean);
        applyCacheRefreshOffset(searchBean);
        applyOpusRestrictionIfNeeded(searchBean);
        return searchBean;
    }
    private void applyUserTimezone(final PromotionSearchDTO searchBean) {
        final TimeZone timeZone = TimeZone.getTimeZone(searchBean.getTimezone());
        if (searchBean.getEndDate() != null && searchBean.getStartDate() != null) {
            searchBean.setMinStartDate(CommonUtils.getDateInTimezone(searchBean.getStartDate(), timeZone));
            searchBean.setMaxEndDate(CommonUtils.getDateInTimezone(searchBean.getEndDate(), timeZone));
        } else {
            Date currentUserDate = CommonUtils.getDateInTimezone(
                    searchBean.getDate() != null ? searchBean.getDate() : new Date(), timeZone);
            searchBean.setMinStartDate(currentUserDate);
            searchBean.setMaxEndDate(currentUserDate);
        }
    }
    private void applyOpusRestrictionIfNeeded(PromotionSearchDTO searchBean) {
        if (searchBean.isOpus()) {
            PromotionTypeDomain promotionTypeDomain = promotionTypeService.findPromotionByCode(PromotionType.BILL_CREDIT);
            if(Objects.nonNull(promotionTypeDomain) && promotionTypeDomain.getActive()) {
                if (searchBean.getTypes() != null) {
                    searchBean.getTypes().add(PromotionType.BILL_CREDIT);
                } else {
                    searchBean.setType(PromotionType.BILL_CREDIT);
                }
            }
        }
    }

    /**
     * add 2 hours caching
     * @param searchBean
     */
    private void applyCacheRefreshOffset(final PromotionSearchDTO searchBean) {
        Date userStartDate = searchBean.getMinStartDate();
        long invalidationIntervalInMillis = 2 * 60 * 60 * 1000;
        searchBean.setMinStartDate(new Date(userStartDate.getTime() + invalidationIntervalInMillis));
    }
}
