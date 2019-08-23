package com.hylamobile.promotion.repository.impl;

import com.hylamobile.promotion.domain.PromotionCriterion;
import com.hylamobile.promotion.domain.PromotionCriterionValue;
import com.hylamobile.promotion.enums.CriterionCode;
import com.hylamobile.promotion.enums.PromotionCriterionType;
import com.hylamobile.promotion.exceptions.DBRuntimeException;
import com.hylamobile.promotion.repository.PromotionCriterionRepository;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class PromotionCriterionRepositoryImpl extends NamedParameterJdbcDaoSupport implements PromotionCriterionRepository {
    private final static Logger LOGGER = LoggerFactory.getLogger(PromotionCriterionRepositoryImpl.class);

    private final static String LOAD_CHILD_PROMOTION_CRITERION_VALUE = "select pcvc.*,pcv.promotioncriterionvalueid parentValueId from ref_promotion_criterion_value pcv join ref_promotion_criterion_value_group pcvg on pcv.promotioncriterionvalueid=pcvg.parentpromotioncriterionvalueid "
            +"join ref_promotion_criterion_value pcvc on pcvc.promotioncriterionvalueid=pcvg.childpromotioncriterionvalueid "
            +"where pcv.promotioncriterionvalueid in (:parentValueIds) order by pcv.promotioncriterionvalueid";
    @Override public List<PromotionCriterion> findByCriterionDisplayTypeNotNullAndActive(boolean active) {
        String query = "select pc.*,pcu.promotioncriterionvalueid,pcu.description valueDescription,pcu.code valueCode,pct.name promotionCriterionType from ref_promotion_criterion pc "
                + "join ref_promotion_criterion_type pct on pct.promotioncriteriontypeid=pc.promotioncriteriontypeid "
                + "left join ref_promotion_criterion_value pcu on pc.promotioncriterionid=pcu.promotioncriterionid and pcu.active=true "
                + "where pc.active=true and pc.displaytype is not null order by pc.promotioncriterionid,pc.sortorder";
        return executeQuery(query, new Object[]{});
    }

    @Cacheable(value = "promotions")
    @Override public List<PromotionCriterion> getPromotionCriteria(){
        String query = "select pc.*,pcu.promotioncriterionvalueid,pcu.description valueDescription,pcu.code valueCode,pct.name promotionCriterionType from ref_promotion_criterion pc "
                + "join ref_promotion_criterion_type pct on pct.promotioncriteriontypeid=pc.promotioncriteriontypeid "
                + "left join ref_promotion_criterion_value pcu on pc.promotioncriterionid=pcu.promotioncriterionid and pcu.active=true "
                + "where pc.active=true order by pc.promotioncriterionid,pc.sortorder";
        return executeQuery(query, new Object[]{});
    }
    private List<PromotionCriterion> executeQuery(String query, Object[] parameters){
        PromotionCriterionRowCallbackHandler handler = new PromotionCriterionRowCallbackHandler();
        getJdbcTemplate().query(query,
                parameters,
                handler);
        List<PromotionCriterion> criterions = handler.getPromotionCriterions();

        if(CollectionUtils.isEmpty(criterions)){
            return criterions;
        }

        List<PromotionCriterionValue> parentValues = criterions.stream().flatMap(c->c.getValues().stream()).collect(Collectors.toList());
        Set<Long> parentValueIds = parentValues.stream().map(PromotionCriterionValue::getPromotionCriterionValueId).collect(
                Collectors.toSet());
        Map<Long, Set<PromotionCriterionValue>> childValueMap = getChildrenValue(parentValueIds);
        Iterator<Map.Entry<Long, Set<PromotionCriterionValue>>> childIterator = childValueMap.entrySet().iterator();
        while(childIterator.hasNext()){
            Map.Entry<Long, Set<PromotionCriterionValue>> child = childIterator.next();
            parentValues.stream().filter(v->v.getPromotionCriterionValueId().equals(child.getKey())).forEach(p->p.addChildCriterionValues(child.getValue()));
        }
        return criterions;
    }
    private Map<Long, Set<PromotionCriterionValue>> getChildrenValue(Set<Long> parentValues){
        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("parentValueIds", parentValues);
        PromotionCriterionChildValueRowCallbackHandler handler = new PromotionCriterionChildValueRowCallbackHandler();
        getNamedParameterJdbcTemplate().query(LOAD_CHILD_PROMOTION_CRITERION_VALUE,
                namedParameters,
                handler);
        return handler.getPromotionCriterionValueMap();
    }

    private class PromotionCriterionChildValueRowCallbackHandler implements RowCallbackHandler{
        private Map<Long, Set<PromotionCriterionValue>> promotionCriterionValueMap = new HashMap<>();
        @Override
        public void processRow(ResultSet rs) throws SQLException {
            Long parentValueId = rs.getLong("parentValueId");
            Set<PromotionCriterionValue> values = promotionCriterionValueMap.computeIfAbsent(parentValueId, k->createAndMapoPromotionCriterionValue(k, rs));
            //since left join, PromotionCriterionValue could be null
            if(!rs.wasNull()) {
                PromotionCriterionValue promotionCriterionValue = mapToPromotionCriterionValue(parentValueId, rs);
                values.add(promotionCriterionValue);
            }
        }
        private Set<PromotionCriterionValue> createAndMapoPromotionCriterionValue(Long parentValueId, ResultSet rs){
            Set<PromotionCriterionValue> values = new HashSet<>();
            values.add(mapToPromotionCriterionValue(parentValueId, rs));
            return values;
        }
        private PromotionCriterionValue mapToPromotionCriterionValue(Long parentValueId, ResultSet rs){
            PromotionCriterionValue value = new PromotionCriterionValue();
            try {
                value.setPromotionCriterionValueId(rs.getLong("promotioncriterionvalueid"));
                value.setCreatedBy(rs.getLong("createdby"));
                value.setCreatedDate(rs.getTimestamp("createddate"));
                value.setDescription(rs.getString("description"));
                value.setCode(rs.getString("code"));
                value.setVersion(rs.getLong("version"));
                value.setLastUpdatedDate(rs.getTimestamp("lastupdateddate"));
                value.setUpdatedBy(rs.getLong("updatedby"));
                value.setSortOrder(rs.getInt("sortorder"));
            }catch(Throwable ex){
                LOGGER.error("PromotionCriterionChildValue error in read from resultset: " + parentValueId, ex);
                throw new DBRuntimeException(ex);
            }
            return value;
        }

        public Map<Long, Set<PromotionCriterionValue>> getPromotionCriterionValueMap() {
            return promotionCriterionValueMap;
        }
    }

    private class PromotionCriterionRowCallbackHandler implements RowCallbackHandler {
        private Map<Long, PromotionCriterion> promotionCriterionMap = new HashMap<>();
        @Override
        public void processRow(ResultSet rs) throws SQLException {
            Long promotionCriterionId = rs.getLong("promotioncriterionid");
            PromotionCriterion promotionCriterion = promotionCriterionMap.computeIfAbsent(promotionCriterionId, k->mapToPromotionCriterion(k, rs));
            //since left join, PromotionCriterionValue could be null
            long promotionCriterionValueId = rs.getLong("promotioncriterionvalueid");
            if(!rs.wasNull()) {
                PromotionCriterionValue promotionCriterionValue = new PromotionCriterionValue();
                promotionCriterionValue.setPromotionCriterionValueId(promotionCriterionValueId);
                promotionCriterionValue.setCode(rs.getString("valueCode"));
                promotionCriterionValue.setDescription(rs.getString("valueDescription"));
                promotionCriterion.addPromotionCriterionValue(promotionCriterionValue);
            }
        }

        private PromotionCriterion mapToPromotionCriterion(Long promotionCriterionId, ResultSet rs){
            PromotionCriterion criterion = new PromotionCriterion();
            try {
                criterion.setPromotionCriterionId(promotionCriterionId);
                criterion.setCriterionDisplayType(PromotionCriterion.CriterionDisplayType.valueOf(rs.getString("displaytype")));
                criterion.setActive(rs.getBoolean("active"));
                criterion.setCreatedBy(rs.getLong("createdby"));
                criterion.setCreatedDate(rs.getTimestamp("createddate"));
                criterion.setDescription(rs.getString("description"));
                criterion.setPromotionCriterionType(PromotionCriterionType.valueOf(rs.getString("promotionCriterionType")));
                criterion.setCriterionCode(CriterionCode.valueOf(rs.getString("code")));
            }catch(Throwable ex){
                LOGGER.error("PromotionCriterion error in read from resultset:" + promotionCriterionId, ex);
                throw new DBRuntimeException(ex);
            }
            return criterion;
        }

        public List<PromotionCriterion> getPromotionCriterions() {
            return new ArrayList(promotionCriterionMap.values());
        }

    }
}
