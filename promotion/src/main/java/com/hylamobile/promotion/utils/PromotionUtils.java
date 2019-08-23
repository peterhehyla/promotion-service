package com.hylamobile.promotion.utils;

import com.hylamobile.promotion.domain.Promotion;
import com.hylamobile.promotion.dto.QuestionResponse;
import com.hylamobile.promotion.enums.CategoryQuestionCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class PromotionUtils {
    public static final String ACCOUNT_NUMBER_SEPARATOR = "-";
    /**
     * Returns immutable pair of customer id and sub account number from composite account number
     * Given 123456789-00001 as account number, 123456789 is returned as customer id and 1 is returned as sub account number (leading zeros are stripped)
     *
     * @param accountNumber composite account number
     * @return pair of customer id and sub account number
     */
    public static ImmutablePair<String, String> getCustomerIdAndSubAccountNumberPair(String accountNumber) {
        if (StringUtils.isBlank(accountNumber)) {
            return null;
        }

        String[] splittedAccount = StringUtils.split(accountNumber, ACCOUNT_NUMBER_SEPARATOR);
        if (splittedAccount.length != 2) {
            return null;
        }
        return ImmutablePair.of(splittedAccount[0], StringUtils.stripStart(splittedAccount[1], "0"));
    }
    public static BigDecimal getSpoOrFrpAmount(Promotion promotion){
        if(promotion.getSpoEligible()){
            return promotion.getSpoCredit().getAmount();
        }
        if(promotion.getFrpEligible()){
            return promotion.getFrpCredit().getAmount();
        }
        return null;
    }
    public static Map<CategoryQuestionCode, SortedSet<String>> getCategoryResponsesMap(
            Set<? extends QuestionResponse> categoryQuestionResponses) {
        if (CollectionUtils.isEmpty(categoryQuestionResponses)) {
            return Collections.emptyMap();
        }
        return categoryQuestionResponses.stream()
                .collect(Collectors.groupingBy(QuestionResponse::getCategoryQuestionCode, Collectors.mapping(
                        QuestionResponse::getResponseCode,
                        Collectors.toCollection(TreeSet::new))));
    }
}
