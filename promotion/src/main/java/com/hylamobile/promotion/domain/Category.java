package com.hylamobile.promotion.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the ref_category database table.
 * 
 */
@Entity
@Table(name="ref_category")
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
public class Category implements Serializable,Comparable<Category>  {
	private static final long serialVersionUID = 1L;

	@Id
	private Long categoryid;

	private Boolean active;

	private String adjustmentcalculationtype;

	private BigDecimal adjustmentminimum;

	private Integer b2binvoiceidentifier;

	private Integer cartitemcountlimit;

	private String categorycode;

	private String chatid;

	private Timestamp createddate;

	private Long currencyid;

	@Column(name="defaultstartprice_amount")
	private BigDecimal defaultstartpriceAmount;

	@Column(name="defaultstartprice_currency")
	private String defaultstartpriceCurrency;

	@Column(name="description_labelcode")
	private Long descriptionLabelcode;

	@Column(name="displayname_labelcode")
	private Long displaynameLabelcode;

	private Boolean displaypricing;

	private Boolean displayrecylemodeltext;

	private Boolean displayrollreceipt;

	private Boolean doublesideprinting;

	@Column(name="enable_select_diferrentdevice")
	private Boolean enableSelectDiferrentdevice;

	private Boolean enablecustomerreceiptemail;

	private Boolean enablemtnsearch;

	private String esindexname;

	private String excludedsellerids;

	private Boolean fraudcheck;

	@Column(name="hylacatalog_api_password")
	private String hylacatalogApiPassword;

	@Column(name="hylacatalog_api_username")
	private String hylacatalogApiUsername;

	private String indexexcludewords;

	private Long infopiacategoryid;

	private Integer invoiceidentifier;

	private String invoicesequencename;

	@Column(name="is_imei_space_validation_excluded")
	private Boolean isImeiSpaceValidationExcluded;

	private String itemsequencename;

	private Timestamp lastupdateddate;

	@Column(name="listingdescription_labelcode")
	private Long listingdescriptionLabelcode;

	@Column(name="luhn_validation_length")
	private Long luhnValidationLength;

	private BigDecimal minimumcredit;

	@Column(name="minimumlistprice_amount")
	private BigDecimal minimumlistpriceAmount;

	@Column(name="minimumlistprice_currency")
	private String minimumlistpriceCurrency;

	@Column(name="minimumlistprice_exchangerate")
	private BigDecimal minimumlistpriceExchangerate;

	@Column(name="minimumlistprice_systemamount")
	private BigDecimal minimumlistpriceSystemamount;

	@Column(name="minimumlistprice_systemcurrency")
	private String minimumlistpriceSystemcurrency;

	private Boolean primarycategory;

	@Column(name="privacy_policy_labelcode")
	private Long privacyPolicyLabelcode;

	private Integer quoteexpirationdays;

	@Column(name="recyclebuttontext_labelcode")
	private Long recyclebuttontextLabelcode;

	@Column(name="recyclecredit_labelcode")
	private Long recyclecreditLabelcode;

	@Column(name="recyclemessage_labelcode")
	private Long recyclemessageLabelcode;

	private Boolean reportingappnewstyle;

	private String roundingtype;

	@Column(name="search_interval")
	private Integer searchInterval;

	@Column(name="search_maxdays")
	private Integer searchMaxdays;

	@Column(name="search_minrecords")
	private Integer searchMinrecords;

	private String searchexcludewords;

	private Integer shipmentquantity;

	private Long shippingaccountid;

	private Boolean showmodeltitle;

	private String skucode;

	@Column(name="startpricethreshold_amount")
	private BigDecimal startpricethresholdAmount;

	@Column(name="startpricethreshold_currency")
	private String startpricethresholdCurrency;

	@Column(name="terms_and_condition_bib_labelcode")
	private Integer termsAndConditionBibLabelcode;

	@Column(name="terms_and_condition_labelcode")
	private Integer termsAndConditionLabelcode;

	@Column(name="terms_and_condition_plain_labelcode")
	private Integer termsAndConditionPlainLabelcode;

	@Column(name="terms_and_condition_turn_in_labelcode")
	private Integer termsAndConditionTurnInLabelcode;

	@Column(name="trade_in_instructions_labelcode")
	private Integer tradeInInstructionsLabelcode;

	private Boolean validateemployee;

	private Integer version;

	@Column(name="weight_lbs")
	private Integer weightLbs;

	@Column(name="weight_oz")
	private Integer weightOz;

	private Long zerovaluetradereminder;

	//bi-directional many-to-one association to CompanyCategory
	@OneToMany(mappedBy="category")
	private List<CompanyCategory> companyCategories;

	//bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(name="warehousecompanyid")
	private Company warehouseCompany;

	public Category() {
	}

	public Long getCategoryid() {
		return this.categoryid;
	}

	public void setCategoryid(Long categoryid) {
		this.categoryid = categoryid;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getAdjustmentcalculationtype() {
		return this.adjustmentcalculationtype;
	}

	public void setAdjustmentcalculationtype(String adjustmentcalculationtype) {
		this.adjustmentcalculationtype = adjustmentcalculationtype;
	}

	public BigDecimal getAdjustmentminimum() {
		return this.adjustmentminimum;
	}

	public void setAdjustmentminimum(BigDecimal adjustmentminimum) {
		this.adjustmentminimum = adjustmentminimum;
	}

	public Integer getB2binvoiceidentifier() {
		return this.b2binvoiceidentifier;
	}

	public void setB2binvoiceidentifier(Integer b2binvoiceidentifier) {
		this.b2binvoiceidentifier = b2binvoiceidentifier;
	}

	public Integer getCartitemcountlimit() {
		return this.cartitemcountlimit;
	}

	public void setCartitemcountlimit(Integer cartitemcountlimit) {
		this.cartitemcountlimit = cartitemcountlimit;
	}

	public String getCategorycode() {
		return this.categorycode;
	}

	public void setCategorycode(String categorycode) {
		this.categorycode = categorycode;
	}

	public String getChatid() {
		return this.chatid;
	}

	public void setChatid(String chatid) {
		this.chatid = chatid;
	}

	public Timestamp getCreateddate() {
		return this.createddate;
	}

	public void setCreateddate(Timestamp createddate) {
		this.createddate = createddate;
	}

	public Long getCurrencyid() {
		return this.currencyid;
	}

	public void setCurrencyid(Long currencyid) {
		this.currencyid = currencyid;
	}

	public BigDecimal getDefaultstartpriceAmount() {
		return this.defaultstartpriceAmount;
	}

	public void setDefaultstartpriceAmount(BigDecimal defaultstartpriceAmount) {
		this.defaultstartpriceAmount = defaultstartpriceAmount;
	}

	public String getDefaultstartpriceCurrency() {
		return this.defaultstartpriceCurrency;
	}

	public void setDefaultstartpriceCurrency(String defaultstartpriceCurrency) {
		this.defaultstartpriceCurrency = defaultstartpriceCurrency;
	}

	public Long getDescriptionLabelcode() {
		return this.descriptionLabelcode;
	}

	public void setDescriptionLabelcode(Long descriptionLabelcode) {
		this.descriptionLabelcode = descriptionLabelcode;
	}

	public Long getDisplaynameLabelcode() {
		return this.displaynameLabelcode;
	}

	public void setDisplaynameLabelcode(Long displaynameLabelcode) {
		this.displaynameLabelcode = displaynameLabelcode;
	}

	public Boolean getDisplaypricing() {
		return this.displaypricing;
	}

	public void setDisplaypricing(Boolean displaypricing) {
		this.displaypricing = displaypricing;
	}

	public Boolean getDisplayrecylemodeltext() {
		return this.displayrecylemodeltext;
	}

	public void setDisplayrecylemodeltext(Boolean displayrecylemodeltext) {
		this.displayrecylemodeltext = displayrecylemodeltext;
	}

	public Boolean getDisplayrollreceipt() {
		return this.displayrollreceipt;
	}

	public void setDisplayrollreceipt(Boolean displayrollreceipt) {
		this.displayrollreceipt = displayrollreceipt;
	}

	public Boolean getDoublesideprinting() {
		return this.doublesideprinting;
	}

	public void setDoublesideprinting(Boolean doublesideprinting) {
		this.doublesideprinting = doublesideprinting;
	}

	public Boolean getEnableSelectDiferrentdevice() {
		return this.enableSelectDiferrentdevice;
	}

	public void setEnableSelectDiferrentdevice(Boolean enableSelectDiferrentdevice) {
		this.enableSelectDiferrentdevice = enableSelectDiferrentdevice;
	}

	public Boolean getEnablecustomerreceiptemail() {
		return this.enablecustomerreceiptemail;
	}

	public void setEnablecustomerreceiptemail(Boolean enablecustomerreceiptemail) {
		this.enablecustomerreceiptemail = enablecustomerreceiptemail;
	}

	public Boolean getEnablemtnsearch() {
		return this.enablemtnsearch;
	}

	public void setEnablemtnsearch(Boolean enablemtnsearch) {
		this.enablemtnsearch = enablemtnsearch;
	}

	public String getEsindexname() {
		return this.esindexname;
	}

	public void setEsindexname(String esindexname) {
		this.esindexname = esindexname;
	}

	public String getExcludedsellerids() {
		return this.excludedsellerids;
	}

	public void setExcludedsellerids(String excludedsellerids) {
		this.excludedsellerids = excludedsellerids;
	}

	public Boolean getFraudcheck() {
		return this.fraudcheck;
	}

	public void setFraudcheck(Boolean fraudcheck) {
		this.fraudcheck = fraudcheck;
	}

	public String getHylacatalogApiPassword() {
		return this.hylacatalogApiPassword;
	}

	public void setHylacatalogApiPassword(String hylacatalogApiPassword) {
		this.hylacatalogApiPassword = hylacatalogApiPassword;
	}

	public String getHylacatalogApiUsername() {
		return this.hylacatalogApiUsername;
	}

	public void setHylacatalogApiUsername(String hylacatalogApiUsername) {
		this.hylacatalogApiUsername = hylacatalogApiUsername;
	}

	public String getIndexexcludewords() {
		return this.indexexcludewords;
	}

	public void setIndexexcludewords(String indexexcludewords) {
		this.indexexcludewords = indexexcludewords;
	}

	public Long getInfopiacategoryid() {
		return this.infopiacategoryid;
	}

	public void setInfopiacategoryid(Long infopiacategoryid) {
		this.infopiacategoryid = infopiacategoryid;
	}

	public Integer getInvoiceidentifier() {
		return this.invoiceidentifier;
	}

	public void setInvoiceidentifier(Integer invoiceidentifier) {
		this.invoiceidentifier = invoiceidentifier;
	}

	public String getInvoicesequencename() {
		return this.invoicesequencename;
	}

	public void setInvoicesequencename(String invoicesequencename) {
		this.invoicesequencename = invoicesequencename;
	}

	public Boolean getIsImeiSpaceValidationExcluded() {
		return this.isImeiSpaceValidationExcluded;
	}

	public void setIsImeiSpaceValidationExcluded(Boolean isImeiSpaceValidationExcluded) {
		this.isImeiSpaceValidationExcluded = isImeiSpaceValidationExcluded;
	}

	public String getItemsequencename() {
		return this.itemsequencename;
	}

	public void setItemsequencename(String itemsequencename) {
		this.itemsequencename = itemsequencename;
	}

	public Timestamp getLastupdateddate() {
		return this.lastupdateddate;
	}

	public void setLastupdateddate(Timestamp lastupdateddate) {
		this.lastupdateddate = lastupdateddate;
	}

	public Long getListingdescriptionLabelcode() {
		return this.listingdescriptionLabelcode;
	}

	public void setListingdescriptionLabelcode(Long listingdescriptionLabelcode) {
		this.listingdescriptionLabelcode = listingdescriptionLabelcode;
	}

	public Long getLuhnValidationLength() {
		return this.luhnValidationLength;
	}

	public void setLuhnValidationLength(Long luhnValidationLength) {
		this.luhnValidationLength = luhnValidationLength;
	}

	public BigDecimal getMinimumcredit() {
		return this.minimumcredit;
	}

	public void setMinimumcredit(BigDecimal minimumcredit) {
		this.minimumcredit = minimumcredit;
	}

	public BigDecimal getMinimumlistpriceAmount() {
		return this.minimumlistpriceAmount;
	}

	public void setMinimumlistpriceAmount(BigDecimal minimumlistpriceAmount) {
		this.minimumlistpriceAmount = minimumlistpriceAmount;
	}

	public String getMinimumlistpriceCurrency() {
		return this.minimumlistpriceCurrency;
	}

	public void setMinimumlistpriceCurrency(String minimumlistpriceCurrency) {
		this.minimumlistpriceCurrency = minimumlistpriceCurrency;
	}

	public BigDecimal getMinimumlistpriceExchangerate() {
		return this.minimumlistpriceExchangerate;
	}

	public void setMinimumlistpriceExchangerate(BigDecimal minimumlistpriceExchangerate) {
		this.minimumlistpriceExchangerate = minimumlistpriceExchangerate;
	}

	public BigDecimal getMinimumlistpriceSystemamount() {
		return this.minimumlistpriceSystemamount;
	}

	public void setMinimumlistpriceSystemamount(BigDecimal minimumlistpriceSystemamount) {
		this.minimumlistpriceSystemamount = minimumlistpriceSystemamount;
	}

	public String getMinimumlistpriceSystemcurrency() {
		return this.minimumlistpriceSystemcurrency;
	}

	public void setMinimumlistpriceSystemcurrency(String minimumlistpriceSystemcurrency) {
		this.minimumlistpriceSystemcurrency = minimumlistpriceSystemcurrency;
	}

	public Boolean getPrimarycategory() {
		return this.primarycategory;
	}

	public void setPrimarycategory(Boolean primarycategory) {
		this.primarycategory = primarycategory;
	}

	public Long getPrivacyPolicyLabelcode() {
		return this.privacyPolicyLabelcode;
	}

	public void setPrivacyPolicyLabelcode(Long privacyPolicyLabelcode) {
		this.privacyPolicyLabelcode = privacyPolicyLabelcode;
	}

	public Integer getQuoteexpirationdays() {
		return this.quoteexpirationdays;
	}

	public void setQuoteexpirationdays(Integer quoteexpirationdays) {
		this.quoteexpirationdays = quoteexpirationdays;
	}

	public Long getRecyclebuttontextLabelcode() {
		return this.recyclebuttontextLabelcode;
	}

	public void setRecyclebuttontextLabelcode(Long recyclebuttontextLabelcode) {
		this.recyclebuttontextLabelcode = recyclebuttontextLabelcode;
	}

	public Long getRecyclecreditLabelcode() {
		return this.recyclecreditLabelcode;
	}

	public void setRecyclecreditLabelcode(Long recyclecreditLabelcode) {
		this.recyclecreditLabelcode = recyclecreditLabelcode;
	}

	public Long getRecyclemessageLabelcode() {
		return this.recyclemessageLabelcode;
	}

	public void setRecyclemessageLabelcode(Long recyclemessageLabelcode) {
		this.recyclemessageLabelcode = recyclemessageLabelcode;
	}

	public Boolean getReportingappnewstyle() {
		return this.reportingappnewstyle;
	}

	public void setReportingappnewstyle(Boolean reportingappnewstyle) {
		this.reportingappnewstyle = reportingappnewstyle;
	}

	public String getRoundingtype() {
		return this.roundingtype;
	}

	public void setRoundingtype(String roundingtype) {
		this.roundingtype = roundingtype;
	}

	public Integer getSearchInterval() {
		return this.searchInterval;
	}

	public void setSearchInterval(Integer searchInterval) {
		this.searchInterval = searchInterval;
	}

	public Integer getSearchMaxdays() {
		return this.searchMaxdays;
	}

	public void setSearchMaxdays(Integer searchMaxdays) {
		this.searchMaxdays = searchMaxdays;
	}

	public Integer getSearchMinrecords() {
		return this.searchMinrecords;
	}

	public void setSearchMinrecords(Integer searchMinrecords) {
		this.searchMinrecords = searchMinrecords;
	}

	public String getSearchexcludewords() {
		return this.searchexcludewords;
	}

	public void setSearchexcludewords(String searchexcludewords) {
		this.searchexcludewords = searchexcludewords;
	}

	public Integer getShipmentquantity() {
		return this.shipmentquantity;
	}

	public void setShipmentquantity(Integer shipmentquantity) {
		this.shipmentquantity = shipmentquantity;
	}

	public Long getShippingaccountid() {
		return this.shippingaccountid;
	}

	public void setShippingaccountid(Long shippingaccountid) {
		this.shippingaccountid = shippingaccountid;
	}

	public Boolean getShowmodeltitle() {
		return this.showmodeltitle;
	}

	public void setShowmodeltitle(Boolean showmodeltitle) {
		this.showmodeltitle = showmodeltitle;
	}

	public String getSkucode() {
		return this.skucode;
	}

	public void setSkucode(String skucode) {
		this.skucode = skucode;
	}

	public BigDecimal getStartpricethresholdAmount() {
		return this.startpricethresholdAmount;
	}

	public void setStartpricethresholdAmount(BigDecimal startpricethresholdAmount) {
		this.startpricethresholdAmount = startpricethresholdAmount;
	}

	public String getStartpricethresholdCurrency() {
		return this.startpricethresholdCurrency;
	}

	public void setStartpricethresholdCurrency(String startpricethresholdCurrency) {
		this.startpricethresholdCurrency = startpricethresholdCurrency;
	}

	public Integer getTermsAndConditionBibLabelcode() {
		return this.termsAndConditionBibLabelcode;
	}

	public void setTermsAndConditionBibLabelcode(Integer termsAndConditionBibLabelcode) {
		this.termsAndConditionBibLabelcode = termsAndConditionBibLabelcode;
	}

	public Integer getTermsAndConditionLabelcode() {
		return this.termsAndConditionLabelcode;
	}

	public void setTermsAndConditionLabelcode(Integer termsAndConditionLabelcode) {
		this.termsAndConditionLabelcode = termsAndConditionLabelcode;
	}

	public Integer getTermsAndConditionPlainLabelcode() {
		return this.termsAndConditionPlainLabelcode;
	}

	public void setTermsAndConditionPlainLabelcode(Integer termsAndConditionPlainLabelcode) {
		this.termsAndConditionPlainLabelcode = termsAndConditionPlainLabelcode;
	}

	public Integer getTermsAndConditionTurnInLabelcode() {
		return this.termsAndConditionTurnInLabelcode;
	}

	public void setTermsAndConditionTurnInLabelcode(Integer termsAndConditionTurnInLabelcode) {
		this.termsAndConditionTurnInLabelcode = termsAndConditionTurnInLabelcode;
	}

	public Integer getTradeInInstructionsLabelcode() {
		return this.tradeInInstructionsLabelcode;
	}

	public void setTradeInInstructionsLabelcode(Integer tradeInInstructionsLabelcode) {
		this.tradeInInstructionsLabelcode = tradeInInstructionsLabelcode;
	}

	public Boolean getValidateemployee() {
		return this.validateemployee;
	}

	public void setValidateemployee(Boolean validateemployee) {
		this.validateemployee = validateemployee;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getWeightLbs() {
		return this.weightLbs;
	}

	public void setWeightLbs(Integer weightLbs) {
		this.weightLbs = weightLbs;
	}

	public Integer getWeightOz() {
		return this.weightOz;
	}

	public void setWeightOz(Integer weightOz) {
		this.weightOz = weightOz;
	}

	public Long getZerovaluetradereminder() {
		return this.zerovaluetradereminder;
	}

	public void setZerovaluetradereminder(Long zerovaluetradereminder) {
		this.zerovaluetradereminder = zerovaluetradereminder;
	}

	public List<CompanyCategory> getCompanyCategories() {
		return this.companyCategories;
	}

	public void setCompanyCategories(List<CompanyCategory> companyCategories) {
		this.companyCategories = companyCategories;
	}

	public CompanyCategory addFsCompanyCategory(CompanyCategory fsCompanyCategory) {
		getCompanyCategories().add(fsCompanyCategory);
		fsCompanyCategory.setCategory(this);

		return fsCompanyCategory;
	}

	public CompanyCategory removeFsCompanyCategory(CompanyCategory fsCompanyCategory) {
		getCompanyCategories().remove(fsCompanyCategory);
		fsCompanyCategory.setCategory(null);

		return fsCompanyCategory;
	}

	public Company getWarehouseCompany() {
		return this.warehouseCompany;
	}

	public void setWarehouseCompany(Company warehouseCompany) {
		this.warehouseCompany = warehouseCompany;
	}
	/**
	 * @param o
	 * @return
	 */
	@Override
	public int compareTo(Category o) {
		String s1 = categorycode == null ? "" : categorycode;
		String s2 = o.getCategorycode() == null ? "" : o.getCategorycode();
		return s1.compareTo(s2);
	}
}
