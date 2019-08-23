package com.hylamobile.promotion.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class PromotionCriterionValue {

    private static final long serialVersionUID = 6177581486231670628L;
    @Id
    private Long promotionCriterionValueId;
    @Version
    private Long version;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    private Long updatedBy;

    private Long createdBy;
    private String code;

    private String description;

    private Integer sortOrder;

    private PromotionCriterion promotionCriterion;

    private Set<PromotionCriterionValue> childCriterionValues;

    public void addChildCriterionValues(Set<PromotionCriterionValue> children){
        if(childCriterionValues==null){
            childCriterionValues = children;
        } else {
            childCriterionValues.addAll(children);
        }
        children.forEach(c->c.setPromotionCriterion(promotionCriterion));
    }
}

