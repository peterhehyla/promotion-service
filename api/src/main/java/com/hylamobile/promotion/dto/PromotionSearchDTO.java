package com.hylamobile.promotion.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hylamobile.promotion.enums.PromotionAppliedTo;
import com.hylamobile.promotion.enums.PromotionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.time.FastDateFormat;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PromotionSearchDTO implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Boolean active;

    private Set<Long> categoryIds;

    private Date startDate;

    private Date endDate;

    private Date minStartDate;

    private Date maxEndDate;

    private String promotionCode;

    private String promotionCodeWildCard;

    private Set<PromotionType> types;

    private Boolean visible;

    private Date tradeInDate;

    private String timezone;

    private Set<PromotionAppliedTo> appliedTo = new HashSet<PromotionAppliedTo>();

    private long affectedCompanyId;
    @Deprecated
    private Long promotionCompanyId;

    private Date date;

    private boolean verificationRequired;

    private boolean spoEligible;

    private boolean postProcessPromotions;

    private String promotionDescription;

    // This flag is set to true if and only if we want best promotion irrespective of flag that is available in constant
    // table.
    private Boolean fetchBestPromotion = Boolean.FALSE;

    private String sortBy;

    private String sortOrder;

    private boolean skipMinValueCheck;

    private boolean frpEligible = false;

    private String companyName;

    private Set<PromotionQualifierDTO> promotionQualifiers = new TreeSet<>();

    private String currencyCode;
    private Set<String> modelCodes;
    @Setter(AccessLevel.NONE)
    private String modelCode;
    private ItemDetailDTO itemDetail;
    private boolean opus;
    private CustomerDTO customer;

    public void setType(final PromotionType promotionType) {
        setTypes(new HashSet<>(Arrays.asList(new PromotionType[] { promotionType })));
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
        if (modelCode != null) {
            setModelCodes(new HashSet<String>(Arrays.asList(new String[] { modelCode })));
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(null).append(active).append(asTree(modelCodes)).append(asTree(categoryIds))
                .append(startDate != null ? getUserLocaleDateFormat().format(startDate) : null)
                .append(endDate != null ? getUserLocaleDateFormat().format(endDate) : null)
                .append(tradeInDate != null ? getUserLocaleDateFormat().format(tradeInDate) : null)
                .append(promotionCode).append(promotionCodeWildCard).append(asTree(types)).append(visible).append(modelCode)
                .append(timezone).append(asTree(appliedTo)).append(affectedCompanyId)
                .append(promotionCompanyId).append(active).append(verificationRequired)
                .append(date != null ? getUserLocaleDateFormat().format(date) : null).append(currencyCode).append(opus)
                .append(promotionQualifiers).append(spoEligible).append(frpEligible).append(companyName).toString();
    }

    private static <T extends Comparable<T>> TreeSet<T> asTree(Collection<T> collection) {
        TreeSet<T> tree = new TreeSet<>();
        if (collection != null) {
            for (T item : collection) {
                if (item != null) {
                    tree.add(item);
                }
            }
        }
        return tree;
    }

    public static FastDateFormat getUserLocaleDateFormat(){
        return FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ss'Z'",
                TimeZone.getTimeZone("UTC"));

    }
}

