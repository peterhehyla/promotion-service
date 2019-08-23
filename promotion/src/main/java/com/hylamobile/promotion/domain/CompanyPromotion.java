package com.hylamobile.promotion.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the fs_company_promotion database table.
 * 
 */
@Getter
@Setter
public class CompanyPromotion implements Serializable {
	private static final long serialVersionUID = 123423534454565464L;

	@Id
	@Column(name="companypromotionid")
	private Long companyPromotionId;
	@Column(name="companyid")
	private Long companyId;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="createddate")
	private Date createdDate;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="enddate")
	private Date endDate;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="lastupdateddate")
	private Date lastUpdatedDate;
	@Version
	@Column(name="version")
	private Integer version;

	//bi-directional many-to-one association to RefPromotion
	@ManyToOne
	@JoinColumn(name="promotionid")
	private Promotion promotion;

	public CompanyPromotion() {
	}

}
