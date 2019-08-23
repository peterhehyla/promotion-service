package com.hylamobile.promotion.domain;

import com.hylamobile.promotion.enums.CriterionCode;
import com.hylamobile.promotion.enums.PromotionCriterionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
@NoArgsConstructor
@Setter
@Getter
public class PromotionCriterion{

    private static final long serialVersionUID = 3208513579118327675L;

    ;

    public enum CriterionDisplayType {MULTI_SELECT, BULK, CHECKBOX, ACCT_BULK};
    @Id
    @Column(name="promotioncriterionid")
    private Long promotionCriterionId;
    @Version
    @Column(name="version")
    private Long version;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="lastupdateddate")
    private Date lastUpdatedDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="createddate")
    private Date createdDate;
    @Column(name="updatedby")
    private Long updatedBy;
    @Column(name="createdby")
    private Long createdBy;
    @Column(name="code")
    private CriterionCode criterionCode;

    private String description;
    @Transient
    private PromotionCriterionType promotionCriterionType;

    private Set<PromotionCriterionValue> values;

    private CriterionDisplayType criterionDisplayType;

    private Boolean active = Boolean.TRUE;

    private int sortOrder;
    public PromotionCriterionValue addPromotionCriterionValue(PromotionCriterionValue promotionCriterionValue) {
        if(CollectionUtils.isEmpty(values)){
            values = new HashSet<>();
        }
        values.add(promotionCriterionValue);
        return promotionCriterionValue;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof PromotionCriterion)) {
            return false;
        }
        PromotionCriterion that = (PromotionCriterion) o;
        return Objects.equals(criterionCode, that.criterionCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(criterionCode);
    }
}

