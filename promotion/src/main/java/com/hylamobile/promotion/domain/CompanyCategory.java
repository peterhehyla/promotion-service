package com.hylamobile.promotion.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the fs_company_category database table.
 * 
 */
@Entity
@Table(name="fs_company_category")
@NamedQuery(name="CompanyCategory.findAll", query="SELECT c FROM CompanyCategory c")
public class CompanyCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long companycategoryid;

	private Timestamp createddate;

	private Timestamp lastupdateddate;

	private Integer version;

	//bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(name="companyid")
	private Company company;

	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="categoryid")
	private Category category;

	public CompanyCategory() {
	}

	public Long getCompanycategoryid() {
		return this.companycategoryid;
	}

	public void setCompanycategoryid(Long companycategoryid) {
		this.companycategoryid = companycategoryid;
	}

	public Timestamp getCreateddate() {
		return this.createddate;
	}

	public void setCreateddate(Timestamp createddate) {
		this.createddate = createddate;
	}

	public Timestamp getLastupdateddate() {
		return this.lastupdateddate;
	}

	public void setLastupdateddate(Timestamp lastupdateddate) {
		this.lastupdateddate = lastupdateddate;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
