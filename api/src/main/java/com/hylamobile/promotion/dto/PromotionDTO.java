package com.hylamobile.promotion.dto;


import com.hylamobile.promotion.enums.PromotionAppliedTo;
import com.hylamobile.promotion.enums.PromotionType;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Andrew Berman
 * @version $Id$
 */
@Setter
@Getter
public class PromotionDTO implements Comparable<PromotionDTO>{

    public enum CountOccurences {
        GLOBALLY("label.promotion.count.global"), PER_COMPANY("label.promotion.count.per.company");

        private String displayKey;

        CountOccurences(String displayKey) {
            this.displayKey = displayKey;
        }

        public String getDisplayKey() {
            return displayKey;
        }
    }
    /**
     *
     */
    private static final long serialVersionUID = 23245561L;

    private Long promotionId;
    private long affectedCompanyId;

    private long promotionCompanyId;

    private Date startDate;

    private Date endDate;

    private Boolean active = Boolean.TRUE;

    private AuditInfoDTO auditInfo = new AuditInfoDTO();

    private PromotionAppliedTo appliedTo = null;

    private MoneyDTO minimumCredit;

    private MoneyDTO maximumCredit;

    private String description;

    private String promotionCode;

    private PromotionType type;

    private Boolean verificationRequired;

    private String verificationInstructions;

    private Boolean visible = Boolean.TRUE;

    private Boolean spoEligible = Boolean.FALSE;

    private MoneyDTO spoCredit;
    //if stackableWithBogo is true, apply SPO promotion no matter if BOGO promotion has been applied or not
    private boolean stackableWithBogo;

    private MoneyDTO minimumEligibleValue;

    private Set<Long> companyPromotions = new HashSet<>();

    private SortedSet<String> modelPromotions = new TreeSet<>();

    private transient Long selectedCategoryId;

    private String disclaimerContent;


    private Boolean applyForAnyValueDevice = Boolean.FALSE;

    private Boolean mtnValidationRequired = Boolean.FALSE;

    // property added for INF-183 to determine whether to include or exclude recycle items
    private Boolean includeRecycleItem;

    // property added for VZ-824 to add skuSuffix with promotion
    private String skuSuffix;

    private Integer applicabilityLimit;

    private boolean isValidPromo;

    private CountOccurences countOccurences;

    private Integer maxOccurences;

    private Boolean enableAdjustmentDeduction = Boolean.FALSE;

    private boolean hasTac;

    private boolean hasEsn;
    /**
     * Flag to enable/disable validation that IMEI being used multiple times while applying to promotion (INF-2577)
     */
    private Boolean enableUniqueImeiValidation = Boolean.FALSE;

    private Boolean frpEligible = Boolean.FALSE;
    private MoneyDTO frpCredit;

    // INF-1368: property added to retain originalPromotionId in copied promotion
    private Long originalPromotionId = new Long(-1);
    private Long dateKey;
    private Boolean isAffectedCompaniesAssociated = Boolean.FALSE;
    private Boolean isAffectedModelsAssociated = Boolean.FALSE;

    //transient fields end


    /*
     * (non-Javadoc)
     * Surprising implementation!
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(PromotionDTO o) {
        return this.getDescription().compareToIgnoreCase(o.getDescription());
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder().append(this.getPromotionId()).toHashCode();
    }

    @Override
    public boolean equals(Object otherPromotion){
        if (this == otherPromotion) {
            return true;
        }
        if (!(otherPromotion instanceof PromotionDTO)) {
            return false;
        }

        PromotionDTO that = (PromotionDTO) otherPromotion;
        if(getPromotionId()==null && that.getPromotionId()==null){
            return false;
        }
        if(getPromotionId().equals(-1L) ||that.getPromotionId().equals(-1L)){
            return false;
        }
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(getPromotionId(), that.getPromotionId());
        return eb.isEquals();

    }
}
