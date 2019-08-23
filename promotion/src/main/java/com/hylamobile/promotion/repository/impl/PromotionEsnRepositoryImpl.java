package com.hylamobile.promotion.repository.impl;

import com.hylamobile.promotion.repository.PromotionEsnRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import java.util.HashMap;
import java.util.Map;

public class PromotionEsnRepositoryImpl extends NamedParameterJdbcDaoSupport implements PromotionEsnRepository {
    private final static Logger LOGGER = LoggerFactory.getLogger(PromotionEsnRepositoryImpl.class);
    private final static String COUNT_BY_PROMOTIONID_AND_ESN_QUERY = "select count(0) from ref_promotion_esn where promotionid=:promotionId and esn=:esn";

    @Override public boolean isValidEsnForPromotion(Long promotionId, String esn) {
        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("promotionId", promotionId);
        namedParameters.put("esn", esn);
        return getNamedParameterJdbcTemplate().queryForObject(COUNT_BY_PROMOTIONID_AND_ESN_QUERY, namedParameters,
               Integer.class)>0;
    }
}
