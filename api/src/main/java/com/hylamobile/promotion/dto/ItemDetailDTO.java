package com.hylamobile.promotion.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemDetailDTO {
    private BigDecimal applicablePrice;
    private BigDecimal baseCredit;
    private String currencyCode;
    private AdjustmentInfoDTO adjustmentInfo;
    private boolean recycle;
    private Set<QuestionResponseDTO> itemResponseSet;

}
