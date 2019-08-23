package com.hylamobile.promotion.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class CustomerDTO {
    private String loanNumber;
    private String newDeviceModel;
    private String contractTerm;
    private String plan;
    private String portIn;
    private String orderRequest;
    private String upgradeReason;
    private List<String> features;
    private String phoneNumber;
    //get from verizon device serial
    private List<String> imeis;
}
