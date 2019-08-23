package com.hylamobile.promotion.dto;

import com.hylamobile.promotion.enums.CriterionCode;
import com.hylamobile.promotion.enums.PromotionCriterionType;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PromotionQualifierDTO implements Serializable, Comparable<PromotionQualifierDTO> {

    public PromotionQualifierDTO(PromotionCriterionType criterionType, CriterionCode criterionCode,
            List<String> values) {
        Objects.requireNonNull(criterionType, () -> "Promotion criterion type should not be null");
        Objects.requireNonNull(criterionCode, () -> "Promotion criterion code should not be null");
        Objects.requireNonNull(values, () -> "Promotion criterion values should not be null");
        this.criterionType = criterionType;
        this.criterionCode = criterionCode;
        this.values = values;
    }

    private PromotionCriterionType criterionType;

    private CriterionCode criterionCode;

    private List<String> values = new ArrayList<>();

    public PromotionCriterionType getCriterionType() {
        return criterionType;
    }

    public void setCriterionType(PromotionCriterionType criterionType) {
        this.criterionType = criterionType;
    }

    public CriterionCode getCriterionCode() {
        return criterionCode;
    }

    public void setCriterionCode(CriterionCode criterionCode) {
        this.criterionCode = criterionCode;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    @Override
    public int compareTo(PromotionQualifierDTO promotionQualifier) {
        return new CompareToBuilder().append(this.criterionType, promotionQualifier.getCriterionType())
                .append(this.getCriterionCode(), promotionQualifier.getCriterionCode())
                .append(this.getValues(), promotionQualifier.getValues()).toComparison();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(criterionCode).append(values)
                .toString();
    }
}
