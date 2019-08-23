package com.hylamobile.promotion.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class AdjustmentInfoDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 158568768L;

    private String adjustmentType;

    private String mixAdjustmentQuestions;

    private String maxDeductiblePercentage;

    private List<AdjustmentDTO> adjustments = new ArrayList<AdjustmentDTO>();
}
