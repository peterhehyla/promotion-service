package com.hylamobile.promotion.repository.rowmapper;

import com.hylamobile.promotion.domain.Money;
import com.hylamobile.promotion.domain.Promotion;
import com.hylamobile.trade.CalculationFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Currency;

public class PromotionRowMapper implements RowMapper<Promotion> {
    @Override
    public Promotion mapRow(ResultSet rs, int rowNum) throws SQLException {
        Promotion promotion = new Promotion();
        promotion.setPromotionId(rs.getLong("promotionid"));
        promotion.setAffectedCompanyId(rs.getLong("affectedcompanyid"));
        promotion.setPromotionCompanyId(rs.getLong("promotioncompanyid"));
        promotion.setStartDate(rs.getTimestamp("startdate"));
        promotion.setEndDate(rs.getTimestamp("enddate"));
        promotion.setDescription(rs.getString("description"));
        promotion.setPromotionCode(rs.getString("promotioncode"));
        promotion.setCalculationType(rs.getString("calculation_type"));
        promotion.setCalculationValue(rs.getBigDecimal("calculation_value"));
        promotion.setType(rs.getString("type"));
        promotion.setVerificationRequired(rs.getBoolean("verificationrequired"));
        promotion.setVerificationInstructions(rs.getString("verificationinstructions"));
        promotion.setCalculationCurrency(rs.getString("calculation_currency"));
        promotion.setCalculation(CalculationFactory.create(promotion.getCalculationType(), promotion.getCalculationValue(), promotion.getCalculationCurrency()));
        promotion.setVisible(rs.getBoolean("visible"));
        promotion.setPaymentMethod(rs.getString("paymentmethod"));
        promotion.setDisclaimerContent(rs.getString("disclaimerContent"));
        promotion.setMaximumCreditAmount(rs.getBigDecimal("maximumcredit_amount"));
        promotion.setMaximumCreditCurrency(rs.getString("maximumcredit_currency"));
        if(promotion.getMaximumCreditAmount()!=null && StringUtils.isNotBlank(promotion.getMaximumCreditCurrency())) {
            promotion.setMaximumCredit(new Money(promotion.getMaximumCreditAmount(), Currency.getInstance(promotion.getMaximumCreditCurrency())));
        }
        promotion.setMaximumCreditSystemAmount(rs.getBigDecimal("maximumcredit_systemamount"));
        promotion.setMaximumCreditSystemCurrency(rs.getString("maximumcredit_systemcurrency"));
        promotion.setMaximumCreditExchangeRate(rs.getBigDecimal("maximumcredit_exchangerate"));
        promotion.setMinimumCreditAmount(rs.getBigDecimal("minimumcredit_amount"));
        promotion.setMinimumCreditCurrency(rs.getString("minimumcredit_currency"));
        if(promotion.getMinimumCreditAmount()!=null && StringUtils.isNotBlank(promotion.getMinimumCreditCurrency())) {
            promotion.setMinimumCredit(new Money(promotion.getMinimumCreditAmount(), Currency.getInstance(promotion.getMinimumCreditCurrency())));
        }
        promotion.setMinimumCreditSystemAmount(rs.getBigDecimal("minimumcredit_systemamount"));
        promotion.setMinimumCreditSystemCurrency(rs.getString("minimumcredit_systemcurrency"));
        promotion.setMinimumCreditExchangeRate(rs.getBigDecimal("minimumcredit_exchangerate"));
        promotion.setAuto(rs.getBoolean("auto"));
        promotion.setPromotionAppliedTo(rs.getString("promotionappliedto"));
        promotion.setIncludeRecycleItem(rs.getBoolean("includerecycleitem"));
        promotion.setSkuSuffix(rs.getString("skusuffix"));
        promotion.setApplicabilityLimit(rs.getInt("applicability_limit"));
        promotion.setMaxOccurences(rs.getInt("max_occurences"));
        promotion.setCountOccurences(rs.getString("count_occurences"));
        promotion.setApplyForAnyValueDevice(rs.getBoolean("applyforanyvaluedevice"));
        promotion.setEnableAdjustmentDeduction(rs.getBoolean("enableadjustmentdeduction"));
        promotion.setEnableImeiUniqueValidation(rs.getBoolean("enableimeiuniquevalidation"));
        promotion.setSpoEligible(rs.getBoolean("spo_eligible"));
        promotion.setSpoCreditAmount(rs.getBigDecimal("spocredit_amount"));
        promotion.setSpoCreditCurrency(rs.getString("spocredit_currency"));
        if(promotion.getSpoCreditAmount()!=null && StringUtils.isNotBlank(promotion.getSpoCreditCurrency())) {
            promotion.setSpoCredit(new Money(promotion.getSpoCreditAmount(), Currency.getInstance(promotion.getSpoCreditCurrency())));
        }
        promotion.setMinimumEligibleAmount(rs.getBigDecimal("minimumeligible_amount"));
        promotion.setMinimumeligibleCurrency(rs.getString("minimumeligible_currency"));
        if(promotion.getMinimumEligibleAmount()!=null && StringUtils.isNotBlank(promotion.getMinimumeligibleCurrency())) {
            promotion.setMinimumEligibleValue(new Money(promotion.getMinimumEligibleAmount(), Currency.getInstance(promotion.getMinimumeligibleCurrency())));
        }
        promotion.setMtnValidationRequired(rs.getBoolean("mtn_validation_required"));
        promotion.setStackableWithBogo(rs.getBoolean("stackablewithbogo"));
        promotion.setFrpEligible(rs.getBoolean("frp_eligible"));
        promotion.setFrpAmount(rs.getBigDecimal("frp_amount"));
        promotion.setFrpCurrency(rs.getString("frp_currency"));
        if(promotion.getFrpAmount()!=null && StringUtils.isNotBlank(promotion.getFrpCurrency())) {
            promotion.setFrpCredit(new Money(promotion.getFrpAmount(), Currency.getInstance(promotion.getFrpCurrency())));
        }
        promotion.setHasTac(rs.getBoolean("has_tac"));
        promotion.setHasEsn(rs.getBoolean("has_esn"));
        promotion.setActive(rs.getBoolean("active"));
        promotion.setCreatedBy(rs.getLong("createdby"));
        promotion.setLastUpdatedDate(rs.getTimestamp("lastupdateddate"));
        promotion.setCreatedDate(rs.getTimestamp("createddate"));
        promotion.setUpdatedBy(rs.getLong("updatedby"));
        return promotion;
    }
}
