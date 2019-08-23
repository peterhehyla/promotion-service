package com.hylamobile.promotion.bean;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class PromotionAcctBulkRestrictionRow implements Serializable {
    private static final long serialVersionUID = -7756769309345529678L;
    private String value;
    private String sedOfferId;

    public PromotionAcctBulkRestrictionRow(String value, String sedOfferId) {
        this.value = value;
        this.sedOfferId = sedOfferId;
    }

    public PromotionAcctBulkRestrictionRow() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSedOfferId() {
        return sedOfferId;
    }

    public void setSedOfferId(String sedOfferId) {
        this.sedOfferId = sedOfferId;
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder().append(this.getValue()).append(sedOfferId).toHashCode();
    }

    @Override
    public boolean equals(Object otherPromotion){
        if (this == otherPromotion) {
            return true;
        }
        if (!(otherPromotion instanceof PromotionAcctBulkRestrictionRow)) {
            return false;
        }

        PromotionAcctBulkRestrictionRow that = (PromotionAcctBulkRestrictionRow) otherPromotion;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(value, that.value);
        eb.append(sedOfferId, that.sedOfferId);
        return eb.isEquals();

    }
}

