package com.hylamobile.promotion.service.impl;

import com.hylamobile.promotion.bean.CalculatedAmountBean;
import com.hylamobile.promotion.domain.CategoryQuestionResponsePromotion;
import com.hylamobile.promotion.domain.Money;
import com.hylamobile.promotion.domain.Promotion;
import com.hylamobile.promotion.dto.CustomerDTO;
import com.hylamobile.promotion.dto.ItemDetailDTO;
import com.hylamobile.promotion.dto.QuestionResponseDTO;
import com.hylamobile.promotion.dto.PromotionQualifierDTO;
import com.hylamobile.promotion.dto.PromotionSearchDTO;
import com.hylamobile.promotion.enums.CategoryQuestionCode;
import com.hylamobile.promotion.enums.CriterionCode;
import com.hylamobile.promotion.enums.PromotionType;
import com.hylamobile.promotion.repository.CompanyRepository;
import com.hylamobile.promotion.service.PromotionAcctBulkRestrictionService;
import com.hylamobile.promotion.service.PromotionEsnService;
import com.hylamobile.promotion.service.PromotionPostDBFilterService;
import com.hylamobile.promotion.utils.CommonUtils;
import com.hylamobile.promotion.utils.PromotionUtils;
import com.hylamobile.trade.CalculationType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TimeZone;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class PromotionPostDBFilterServiceImpl implements PromotionPostDBFilterService {
    private Logger LOGGER = LoggerFactory.getLogger(PromotionPostDBFilterServiceImpl.class);

    private static final String CARRIER_FUNDED_COMPANY_NAME = "Carrier Funded";

    @Resource
    private PromotionEsnService promotionEsnService;
    @Resource
    private PromotionAcctBulkRestrictionService promotionAcctBulkRestrictionService;
    @Resource
    private CompanyRepository companyRepository;
    @Override
    public List<CalculatedAmountBean<Promotion>> retrieveAvailablePromotions(PromotionSearchDTO searchBean,
            Collection<Promotion> allAvailablePromotions) {
        Money amount = Money.newZeroInstance(Currency.getInstance("USD"));
        if(searchBean.getItemDetail()!=null){
            amount = new Money(searchBean.getItemDetail().getBaseCredit(), Currency
                    .getInstance(searchBean.getItemDetail().getCurrencyCode()));
        }
        return fliterAndConvertValidPromotions(searchBean, amount, allAvailablePromotions);
    }

    private List<CalculatedAmountBean<Promotion>> fliterAndConvertValidPromotions(PromotionSearchDTO searchBean, Money amount, Collection<Promotion> promotions){
        Date minStartDate;
        Date maxEndDate;
        ItemDetailDTO itemDetail = searchBean.getItemDetail();
        if (searchBean.getEndDate() == null && searchBean.getStartDate() == null){
            Date promotionDate = Objects.nonNull(searchBean.getDate())?searchBean.getDate():new Date();
            minStartDate = CommonUtils.getDateInTimezone(promotionDate, TimeZone.getTimeZone(searchBean.getTimezone()));
            maxEndDate = CommonUtils.getDateInTimezone(promotionDate, TimeZone.getTimeZone(searchBean.getTimezone()));
        }else{
            minStartDate = searchBean.getMinStartDate();
            maxEndDate = searchBean.getMaxEndDate();
        }
        List<CalculatedAmountBean<Promotion>> results;
        if (itemDetail == null) {
            results = promotions.stream().filter(promo->!isPromotionInvalidByDate(promo, minStartDate, maxEndDate)
                    && minEligibleValue(promo.getMinimumEligibleValue(), amount, searchBean.isSkipMinValueCheck())
                    && isValidPromotionByCheckingSpoOrFrp(promo, amount)).map(p->new CalculatedAmountBean<Promotion>(amount, p, null)
            ).collect(Collectors.toList());
        }
        // changes for getting filtered promotions and new calculated value of promotions
        else {
            results = applyAndFliterPromotionsByItemDetailWithoutBestPick(minStartDate, maxEndDate, promotions, itemDetail, amount, searchBean.isSkipMinValueCheck());
        }
        filterMtnAndEsnPromotionsAndGetBestIfOptionEnabled(results, searchBean);
        return results;
    }
    protected void filterMtnAndEsnPromotionsAndGetBestIfOptionEnabled(List<CalculatedAmountBean<Promotion>> allCalculatedAmounts,
            PromotionSearchDTO searchBean) {
        if (!Boolean.TRUE.equals(searchBean.isPostProcessPromotions())) {
            return;
        }
        Boolean displayBestPromotion = searchBean.getFetchBestPromotion();
        Set<PromotionQualifierDTO> promotionQualifiers = searchBean.getPromotionQualifiers();
        if (CollectionUtils.isEmpty(promotionQualifiers)) {
            if (displayBestPromotion) {
                LOGGER.warn("Best promotion displaying feature can be used with Parameterized logins only. "
                        + "Please set 'DISPLAY_BEST_PROMOTION_ONLY' category attribute value to 'false' or login through Parameterized login V2");
            }
            // the feature is available for parameterized login only
            return;
        }
        filterByNewDeviceModel(allCalculatedAmounts, findPromotionQualifier(promotionQualifiers, CriterionCode.NEW_DEVICE_MODEL));
        filterMtnPromotions(allCalculatedAmounts, findPromotionQualifier(promotionQualifiers, CriterionCode.MTN));
        CustomerDTO customer = searchBean.getCustomer();
        List<String> imeis = customer!=null?customer.getImeis():Collections.emptyList();
        if (!displayBestPromotion && imeis.stream().noneMatch(imei -> StringUtils.isNotBlank(imei))) {
            return;
        }
        // remove all single choice promotions that have TAC/ESN restriction and don't match IMEI from parameterized
        // login
        allCalculatedAmounts
                .removeIf(calculatedAmount -> isBsiOrBillCredit(calculatedAmount) && !applicableImei(calculatedAmount,
                        imeis, displayBestPromotion));
        if (!displayBestPromotion) {
            return;
        }
        filterBestSingleChoicePromotions(allCalculatedAmounts);
    }
    void filterBestSingleChoicePromotions(List<CalculatedAmountBean<Promotion>> allCalculatedAmounts) {
        List<CalculatedAmountBean<Promotion>> amountsToFilter = allCalculatedAmounts.stream()
                .filter(calculatedAmount -> isBsiOrBillCredit(calculatedAmount)).collect(toList());
        if (amountsToFilter.isEmpty()) {
            return;
        }
        filterByHighestPromotionValue(amountsToFilter);
        filterByClosestExpirationDate(amountsToFilter);
        filterByPromotionCompany(amountsToFilter);

        BigDecimal bestBsiPromoValue = getPromotionValue(amountsToFilter.get(0));
        allCalculatedAmounts.removeIf(
                calculatedAmount -> (isBsiOrBillCredit(calculatedAmount) && !amountsToFilter.contains(calculatedAmount))
                        || isBsuWithValueLowerThan(calculatedAmount, bestBsiPromoValue));
    }
    private boolean isBsuWithValueLowerThan(CalculatedAmountBean<Promotion> calculatedAmount,
            BigDecimal bestBsiPromoValue) {
        return PromotionType.BONUS_SINGLE_USE.equals(calculatedAmount.getObject().getType())
                && getPromotionValue(calculatedAmount).compareTo(bestBsiPromoValue) < 0;
    }
    private void filterByPromotionCompany(List<CalculatedAmountBean<Promotion>> amountsToFilter) {
        if (amountsToFilter.size() == 1) {
            return;
        }
        Set<CalculatedAmountBean<Promotion>> carrierFundedAmounts = amountsToFilter.stream()
                .filter(calculatedAmount -> calculatedAmount.getObject().getPromotionCompanyId() != null
                        && CARRIER_FUNDED_COMPANY_NAME.equals(companyRepository
                        .findById(calculatedAmount.getObject().getPromotionCompanyId()).get().getName()))
                .collect(Collectors.toSet());
        if (carrierFundedAmounts.isEmpty()) {
            return;
        }
        amountsToFilter.removeIf(calculatedAmountBean -> !carrierFundedAmounts.contains(calculatedAmountBean));
    }

    private void filterByHighestPromotionValue(List<CalculatedAmountBean<Promotion>> amountsToFilter) {
        if (amountsToFilter.size() == 1) {
            return;
        }
        Comparator<CalculatedAmountBean<Promotion>> promotionValueComparator = Comparator
                .comparing(calculatedAmount -> getPromotionValue(calculatedAmount), Comparator.naturalOrder());
        BigDecimal highestPromoValue = getPromotionValue(amountsToFilter.stream().max(promotionValueComparator).get());
        amountsToFilter
                .removeIf(calculatedAmount -> getPromotionValue(calculatedAmount).compareTo(highestPromoValue) != 0);
    }
    private PromotionQualifierDTO findPromotionQualifier(Set<PromotionQualifierDTO> promotionQualifiers, CriterionCode criterionCode){
        return promotionQualifiers.stream().filter(p->p.getCriterionCode()==criterionCode).findFirst().orElse(null);
    }
    private BigDecimal getPromotionValue(CalculatedAmountBean<Promotion> calculatedAmount) {
        Promotion promotion = calculatedAmount.getObject();
        return promotion.isSpoOrFrpEligible()?
                PromotionUtils.getSpoOrFrpAmount(promotion):calculatedAmount.getCalculatedAmount().getAmount();
    }

    private void filterByClosestExpirationDate(List<CalculatedAmountBean<Promotion>> amountsToFilter) {
        if (amountsToFilter.size() == 1) {
            return;
        }
        Comparator<CalculatedAmountBean<Promotion>> expirationDateComparator = Comparator.comparing(
                calculatedAmountBean -> calculatedAmountBean.getObject().getEndDate(),
                Comparator.nullsLast(Comparator.naturalOrder()));
        Date closestExpirationDate = amountsToFilter.stream().min(expirationDateComparator).get().getObject()
                .getEndDate();
        if (closestExpirationDate == null) {
            return;
        }
        amountsToFilter.removeIf(calculatedAmountBean -> calculatedAmountBean.getObject().getEndDate() == null
                || calculatedAmountBean.getObject().getEndDate().compareTo(closestExpirationDate) != 0);
    }
    private void filterByNewDeviceModel(List<CalculatedAmountBean<Promotion>> allCalculatedAmounts,
            PromotionQualifierDTO newDeviceModelQualifier) {
        String newDeviceModel = newDeviceModelQualifier!=null?newDeviceModelQualifier.getValues().get(0):null;
        // remove single choice promotions that have a MTN restriction and the MTN doesn't match
        allCalculatedAmounts.removeIf(calculatedAmount -> !promotionAcctBulkRestrictionService
                .isValidNewDeviceModelForPromotion(calculatedAmount.getObject(), newDeviceModel));
    }
    private void filterMtnPromotions(List<CalculatedAmountBean<Promotion>> allCalculatedAmounts,
            PromotionQualifierDTO phoneNumberQualifier) {
        String mtn = phoneNumberQualifier!=null?phoneNumberQualifier.getValues().get(0):null;
        // remove single choice promotions that have a MTN restriction and the MTN doesn't match
        allCalculatedAmounts.removeIf(calculatedAmount -> isBsiOrBillCredit(calculatedAmount)
                && !promotionAcctBulkRestrictionService.isValidMtnForPromotion(calculatedAmount.getObject(), mtn));
    }

    private boolean isBsiOrBillCredit(CalculatedAmountBean<Promotion> calculatedAmount) {
        return PromotionType.BONUS_SINGLE_ITEM.equals(calculatedAmount.getObject().getType())
                || PromotionType.BILL_CREDIT.equals(calculatedAmount.getObject().getType());
    }

    private boolean applicableImei(CalculatedAmountBean<Promotion> calculatedAmount,
            List<String> newDeviceImeis, boolean displayBestPromotion) {
        // promotion does not require IMEI/TAC confirmation to be applied
        if (StringUtils.isEmpty(calculatedAmount.getObject().getDisclaimerContent())) {
            return Boolean.TRUE;
        }
        boolean hasApplicableImei = Boolean.FALSE;
        for (String imei: newDeviceImeis) {
            if (newDeviceImeiValidForPromotion(imei,
                    calculatedAmount.getObject(), displayBestPromotion)) {
                hasApplicableImei = Boolean.TRUE;
            }
        }
        return hasApplicableImei;
    }
    private boolean newDeviceImeiValidForPromotion(String esn, Promotion promotion,
            boolean displayBestPromotion) {
        if (StringUtils.isEmpty(esn) || esn.length() < 3 || !StringUtils.isAlphanumeric(esn)) {
            return false;
        }
        boolean valid = true;
        if (promotion.getHasTac()) {
            valid = CommonUtils.validateImeiLuhnAlgorithm(esn)
                    && promotionAcctBulkRestrictionService.isImeiValidForPromotionTac(promotion, esn);
        } else if (promotion.getHasEsn()) {
            valid = promotionEsnService.isValidEsnForPromotion(promotion.getPromotionId(), esn);
        }
        return valid;
    }
    private boolean isPromotionInvalidByDate(Promotion promotion, Date startDate, Date endDate) {
        FastDateFormat fdf=FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
        LOGGER.debug("promotion id: {}, promotion start date: {}, promotion endDate: {}, start date: {} end date: {}",
                promotion.getPromotionId(), fdf.format(promotion.getStartDate()), Objects.nonNull(promotion.getEndDate())?fdf.format(promotion.getEndDate()) :null, fdf.format(startDate), fdf.format(endDate));
        return promotion.getStartDate().after(startDate)
                || (promotion.getEndDate() != null && promotion.getEndDate().before(endDate));
    }
    private List<CalculatedAmountBean<Promotion>> applyAndFliterPromotionsByItemDetailWithoutBestPick(Date minStartDate,
            Date maxEndDate, Collection<Promotion> promotions, ItemDetailDTO itemDetail,  Money amount,
            boolean skipMinimumEligibleCheck) {
        List<CalculatedAmountBean<Promotion>> results = new ArrayList<>();
        for (Promotion promotion : promotions) {
            if (isPromotionInvalidByDate(promotion, minStartDate, maxEndDate)) {
                continue;
            }

            boolean isRecycle = itemDetail.isRecycle();
            boolean isRecycleItemIncluded = promotion.getIncludeRecycleItem();
            BigDecimal applicablePriceAmount = promotion.getEnableAdjustmentDeduction() ? itemDetail.getApplicablePrice()
                    : itemDetail.getBaseCredit();
            Money applicablePrice = new Money(applicablePriceAmount, Currency.getInstance(itemDetail.getCurrencyCode()));
            SortedSet<CategoryQuestionResponsePromotion> categoryQuestionSet = promotion
                    .getCategoryQuestionResponsePromotionSet();
            // INF-1404:Added adjustment info to allow adjustment based deductions.
            if ((promotion.getCalculation().getCalculationType() == CalculationType.ADDITION
                    || promotion.getCalculatedAmount(applicablePrice, itemDetail.getAdjustmentInfo())
                    .getAmount().compareTo(BigDecimal.ZERO) != 0
                    || promotion.getApplyForAnyValueDevice() || promotion.isSpoOrFrpEligible())
                    && ((isRecycle && isRecycleItemIncluded) || (categoryQuestionSet == null
                    || categoryQuestionSet.isEmpty()
                    || (isValidPromoForResponses(promotion, itemDetail.getItemResponseSet()))))
                    // PROD-1324 check minimum device value after deduction for Promotion Eligibility, we can do
                    // this only here for /recalculate, as #amount comes non-zero
                    && minEligibleValue(promotion.getMinimumEligibleValue(), amount, skipMinimumEligibleCheck)
                    //PROD-6820 not allow organic value greater than spo credit amount
                    &&isValidPromotionByCheckingSpoOrFrp(promotion, amount)) {
                results.add(new CalculatedAmountBean<Promotion>(applicablePrice, promotion, itemDetail.getAdjustmentInfo()));
            }
        }

        return results;
    }

    private boolean isValidPromoForResponses(Promotion promotion,
            Set<QuestionResponseDTO> itemResponseSet) {
        boolean flag = true;
        // itemResponseMap tree map has not consistent comparator and equals implementation.
        // See CategoryQuestion.equals and CategoryQuestionSortOrderComparator.compare methods.
        Map<CategoryQuestionCode, SortedSet<String>> itemResponseMap =  PromotionUtils.getCategoryResponsesMap(itemResponseSet);
        Map<CategoryQuestionCode, SortedSet<String>> promoCategoryResponsesMap = PromotionUtils.getCategoryResponsesMap(
                promotion.getCategoryQuestionResponsePromotionSet());
        for (CategoryQuestionCode categoryQuestionCode : promoCategoryResponsesMap.keySet()) {
            SortedSet<String> promoCatResponses = promoCategoryResponsesMap.get(categoryQuestionCode);
            SortedSet<String> itemResponses = itemResponseMap.get(categoryQuestionCode);
            if (promoCatResponses != null) {
                if (Collections.disjoint(itemResponses, promoCatResponses)) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    private boolean minEligibleValue(Money minValue, Money actual, boolean skipMinimumEligibleCheck) {
        if (minValue == null || skipMinimumEligibleCheck) {
            return true;
        }
        return actual.compareTo(minValue) >= 0;
    }
    private boolean isValidPromotionByCheckingSpoOrFrp(Promotion promotion, Money baseCredit){
        return !promotion.isSpoOrFrpEligible()
                || (promotion.getSpoEligible() && baseCredit.compareTo(promotion.getSpoCredit())<0)
                || (promotion.getFrpEligible() && baseCredit.compareTo(promotion.getFrpCredit())<0);
    }

}
