package com.hylamobile.promotion.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the ref_promotion_acct_bulk_restrict database table.
 * 
 */
@Entity
@Table(name="ref_promotion_acct_bulk_restrict")
@NamedQuery(name="PromotionAcctBulkRestrict.findAll", query="SELECT r FROM PromotionAcctBulkRestrict r")
public class PromotionAcctBulkRestrict implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long createdby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createddate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date enddate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastupdateddate;

	private Long promotionacctbulkrestrictid;
	@Id
	private Long promotioncriterionid;

	private Long promotionid;

	@Column(name="sed_offer_id")
	private String sedOfferId;

	private Long updatedby;

	private String value;

	private Integer version;

	public PromotionAcctBulkRestrict() {
	}

	public Long getCreatedby() {
		return this.createdby;
	}

	public void setCreatedby(Long createdby) {
		this.createdby = createdby;
	}

	public Date getCreateddate() {
		return this.createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public Date getEnddate() {
		return this.enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public Date getLastupdateddate() {
		return this.lastupdateddate;
	}

	public void setLastupdateddate(Date lastupdateddate) {
		this.lastupdateddate = lastupdateddate;
	}

	public Long getPromotionacctbulkrestrictid() {
		return this.promotionacctbulkrestrictid;
	}

	public void setPromotionacctbulkrestrictid(Long promotionacctbulkrestrictid) {
		this.promotionacctbulkrestrictid = promotionacctbulkrestrictid;
	}

	public Long getPromotioncriterionid() {
		return this.promotioncriterionid;
	}

	public void setPromotioncriterionid(Long promotioncriterionid) {
		this.promotioncriterionid = promotioncriterionid;
	}

	public Long getPromotionid() {
		return this.promotionid;
	}

	public void setPromotionid(Long promotionid) {
		this.promotionid = promotionid;
	}

	public String getSedOfferId() {
		return this.sedOfferId;
	}

	public void setSedOfferId(String sedOfferId) {
		this.sedOfferId = sedOfferId;
	}

	public Long getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(Long updatedby) {
		this.updatedby = updatedby;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
