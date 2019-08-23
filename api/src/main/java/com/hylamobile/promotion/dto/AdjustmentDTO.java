package com.hylamobile.promotion.dto;

import com.hylamobile.trade.CalculationType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
@Getter
@Setter
public class AdjustmentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private CalculationType calculationType = CalculationType.PERCENTAGE;

    private BigDecimal calculationAmount;

    private Currency currency;

    private String questionCode;

    private Boolean postFlipswapPercentage = Boolean.FALSE;

    private Boolean isDefaultResponse;
}
