package com.hylamobile.promotion.repository.impl;

import com.hylamobile.promotion.domain.CategoryQuestionResponsePromotion;
import com.hylamobile.promotion.enums.CategoryQuestionCode;
import com.hylamobile.promotion.exceptions.DBRuntimeException;
import com.hylamobile.promotion.repository.CategoryQuestionResponsePromotionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class CategoryQuestionResponsePromotionRepositoryImpl  extends NamedParameterJdbcDaoSupport implements CategoryQuestionResponsePromotionRepository {
    private final static Logger LOGGER = LoggerFactory.getLogger(CategoryQuestionResponsePromotionRepositoryImpl.class);
    private final static String CATEGORY_QUESTION_RESPONSE_PROMOTION_BY_PROMOTION_IDS_QUERY = "select cqr.categoryquestionresponseid,cq.questioncode categoryquestioncode,ll.code responsecode,cqrp.promotionid from fs_categoryquestion_response_promotion cqrp join ref_categoryquestion_response cqr on cqrp.categoryquestionresponseid=cqr.categoryquestionresponseid "
            + "join ref_category_question cq on cq.categoryquestionid=cqr.categoryquestionid "
            + "join ref_labelconstant ll on ll.labelconstantid=cqr.responseid "
            + "where cqrp.promotionid in (:promotionIds) order by cqrp.promotionid";
    @Override
    public Map<Long, SortedSet<CategoryQuestionResponsePromotion>> findByPromotionIdsOrderByPromotionId(Set<Long> promotionIds){
        Map<String, Object> namedMap = new HashMap<>();
        namedMap.put("promotionIds", promotionIds);
        CategoryQuestionResponsePromotionRowCallbackHandler handler = new CategoryQuestionResponsePromotionRowCallbackHandler();
        getNamedParameterJdbcTemplate().query(CATEGORY_QUESTION_RESPONSE_PROMOTION_BY_PROMOTION_IDS_QUERY, namedMap, handler);
        return handler.getCategoryQuestionResponse();
    }

    private class CategoryQuestionResponsePromotionRowCallbackHandler implements RowCallbackHandler {
        private Map<Long, SortedSet<CategoryQuestionResponsePromotion>> promotionQuestionResponseMap = new HashMap<>();
        @Override
        public void processRow(ResultSet rs) throws SQLException {
            Long promotionId = rs.getLong("promotionid");
            SortedSet<CategoryQuestionResponsePromotion> responses = promotionQuestionResponseMap.computeIfAbsent(promotionId, k->mapToQuestionResponseList(k, rs));
            CategoryQuestionResponsePromotion response = createCategoryQuestionResponse(promotionId, rs);
            responses.add(response);
        }
        private CategoryQuestionResponsePromotion createCategoryQuestionResponse(Long promotionId, ResultSet rs){
            CategoryQuestionResponsePromotion response = new CategoryQuestionResponsePromotion();
            try {
                response.setCategoryQuestionResponseId(rs.getLong("categoryquestionresponseid"));
                response.setCategoryQuestionCode(CategoryQuestionCode.valueOf(rs.getString("categoryquestioncode")));
                response.setResponseCode(rs.getString("responsecode"));
                response.setPromotionId(promotionId);
            }catch(Throwable ex){
                LOGGER.error("PromotionCriterion error in read from resultset:" + promotionId, ex);
                throw new DBRuntimeException(ex);
            }
            return response;
        }
        private SortedSet<CategoryQuestionResponsePromotion> mapToQuestionResponseList(Long promotionId, ResultSet rs){
            SortedSet<CategoryQuestionResponsePromotion> responses = new TreeSet<>();
            responses.add(createCategoryQuestionResponse(promotionId, rs));
            return responses;
        }

        public Map<Long, SortedSet<CategoryQuestionResponsePromotion>> getCategoryQuestionResponse() {
            return promotionQuestionResponseMap;
        }

    }
}
