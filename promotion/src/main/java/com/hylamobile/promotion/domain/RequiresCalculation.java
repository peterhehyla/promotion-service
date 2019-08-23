package com.hylamobile.promotion.domain;

import com.hylamobile.promotion.dto.AdjustmentInfoDTO;

public interface RequiresCalculation<T> extends Comparable<T> {
    Money getCalculatedAmount(Money amount, AdjustmentInfoDTO adjustmentInfo);
}

