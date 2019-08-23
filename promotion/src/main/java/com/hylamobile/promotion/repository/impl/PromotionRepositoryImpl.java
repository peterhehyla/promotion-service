package com.hylamobile.promotion.repository.impl;

import com.hylamobile.promotion.domain.CategoryQuestionResponsePromotion;
import com.hylamobile.promotion.dto.PromotionQualifierDTO;
import com.hylamobile.promotion.dto.PromotionSearchDTO;
import com.hylamobile.promotion.domain.CompanyPromotion;
import com.hylamobile.promotion.domain.ModelPromotion;
import com.hylamobile.promotion.domain.Promotion;
import com.hylamobile.promotion.exceptions.DBRuntimeException;
import com.hylamobile.promotion.repository.CategoryQuestionResponsePromotionRepository;
import com.hylamobile.promotion.repository.ModelPromotionRepository;
import com.hylamobile.promotion.repository.PromotionRepository;
import com.hylamobile.promotion.repository.rowmapper.PromotionRowMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

//use @Bean to create spring bean instance
public class PromotionRepositoryImpl extends NamedParameterJdbcDaoSupport implements PromotionRepository {
    private final static Logger LOGGER = LoggerFactory.getLogger(PromotionRepositoryImpl.class);
    //join company promotion and promotion since fs_company_promotion always has value for each promotion
    private final static String ALL_CURRENT_VALID_PROMOTION_QUERY = "select p.*,l.description as disclaimerContent,cp.companyid,cp.companypromotionid,cp.createddate companyPromtionCreatedDate,cp.enddate companyPromotionEndDate "
            + "from ref_promotion p join fs_company_promotion cp on p.promotionid=cp.promotionid and cp.createddate<:promotionDate and (cp.enddate is null or cp.enddate>=:promotionDate) "
            + "left join ref_label l on l.code=p.disclaimercontent_labelcode and l.language='en' ";
    //we only need promotion information
    private final static String ALL_VALID_PROMOTION_BASE_QUERY = "select distinct p.*,l.description as disclaimerContent "
            + "from ref_promotion p join fs_company_promotion cp on p.promotionid=cp.promotionid and cp.createddate<:promotionDate and (cp.enddate is null or cp.enddate>=:promotionDate) "
            + "left join ref_label l on l.code=p.disclaimercontent_labelcode and l.language='en' ";
    private final static String ALL_VALID_PROMOTION_BASE_WHERE_CLAUSE = " where p.active=:active and p.startdate<:startDate and (p.enddate is null or p.enddate>=:endDate)";
    private final static String MODEL_CODE_FILTER = " and (not exists(select modelcode from fs_model_promotion mp where mp.promotionid=p.promotionid and (mp.enddate is null or mp.enddate<:promotionDate)) "
            + " or exists(select modelcode from fs_model_promotion mp1 where mp1.promotionid=p.promotionid and mp1.modelcode in (:modelCodes) and mp1.createddate<:promotionDate and (mp1.enddate is null or mp1.enddate>=:promotionDate))) ";
    private final static String QAULIFIER_BASE_FILTER_START = " and not exists (select code from ( "
            + "(select pc.code,pct.name as type,max(pr.value) as value from ref_promotion_restriction pr "
            + "join ref_promotion_criterion pc on pc.promotioncriterionid=pr.promotioncriterionid "
            + "join ref_promotion_criterion_type pct on pct.promotioncriteriontypeid=pc.promotioncriteriontypeid "
            + "where promotionid=p.promotioinid and pr.createddate<:promotionDate and (pr.enddate is null or enddate >=:promotionDate) "
            + "group by pc.code,pct.name) EXCEPT ( ";
    private final static String SINGLE_QAULIFIER_TEMPLATE = " select :code%d as code,:type%d as type,:value%d as value ";
    private final static String UNION_ALL =" union all ";
    private final static String QAULIFIER_BASE_FILTER_END = " )) as restricts) ";
    private final static String ORDER_BY_PROMOTION_ID = " order by p.promotionid";

    @Resource
    private ModelPromotionRepository modelPromotionRepository;
    @Resource
    private CategoryQuestionResponsePromotionRepository categoryQuestionResponsePromotionRepository;
    @Cacheable(value="promotions", key="#searchBean.toString()")
    @Override public List<Promotion> getAllValidPromotions(PromotionSearchDTO searchBean) {
        LOGGER.debug("getAllValidPromotions {}", searchBean);
        Date promotionDate = searchBean.getDate();
        if (promotionDate == null) {
            promotionDate = new Date();
        }
        Set<String> modelCodes = searchBean.getModelCodes();
        String promoCode = StringUtils.trimToNull(searchBean.getPromotionCode());
        StringBuilder queryBuilder = new StringBuilder(ALL_VALID_PROMOTION_BASE_QUERY);
        StringBuilder whereClauseBuilder = new StringBuilder(ALL_VALID_PROMOTION_BASE_WHERE_CLAUSE);
        Map<String, Object> namedParameters = new HashMap<>();
        //put must have search conditions
        namedParameters.put("promotionDate", promotionDate);
        namedParameters.put("startDate", searchBean.getMinStartDate());
        namedParameters.put("endDate", searchBean.getMaxEndDate());
        if (searchBean.getActive() != null) {
            namedParameters.put("active", searchBean.getActive());
        }
        //search by company
        if (searchBean.getAffectedCompanyId() != 0L) {
            whereClauseBuilder.append(" and cp.companyid=:companyId");
            namedParameters.put("companyId", searchBean.getAffectedCompanyId());
        }
        //search by model
        if (CollectionUtils.isNotEmpty(modelCodes)) {
            whereClauseBuilder.append(MODEL_CODE_FILTER);
            namedParameters.put("modelCodes", modelCodes);
        }
        //search by promotionCode in ref_promotion
        if (StringUtils.isNotBlank(promoCode)) {
            whereClauseBuilder.append(" and p.promotioncode=:promotioncode ");
            namedParameters.put("promotioncode", promoCode);
        }
        //for admin promotion wildcard search
        if (StringUtils.isNotBlank(searchBean.getPromotionCodeWildCard())) {
            whereClauseBuilder.append(" and p.promotioncode ilike '%:promotionCodeWildCard%' ");
            namedParameters.put("promotionCodeWildCard", searchBean.getPromotionCodeWildCard());
        }

        //search by promotion type
        if (!CollectionUtils.isEmpty(searchBean.getTypes())) {
            whereClauseBuilder.append(" and p.type in (:types) ");
            namedParameters.put("types", searchBean.getTypes());
        }

        if (searchBean.getAppliedTo().size() > 0) {
            whereClauseBuilder.append(" and p.promotionappliedto in (:appliedTo) ");
            namedParameters.put("appliedTo", searchBean.getAppliedTo());
        }

        if (searchBean.getVisible() != null) {
            whereClauseBuilder.append(" and p.visible=:visible ");
            namedParameters.put("visible", searchBean.getVisible());
        }

        if (searchBean.isVerificationRequired()) {
            whereClauseBuilder.append(" and p.verificationrequired=:verificationRequired ");
            namedParameters.put("verificationRequired", true);
        }

        if( StringUtils.isNotBlank(searchBean.getCurrencyCode())) {
            whereClauseBuilder.append(" and p.calculation_currency=:calculationCurrency ");
            namedParameters.put("calculationCurrency", searchBean.getCurrencyCode());
        }

        if (searchBean.getTradeInDate() != null) {
            namedParameters.put("endDate", searchBean.getTradeInDate());
            namedParameters.put("startDate", searchBean.getTradeInDate());
        }

        if (!searchBean.isSpoEligible()) {
            whereClauseBuilder.append(" and p.spo_eligible=:spoEligible ");
            namedParameters.put("spoEligible", false);
        }
        if (!searchBean.isFrpEligible()) {
            whereClauseBuilder.append(" and p.frp_eligible=:frpEligible ");
            namedParameters.put("frpEligible", false);
        }

        if (StringUtils.isNotEmpty(searchBean.getCompanyName())) {
            queryBuilder.append((" join fs_company c on c.companyid=cp.companyid "));
            whereClauseBuilder.append(" and c.name=:companyName ");
            namedParameters.put("companyName", searchBean.getCompanyName());
        }
        //build promotion restriction condition for verizon
        addQualifierRestrictions(searchBean, whereClauseBuilder, namedParameters);

        queryBuilder.append(whereClauseBuilder);
        queryBuilder.append(ORDER_BY_PROMOTION_ID);

        List<Promotion> availablePromotions = getNamedParameterJdbcTemplate().query(queryBuilder.toString(), namedParameters,
                new PromotionRowMapper());

        loadCategoryQuestionResponse(availablePromotions);
        return availablePromotions;
    }
    private List<Promotion> loadModelPromotion(List<Promotion> availablePromotions, Date promotionDate){
        Set<Long> promotionIds = availablePromotions.stream().map(Promotion::getPromotionId).collect(Collectors.toSet());
        Map<Long, Promotion> promotionMap = availablePromotions.stream().collect(Collectors.toMap(Promotion::getPromotionId, p->p, (p1,p2)->p1));
        List<ModelPromotion> modelPromotions = modelPromotionRepository.findModelPromotionsByPromotionIdsOrderByPromotionId(promotionIds, promotionDate);
        Promotion promotion = null;
        for(ModelPromotion mp:modelPromotions){
            if(promotion==null || !promotion.getPromotionId().equals(mp.getPromotionId())){
                promotion = promotionMap.get(mp.getPromotionId());
            }
            if(promotion!=null){
                promotion.addModelPromotion(mp);
            }
        }
        return availablePromotions;
    }
    private List<Promotion> loadCategoryQuestionResponse(List<Promotion> availablePromotions){
        Set<Long> promotionIds = availablePromotions.stream().map(Promotion::getPromotionId).collect(Collectors.toSet());
        Map<Long, Promotion> promotionMap = availablePromotions.stream().collect(Collectors.toMap(Promotion::getPromotionId, p->p, (p1,p2)->p1));
        Map<Long, SortedSet<CategoryQuestionResponsePromotion>> responseMap = categoryQuestionResponsePromotionRepository.findByPromotionIdsOrderByPromotionId(promotionIds);
        Promotion promotion = null;
        for(Long promotionId:promotionIds){
            if(promotion==null || !promotion.getPromotionId().equals(promotionId)){
                promotion = promotionMap.get(promotionId);
            }
            if(promotion!=null){
                promotion.setCategoryQuestionResponsePromotionSet(responseMap.get(promotionId));
            }
        }
        return availablePromotions;
    }
    private StringBuilder addQualifierRestrictions(PromotionSearchDTO searchBean,
            StringBuilder whereClauseBuilder, Map<String, Object> namedParameters) {
        Set<PromotionQualifierDTO> promotionQualifiers = searchBean.getPromotionQualifiers();
        if(CollectionUtils.isEmpty(promotionQualifiers)){
            return whereClauseBuilder;
        }
        whereClauseBuilder.append(QAULIFIER_BASE_FILTER_START);
        Iterator<PromotionQualifierDTO> iterator = promotionQualifiers.iterator();
        PromotionQualifierDTO promotionQualifier = iterator.next();
        int i = 0;
        whereClauseBuilder.append(buildSinglePromotionQualifierCondition(promotionQualifier, i, namedParameters));
        i+=promotionQualifier.getValues().size();
        while (iterator.hasNext()) {
            promotionQualifier = iterator.next();
            whereClauseBuilder.append(UNION_ALL);
            whereClauseBuilder.append(buildSinglePromotionQualifierCondition(promotionQualifier, i, namedParameters));
            i+=promotionQualifier.getValues().size();
        }
        whereClauseBuilder.append(QAULIFIER_BASE_FILTER_END);
        return whereClauseBuilder;
    }
    private String buildSinglePromotionQualifierCondition(PromotionQualifierDTO promotionQualifier, int index, Map<String, Object> namedParameters){
        int i = index;
        StringBuilder conditionBuilder = new StringBuilder();
        for(String value: promotionQualifier.getValues()){
            if(i!=index){
                conditionBuilder.append(UNION_ALL);
            }
            namedParameters.put("code"+i, promotionQualifier.getCriterionCode().name());
            namedParameters.put("type"+i, promotionQualifier.getCriterionType().name());
            namedParameters.put("value"+i, value);
            conditionBuilder.append(String.format(SINGLE_QAULIFIER_TEMPLATE, i, i, i));
            i++;
        }

        return conditionBuilder.toString();
    }
    private class PromotionCompanyRowCallbackHandler implements RowCallbackHandler {
        private Map<Long, Promotion> promotionMap = new HashMap<>();
        @Override
        public void processRow(ResultSet rs) throws SQLException {
            Long promotionId = rs.getLong("promotionid");
            Promotion promotion = promotionMap.computeIfAbsent(promotionId, k->mapToPromotion(k, rs));
            CompanyPromotion companyPromotion = new CompanyPromotion();
            companyPromotion.setCompanyId(rs.getLong("companyid"));
            companyPromotion.setCreatedDate(rs.getTimestamp("companyPromtionCreatedDate"));
            companyPromotion.setEndDate(rs.getTimestamp("companyPromotionEndDate"));
            promotion.addCompanyPromotion(companyPromotion);
        }

        private Promotion mapToPromotion(Long promotionId, ResultSet rs){
            Promotion promotion = null;
            //reuse promotion row mapper, rowNum should not have any meaning in the following call
            PromotionRowMapper promotionRowMapper = new PromotionRowMapper();
            try {
                promotion = promotionRowMapper.mapRow(rs, 0);
            }catch(Throwable ex){
                LOGGER.error("PromotionCriterion error in read from resultset:" + promotionId, ex);
                throw new DBRuntimeException(ex);
            }
            return promotion;
        }
        public List<Promotion> getPromotions() {
            return new ArrayList(promotionMap.values());
        }
    }
}
