package com.hylamobile.promotion.service.impl;

import com.hylamobile.promotion.domain.PromotionCriterion;
import com.hylamobile.promotion.domain.PromotionCriterionValue;
import com.hylamobile.promotion.dto.CustomerDTO;
import com.hylamobile.promotion.dto.PromotionQualifierDTO;
import com.hylamobile.promotion.enums.CriterionCode;
import com.hylamobile.promotion.enums.PromotionCriterionType;
import com.hylamobile.promotion.repository.PromotionCriterionRepository;
import com.hylamobile.promotion.service.PromotionCriterionService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.hylamobile.promotion.enums.CriterionCode.CONTRACT_TERM;
import static com.hylamobile.promotion.enums.CriterionCode.FEATURE;
import static com.hylamobile.promotion.enums.CriterionCode.LOAN_NUMBER;
import static com.hylamobile.promotion.enums.CriterionCode.NEW_DEVICE_MODEL;
import static com.hylamobile.promotion.enums.CriterionCode.ORDER_REQUEST;
import static com.hylamobile.promotion.enums.CriterionCode.PLAN;
import static com.hylamobile.promotion.enums.CriterionCode.PORT_IN;
import static com.hylamobile.promotion.enums.CriterionCode.UPGRADE_REASON;

@Service
@Transactional(readOnly = true)
public class PromotionCriterionServiceImpl implements PromotionCriterionService {

    @Resource
    private PromotionCriterionRepository promotionCriterionRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(PromotionCriterionServiceImpl.class);

    @Override
    public Map<PromotionCriterion, Set<PromotionCriterionValue>> getDisplayablePromotionCriteriaValuesByCriterion() {
        return groupByCriterion(promotionCriterionRepository.findByCriterionDisplayTypeNotNullAndActive(true));
    }

    @Override
    public Map<PromotionCriterion, Set<PromotionCriterionValue>> getPromotionCriteriaValuesByCriterion() {
        return groupByCriterion(promotionCriterionRepository.getPromotionCriteria());
    }

    private Map<PromotionCriterion, Set<PromotionCriterionValue>> groupByCriterion(
            List<PromotionCriterion> promotionCriteria) {
        return promotionCriteria.stream().collect(Collectors.toMap(Function.identity(), PromotionCriterion::getValues));
    }

    @Override
    public List<PromotionCriterion> getDisplayablePromotionCriteriaValues() {
        return promotionCriterionRepository.findByCriterionDisplayTypeNotNullAndActive(true).stream()
                .sorted(Comparator.comparing(PromotionCriterion::getSortOrder)).collect(Collectors.toList());
    }

    @Override public Set<PromotionQualifierDTO> findPromotionQualifier(CustomerDTO customer) {
        List<PromotionCriterion> promotionCriteria = promotionCriterionRepository.getPromotionCriteria();
        Map<CriterionCode, PromotionCriterionType> criterionTypes = getPromotionCriteriaTypes(
                promotionCriteria);
        Map<CriterionCode, Set<String>> criterionValues = getPromotionCriteriaValues(
                promotionCriteria);

        Set<PromotionQualifierDTO> promotionQualifiers = new TreeSet<>();

        String orderRequestValue = customer.getOrderRequest();
        if (orderRequestValue != null) {
            promotionQualifiers.add(new PromotionQualifierDTO(criterionTypes.get(ORDER_REQUEST), ORDER_REQUEST,
                    Arrays.asList(orderRequestValue)));
        }

        String portInValue = customer.getPortIn();
        if (portInValue != null) {
            promotionQualifiers.add(new PromotionQualifierDTO(criterionTypes.get(PORT_IN), PORT_IN,
                    Arrays.asList(portInValue)));
        }

        String pricePlanValue = customer.getPlan();
        if (pricePlanValue != null) {
            promotionQualifiers
                    .add(new PromotionQualifierDTO(criterionTypes.get(PLAN), PLAN, Arrays.asList(pricePlanValue)));
        }
        //disable loan number search to improve promotion search
        String loanNumberValue = customer.getLoanNumber();
        if (loanNumberValue != null) {
            promotionQualifiers.add(new PromotionQualifierDTO(criterionTypes.get(LOAN_NUMBER), LOAN_NUMBER,
                    Arrays.asList(loanNumberValue)));
        }

        String contractTermValue = customer.getContractTerm();
        if (contractTermValue != null) {
            promotionQualifiers.add(new PromotionQualifierDTO(criterionTypes.get(CONTRACT_TERM), CONTRACT_TERM,
                    Arrays.asList(contractTermValue)));
        }

        String newDeviceModel = customer.getNewDeviceModel();
        if (newDeviceModel != null) {
            promotionQualifiers
                    .add(new PromotionQualifierDTO(criterionTypes.get(NEW_DEVICE_MODEL), NEW_DEVICE_MODEL,
                            Arrays.asList(newDeviceModel)));
        }

        String upgradeReasonValue = customer.getUpgradeReason();
        if (upgradeReasonValue != null) {
            promotionQualifiers.add(new PromotionQualifierDTO(criterionTypes.get(UPGRADE_REASON), UPGRADE_REASON,
                    Arrays.asList(upgradeReasonValue)));
        }

        if (CollectionUtils.isNotEmpty(customer.getFeatures())) {
            promotionQualifiers
                    .add(new PromotionQualifierDTO(criterionTypes.get(FEATURE), FEATURE, customer.getFeatures()));
        }

        addGroupedQualifiers(promotionQualifiers, promotionCriteria, criterionTypes);


        return promotionQualifiers;

    }

    private Map<CriterionCode, PromotionCriterionType> getPromotionCriteriaTypes(
            List<PromotionCriterion> promotionCriteria) {
        return promotionCriteria.stream().collect(Collectors.toMap(PromotionCriterion::getCriterionCode,
                promotionCriterion -> promotionCriterion.getPromotionCriterionType()));
    }

    private Map<CriterionCode, Set<String>> getPromotionCriteriaValues(
            List<PromotionCriterion> promotionCriteria) {
        return promotionCriteria.stream().collect(Collectors.toMap(PromotionCriterion::getCriterionCode,
                promotionCriterion -> promotionCriterion.getValues().stream()
                        .map(promotionCriterionValue -> promotionCriterionValue.getCode())
                        .collect(Collectors.toSet())));
    }

    private void addGroupedQualifiers(Set<PromotionQualifierDTO> promotionQualifiers,
            List<PromotionCriterion> promotionCriteria,
            Map<CriterionCode, PromotionCriterionType> criterionTypes) {
        Map<CriterionCode, List<String>> foundQualifiesByCode = promotionQualifiers.stream()
                .collect(Collectors.toMap(PromotionQualifierDTO::getCriterionCode, PromotionQualifierDTO::getValues));

        for (PromotionCriterion promotionCriterion : promotionCriteria) {
            for (PromotionCriterionValue promotionCriterionValue : promotionCriterion.getValues()) {
                if (CollectionUtils.isEmpty(promotionCriterionValue.getChildCriterionValues())) {
                    continue;
                }
                boolean allMatch = true;
                for (PromotionCriterionValue childCriterionValue : promotionCriterionValue
                        .getChildCriterionValues()) {
                    if (!childCriterionValueMatches(childCriterionValue, foundQualifiesByCode)) {
                        allMatch = false;
                        break;
                    }
                }
                if (allMatch) {
                    promotionQualifiers.add(new PromotionQualifierDTO(criterionTypes.get(
                            promotionCriterion.getCriterionCode()), promotionCriterion.getCriterionCode(),
                            Arrays.asList(promotionCriterionValue.getCode())));
                }
            }
        }
    }

    private boolean childCriterionValueMatches(PromotionCriterionValue childCriterionValue,
            Map<CriterionCode, List<String>> foundQualifiesByCode) {
        List<String> foundValues = foundQualifiesByCode
                .get(childCriterionValue.getPromotionCriterion().getCriterionCode());
        return foundValues != null && foundValues.contains(childCriterionValue.getCode());
    }
}

