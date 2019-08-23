package com.hylamobile.promotion.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the fs_company database table.
 * 
 */
@Entity
@Table(name="fs_company")
@NamedQuery(name="Company.findAll", query="SELECT c FROM Company c")
@Setter
@Getter
public class Company implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long companyId;

	private Boolean active;

	private String cancelexceptionnotificationmailinglist;

	private String cartonworkflowcode;

	private String city;

	private String companytimezone;

	private String companytype;

	private Long consumersiteinformationid;

	private Long corporatecompanyid;

	private String countrycode;

	private Long createdby;

	private Timestamp createddate;

	private String customerdata1;

	private String customerdata2;

	private String customerdata3;

	private String customerdata4;

	private String customerdata5;

	private String customeridentifier;

	private String dealerlocatorname;

	private String dealerlocatornote;

	private String devicesperinvoice;

	private String displaybusinesslicense;

	private String displaysecondhandgoodslicense;

	private String email;

	@Column(name="enable_signature")
	private Boolean enableSignature;

	private Boolean enablebulktrade;

	private Boolean enablequickpicks;

	private Boolean enablesavequote;

	private String exceptionnotificationday;

	private String exceptionnotificationinterval;

	private String exceptionnotificationmailinglist;

	private String exceptionnotificationwindow;

	private String fax;

	private String giftcardtype;

	private Boolean haswelcomepacksent;

	private Integer inventoryholding;

	private Timestamp lastupdateddate;

	private double latitude;

	@Column(name="locator_active")
	private Boolean locatorActive;

	@Column(name="locator_premium_placement")
	private Boolean locatorPremiumPlacement;

	private double longitude;

	private String name;

	private Boolean originalreprouting;

	private String paymentcity;

	private Boolean paymentdetailverified;

	private String paymentmethod;

	private String paymentname;

	private String paymentpostalcode;

	private Long paymentstateid;

	private String paymentstreet1;

	private String paymentstreet2;

	private Long pointofsaleid;

	private String postalcode;

	private String primaryphone;

	private Integer prplimitperday;

	private Long questiontemplatepackageid;

	private String quoteexpirationdays;
	@Column(name="reconcilenotificationmailinglist", nullable = true)
	private String reconcilenotificationmailinglist;

	@Column(name="reporting_app_context")
	private String reportingAppContext;

	private String secondaryphone;

	@Column(name="shipment_pickup_required")
	private Boolean shipmentPickupRequired;

	private Long shippingaccountid;

	private Boolean shouldreconcile;
	@ManyToOne
	@JoinColumn(name="stateid")
	private State state;

	private Long storetypeid;

	private String street1;

	private String street2;

	private Long templatepackageid;

	@Column(name="trade_return_kit_type")
	private String tradeReturnKitType;

	private String tradeoption;

	private Long updatedby;

	private String url;

	private String vendorid;
	@Version
	private Integer version;

	private String warehousecode;

	private String workflowcode;

	//bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(name="paymentcompanyid")
	private Company paymentCompany;

	//bi-directional many-to-one association to CompanyAttributeValue
	@OneToMany(mappedBy="company")
	private List<CompanyAttributeValue> companyAttributeValues;

	//bi-directional many-to-one association to CompanyCategory
	@OneToMany(mappedBy="company")
	private List<CompanyCategory> companyCategories;


	public Company() {
	}

	public CompanyAttributeValue addFsCompanyAttributeValue(CompanyAttributeValue fsCompanyAttributeValue) {
		getCompanyAttributeValues().add(fsCompanyAttributeValue);
		fsCompanyAttributeValue.setCompany(this);

		return fsCompanyAttributeValue;
	}

	public CompanyAttributeValue removeFsCompanyAttributeValue(CompanyAttributeValue fsCompanyAttributeValue) {
		getCompanyAttributeValues().remove(fsCompanyAttributeValue);
		fsCompanyAttributeValue.setCompany(null);

		return fsCompanyAttributeValue;
	}

	public List<CompanyCategory> getCompanyCategories() {
		return this.companyCategories;
	}

	public void setCompanyCategories(List<CompanyCategory> companyCategories) {
		this.companyCategories = companyCategories;
	}

	public CompanyCategory addFsCompanyCategory(CompanyCategory fsCompanyCategory) {
		getCompanyCategories().add(fsCompanyCategory);
		fsCompanyCategory.setCompany(this);

		return fsCompanyCategory;
	}

	public CompanyCategory removeFsCompanyCategory(CompanyCategory fsCompanyCategory) {
		getCompanyCategories().remove(fsCompanyCategory);
		fsCompanyCategory.setCompany(null);

		return fsCompanyCategory;
	}

}
