package com.hylamobile.promotion.domain;

import com.hylamobile.promotion.bean.PromotionAcctBulkRestrictionRow;
import com.hylamobile.promotion.dto.AdjustmentInfoDTO;
import com.hylamobile.promotion.utils.PriceGuideUtils;
import com.hylamobile.trade.Calculation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

/**
 * The persistent class for the ref_promotion database table.
 * 
 */
@Getter
@Setter
@NoArgsConstructor
public class Promotion implements Serializable,RequiresCalculation<Promotion> {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="promotionid")
	private Long promotionId;
	@Column(name="active")
	private Boolean active;
	@Column(name="affectedcompanyid")
	private Long affectedCompanyId;

	@Column(name="applicability_limit")
	private Integer applicabilityLimit;
	@Column(name="applyforanyvaluedevice")
	private Boolean applyForAnyValueDevice;

	private Boolean auto;

	@Column(name="calculation_currency")
	private String calculationCurrency;

	@Column(name="calculation_type")
	private String calculationType;

	@Column(name="calculation_value")
	private BigDecimal calculationValue;
	@Transient
	private Calculation calculation;
	@Column(name="count_occurences")
	private String countOccurences;
	@Column(name="createdby")
	private Long createdBy;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="createddate")
	private Date createdDate;

	private String description;

	@Column(name="disclaimercontent_labelcode")
	private Long disclaimerContentLabelcode;
	@Transient
	private String disclaimerContent;
	@Column(name="enableadjustmentdeduction")
	private Boolean enableAdjustmentDeduction;
	@Column(name="enableimeiuniquevalidation")
	private Boolean enableImeiUniqueValidation;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="enddate")
	private Date endDate;

	@Column(name="frp_amount")
	private BigDecimal frpAmount;

	@Column(name="frp_currency")
	private String frpCurrency;
	@Transient
	private Money frpCredit;
	@Column(name="frp_eligible")
	private Boolean frpEligible;

	@Column(name="has_esn")
	private Boolean hasEsn;

	@Column(name="has_tac")
	private Boolean hasTac;
    @Column(name="includerecycleitem")
	private Boolean includeRecycleItem;
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdatedDate;

	@Column(name="max_occurences")
	private Integer maxOccurences;

	@Column(name="maximumcredit_amount")
	private BigDecimal maximumCreditAmount;

	@Column(name="maximumcredit_currency")
	private String maximumCreditCurrency;
	@Transient
	private Money maximumCredit;
	@Column(name="maximumcredit_exchangerate")
	private BigDecimal maximumCreditExchangeRate;

	@Column(name="maximumcredit_systemamount")
	private BigDecimal maximumCreditSystemAmount;

	@Column(name="maximumcredit_systemcurrency")
	private String maximumCreditSystemCurrency;

	@Column(name="minimumcredit_amount")
	private BigDecimal minimumCreditAmount;

	@Column(name="minimumcredit_currency")
	private String minimumCreditCurrency;
	@Transient
	private Money minimumCredit;

	@Column(name="minimumcredit_exchangerate")
	private BigDecimal minimumCreditExchangeRate;

	@Column(name="minimumcredit_systemamount")
	private BigDecimal minimumCreditSystemAmount;

	@Column(name="minimumcredit_systemcurrency")
	private String minimumCreditSystemCurrency;

	@Column(name="minimumeligible_amount")
	private BigDecimal minimumEligibleAmount;

	@Column(name="minimumeligible_currency")
	private String minimumeligibleCurrency;
	@Transient
	private Money minimumEligibleValue;

	@Column(name="mtn_validation_required")
	private Boolean mtnValidationRequired;
	@Column(name="paymentmethod")
	private String paymentMethod;
	@Column(name="promotionappliedto")
	private String promotionAppliedTo;
	@Column(name="promotioncode")
	private String promotionCode;
	@Column(name="promotioncompanyid")
	private Long promotionCompanyId;
	@Column(name="skusuffix")
	private String skuSuffix;

	@Column(name="spo_eligible")
	private Boolean spoEligible;

	@Column(name="spocredit_amount")
	private BigDecimal spoCreditAmount;

	@Column(name="spocredit_currency")
	private String spoCreditCurrency;
	@Transient
	private Money spoCredit;
	@Column(name="stackablewithbogo")
	private Boolean stackableWithBogo;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="startdate")
	private Date startDate;

	private String type;
	@Column(name="updatedby")
	private Long updatedBy;
	@Column(name="verificationinstructions")
	private String verificationInstructions;
	@Column(name="verificationrequired")
	private Boolean verificationRequired;
	@Version
	private Integer version;

	private Boolean visible;

	//bi-directional many-to-one association to FsCompanyPromotion
	@OneToMany(mappedBy="promotion")
	private List<CompanyPromotion> companyPromotions = new ArrayList<>();

	//bi-directional many-to-one association to FsModelPromotion
//	@OneToMany(mappedBy="promotion")
	private List<ModelPromotion> modelPromotions = new ArrayList<>();

	//bi-directional many-to-one association to RefPromotioncode
	@OneToMany(mappedBy="promotion")
	private List<PromotionCode> promotionCodes;
	@ManyToMany
	@JoinTable(
			name="fs_categoryquestion_response_promotion"
			, joinColumns={
			@JoinColumn(name="promotionid", nullable=false)
	}
			, inverseJoinColumns={
			@JoinColumn(name="categoryquestionresponseid", nullable=false)
	}
	)
	private SortedSet<CategoryQuestionResponsePromotion> categoryQuestionResponsePromotionSet;

	@Transient
	private MultipartFile uploadAffectedCompany;
	@Transient
	private Map<PromotionCriterion, List<String>> selectedRestrictions = new LinkedHashMap<>();

	@Transient
	private Map<PromotionCriterion, MultipartFile> selectedBulkRestrictions = new LinkedHashMap<>();
	//this is for acct_bulk type promotion criterion which is stored value and sedofferid in ref_promotion_acct_bulk_restrict
	//for new device model, tac upload
	@Transient
	private Map<PromotionCriterion, List<PromotionAcctBulkRestrictionRow>> selectedAcctBulkRestrictions = new LinkedHashMap<>();
	@Transient
	private Map<PromotionCriterion, Integer> selectedAcctBulkRestrictionCounters = new LinkedHashMap<>();
	@Transient
	private MultipartFile uploadManufacturerModel;

	@Transient
	private PromotionCode promotionAlias;

	public boolean isSpoOrFrpEligible(){
		return Boolean.TRUE.equals(getSpoEligible()) || Boolean.TRUE.equals(getFrpEligible());
	}

	public CompanyPromotion addCompanyPromotion(CompanyPromotion companyPromotion) {
		getCompanyPromotions().add(companyPromotion);
		companyPromotion.setPromotion(this);

		return companyPromotion;
	}

	public CompanyPromotion removeCompanyPromotion(CompanyPromotion companyPromotion) {
		getCompanyPromotions().remove(companyPromotion);
		companyPromotion.setPromotion(null);

		return companyPromotion;
	}

	public ModelPromotion addModelPromotion(ModelPromotion modelPromotion) {
		getModelPromotions().add(modelPromotion);

		return modelPromotion;
	}

	public ModelPromotion removeModelPromotion(ModelPromotion modelPromotion) {
		getModelPromotions().remove(modelPromotion);

		return modelPromotion;
	}

	public PromotionCode addPromotionCode(PromotionCode promotionCode) {
		getPromotionCodes().add(promotionCode);
		promotionCode.setPromotion(this);

		return promotionCode;
	}

	public PromotionCode removePromotionCode(PromotionCode promotionCode) {
		getPromotionCodes().remove(promotionCode);
		promotionCode.setPromotion(null);

		return promotionCode;
	}

	public Money getCalculatedAmount(Money credit, AdjustmentInfoDTO adjustmentInfo) {
		Money promoAmount = this.getCalculation().eval(credit);
		Money adjAmount = Money.newZeroInstance(credit.getCurrency());
		Money calCultedAmount = promoAmount.add(credit);
		Money tempPromoAmount = Money.newZeroInstance(credit.getCurrency());
		if (this.maximumCredit != null && this.minimumCredit != null) {
			int checkMaxValue = calCultedAmount.compareTo(this.maximumCredit);
			int checkMinValue = calCultedAmount.compareTo(this.minimumCredit);
			// setting the promotion value to get minimum or maximum total amount
			if ((maximumCredit.getAmount().floatValue() > 0) && (checkMaxValue == 1)) {
				promoAmount = maximumCredit.subtract(credit);
			} else if ((minimumCredit.getAmount().floatValue() > 0) && (checkMinValue == -1)) {
				promoAmount = minimumCredit.subtract(credit);
			}
		}

		/*
		 * Category category = this.getPromotionCompany().getCategories().first(); if(category.getIsRoundingRequired()){
		 * promoAmount=promoAmount.round(category.getRoundingType()); }
		 */

		if (this.getEnableAdjustmentDeduction() && adjustmentInfo != null
				&& StringUtils.isNotBlank(adjustmentInfo.getAdjustmentType())
				&& adjustmentInfo.getAdjustments() != null && adjustmentInfo.getAdjustments().size() > 0) {
			adjAmount = adjAmount.add(PriceGuideUtils.getResponsesAmount(promoAmount, adjustmentInfo, false));
			tempPromoAmount = promoAmount.add(adjAmount);
		}
		if (promoAmount.compareTo(Money.newZeroInstance(credit.getCurrency())) == 1
				&& tempPromoAmount.compareTo(Money.newZeroInstance(credit.getCurrency())) == -1) {
			return Money.newZeroInstance(credit.getCurrency());
		} else if (promoAmount.compareTo(Money.newZeroInstance(credit.getCurrency())) == -1
				&& tempPromoAmount.compareTo(Money.newZeroInstance(credit.getCurrency())) == 1) {
			return Money.newZeroInstance(credit.getCurrency());
		} else {
			promoAmount = promoAmount.add(adjAmount);
		}
		return promoAmount;
	}

	/**
	 * Compares this object with the specified object for order.  Returns a
	 * negative integer, zero, or a positive integer as this object is less
	 * than, equal to, or greater than the specified object.
	 *
	 * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
	 * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
	 * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
	 * <tt>y.compareTo(x)</tt> throws an exception.)
	 *
	 * <p>The implementor must also ensure that the relation is transitive:
	 * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
	 * <tt>x.compareTo(z)&gt;0</tt>.
	 *
	 * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
	 * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
	 * all <tt>z</tt>.
	 *
	 * <p>It is strongly recommended, but <i>not</i> strictly required that
	 * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
	 * class that implements the <tt>Comparable</tt> interface and violates
	 * this condition should clearly indicate this fact.  The recommended
	 * language is "Note: this class has a natural ordering that is
	 * inconsistent with equals."
	 *
	 * <p>In the foregoing description, the notation
	 * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
	 * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
	 * <tt>0</tt>, or <tt>1</tt> according to whether the value of
	 * <i>expression</i> is negative, zero or positive.
	 *
	 * @param promotion the object to be compared.
	 * @return a negative integer, zero, or a positive integer as this object
	 * is less than, equal to, or greater than the specified object.
	 * @throws NullPointerException if the specified object is null
	 * @throws ClassCastException   if the specified object's type prevents it
	 *                              from being compared to this object.
	 */
	@Override public int compareTo(Promotion promotion) {
		return this.getDescription().compareToIgnoreCase(promotion.getDescription());
	}
}
