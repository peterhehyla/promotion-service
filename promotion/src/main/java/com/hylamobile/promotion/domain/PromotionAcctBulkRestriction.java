package com.hylamobile.promotion.domain;

import com.hylamobile.promotion.bean.PromotionAcctBulkRestrictionRow;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.util.Date;

public class PromotionAcctBulkRestriction  implements Restriction {
    private static final long serialVersionUID = -7012135601108632345L;
    private long promotionAcctBulkRestrictId = -1;
    private int version;
    private Long promotionId;

    private long promotionCriterionId;

    private String value;

    private String sedOfferId;

    private Date endDate;

    private Date lastUpdatedDate;

    private Date createdDate;

    private Long updatedBy;

    private Long createdBy;


    public PromotionAcctBulkRestriction() {
    }

    public PromotionAcctBulkRestriction(long promotionAcctBulkRestrictId, Long promotionId, long promotionCriterionId,
            String value, String sedOfferId, Date endDate) {
        this.promotionAcctBulkRestrictId = promotionAcctBulkRestrictId;
        this.promotionId = promotionId;
        this.promotionCriterionId = promotionCriterionId;
        this.value = value;
        this.sedOfferId = sedOfferId;
        this.endDate = endDate;
    }

    public PromotionAcctBulkRestriction(Promotion promotion, long promotionCriterionId, PromotionAcctBulkRestrictionRow row) {
        this.promotionId = promotion.getPromotionId();
        this.promotionCriterionId = promotionCriterionId;
        this.value = row.getValue();
        this.sedOfferId = row.getSedOfferId();
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public long getPromotionCriterionId() {
        return promotionCriterionId;
    }

    public void setPromotionCriterionId(long promotionCriterionId) {
        this.promotionCriterionId = promotionCriterionId;
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
    public Date getEndDate() {
        return endDate;
    }
    @Override
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public long getPromotionAcctBulkRestrictId() {
        return promotionAcctBulkRestrictId;
    }

    public void setPromotionAcctBulkRestrictId(long promotionAcctBulkRestrictId) {
        this.promotionAcctBulkRestrictId = promotionAcctBulkRestrictId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PromotionAcctBulkRestriction)) {
            return false;
        }

        PromotionAcctBulkRestriction that = (PromotionAcctBulkRestriction) obj;

        if(getPromotionAcctBulkRestrictId()==(-1L) || that.getPromotionAcctBulkRestrictId()==(-1L)){
            return false;
        }

        EqualsBuilder eb = new EqualsBuilder();
        eb.append(getPromotionAcctBulkRestrictId(), that.getPromotionAcctBulkRestrictId());
        return eb.isEquals();
    }

    /**
     * @return
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(3, 7).append(promotionId).append(value).toHashCode();
    }
}
