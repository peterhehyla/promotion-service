package com.hylamobile.promotion.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fs_company_attribute_value database table.
 * 
 */
@Entity
@Table(name="fs_company_attribute_value")
@NamedQuery(name="CompanyAttributeValue.findAll", query="SELECT c FROM CompanyAttributeValue c")
public class CompanyAttributeValue implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long attributeid;

	private Long companyattributeid;

	private String companyattributevalue;

	//bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(name="companyid")
	private Company company;

	public CompanyAttributeValue() {
	}

	public Long getAttributeid() {
		return this.attributeid;
	}

	public void setAttributeid(Long attributeid) {
		this.attributeid = attributeid;
	}

	public Long getCompanyattributeid() {
		return this.companyattributeid;
	}

	public void setCompanyattributeid(Long companyattributeid) {
		this.companyattributeid = companyattributeid;
	}

	public String getCompanyattributevalue() {
		return this.companyattributevalue;
	}

	public void setCompanyattributevalue(String companyattributevalue) {
		this.companyattributevalue = companyattributevalue;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}
