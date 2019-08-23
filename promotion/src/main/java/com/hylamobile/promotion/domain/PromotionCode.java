package com.hylamobile.promotion.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Date;


/**
 * The persistent class for the ref_promotioncodes database table.
 * 
 */
@Getter
@Setter
public class PromotionCode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private Boolean active;

	private Long batchid;

	private Long createdby;

	private Date createddate;

	private String devicenumber;

	private Date lastupdateddate;

	private String promotioncode;

	private Date tradeindate;

	private Long updatedby;
	@Version
	private Integer version;

	//bi-directional many-to-one association to RefPromotion
	@ManyToOne
	@JoinColumn(name="promotionid")
	private Promotion promotion;

	public PromotionCode() {
	}

}
