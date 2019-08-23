package com.hylamobile.promotion.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the fs_model_promotion database table.
 * 
 */
@Getter
@Setter
public class ModelPromotion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="modelpromotionid")
	private Long modelPromotionId;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="createddate")
	private Date createdDate;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="enddate")
	private Date endDate;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="lastupdateddate")
	private Date lastUpdatedDate;
	@Column(name="modelcode")
	private String modelCode;
	@Version
	private Integer version;

	//bi-directional many-to-one association to Promotion
	@Column(name="promotionid")
	private Long promotionId;

	public ModelPromotion() {
	}


}
