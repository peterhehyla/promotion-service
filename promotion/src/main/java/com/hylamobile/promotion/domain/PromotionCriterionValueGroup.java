package com.hylamobile.promotion.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name="ref_promotion_criterion_value_group")
@NamedQuery(name="PromotionCriterionValueGroup.findAll", query="SELECT p FROM PromotionCriterionValueGroup p")
public class PromotionCriterionValueGroup implements Serializable {

    private static final long serialVersionUID = 333345295867308619L;

    @EmbeddedId
    private PromotionCriterionValueGroupPK id;

    private Boolean active;

    private Long createdby;

    private Timestamp createddate;

    private Timestamp lastupdateddate;

    private Long updatedby;

    public PromotionCriterionValueGroup() {
    }
}
