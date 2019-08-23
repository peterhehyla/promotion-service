package com.hylamobile.promotion.repository.impl;

import com.hylamobile.promotion.domain.ModelPromotion;
import com.hylamobile.promotion.repository.ModelPromotionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ModelPromotionRepositoryImpl extends NamedParameterJdbcDaoSupport implements ModelPromotionRepository {
    private final static Logger LOGGER = LoggerFactory.getLogger(ModelPromotionRepositoryImpl.class);
    private final static String FIND_MODEL_PROMOTION_BY_PROMOTION_IDS_QUERY = "select * from fs_model_promotion where promotionid in (:promotionIds) and createddate<:promotionDate and enddate is null or enddate>=:promotionDate order by promotionid";
    @Override public List<ModelPromotion> findModelPromotionsByPromotionIdsOrderByPromotionId(Set<Long> promotionIds, Date promotionDate) {
        LOGGER.debug("prmotion ids: {}, promotiondate: {}", promotionIds, promotionDate);
        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("promotionIds", promotionIds);
        namedParameters.put("promotionDate", promotionDate);
        return getNamedParameterJdbcTemplate().query(FIND_MODEL_PROMOTION_BY_PROMOTION_IDS_QUERY, namedParameters, new BeanPropertyRowMapper(ModelPromotion.class));
    }
}
