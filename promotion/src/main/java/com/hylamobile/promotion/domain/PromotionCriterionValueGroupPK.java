package com.hylamobile.promotion.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Setter
@Getter
public class PromotionCriterionValueGroupPK implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -4987459730191552508L;


    private Long parentpromotioncriterionvalueid;

    private Long childpromotioncriterionvalueid;

    public PromotionCriterionValueGroupPK() {
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PromotionCriterionValueGroupPK)) {
            return false;
        }
        PromotionCriterionValueGroupPK castOther = (PromotionCriterionValueGroupPK)other;
        return
                this.parentpromotioncriterionvalueid.equals(castOther.parentpromotioncriterionvalueid)
                        && this.childpromotioncriterionvalueid.equals(castOther.childpromotioncriterionvalueid);
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + this.parentpromotioncriterionvalueid.hashCode();
        hash = hash * prime + this.childpromotioncriterionvalueid.hashCode();

        return hash;
    }
}
