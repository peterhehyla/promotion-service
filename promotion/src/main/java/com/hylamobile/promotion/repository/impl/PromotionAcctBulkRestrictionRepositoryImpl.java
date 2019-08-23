package com.hylamobile.promotion.repository.impl;

import com.hylamobile.promotion.domain.Promotion;
import com.hylamobile.promotion.domain.PromotionAcctBulkRestriction;
import com.hylamobile.promotion.enums.CriterionCode;
import com.hylamobile.promotion.repository.PromotionAcctBulkRestrictionRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Service;

import java.util.List;

public class PromotionAcctBulkRestrictionRepositoryImpl extends NamedParameterJdbcDaoSupport implements
        PromotionAcctBulkRestrictionRepository {
    @Override public boolean isImeiValidForPromotionTac(Promotion promotion, String tac) {
        if (!promotion.getHasTac()) {
            return false;
        }
        return hasValueForPromotion(promotion.getPromotionId(), tac, CriterionCode.TAC);
    }
    @Override
    public boolean hasValueForPromotion(long promotionId, String value, CriterionCode code){
        String hqlStatement = "select count(*) from ref_promotion_acct_bulk_restrict where promotionId = ? and value = ? and promotionCriterionId=? and enddate is null";

        return getJdbcTemplate().queryForObject(hqlStatement,
                new Object[]{promotionId,value, code.getCriterionId()}, Integer.class)>0;

    }
    @Override
    public List<PromotionAcctBulkRestriction> findActivePromotionAcctBulkRestrictionForPromotion
            (Long promotionId, CriterionCode code){

        String hqlStatement = "select * from ref_promotion_acct_bulk_restrict where promotionid = ? and promotioncriterionid=? and enddate is null";

        return  getJdbcTemplate().query(hqlStatement,
                new Object[]{promotionId, code.getCriterionId()},
                new BeanPropertyRowMapper(PromotionAcctBulkRestriction.class));
    }
}
