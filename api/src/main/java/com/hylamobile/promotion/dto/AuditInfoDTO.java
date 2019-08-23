package com.hylamobile.promotion.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
public class AuditInfoDTO {
    /**
     *
     */
    private static final long serialVersionUID = -4999284044864759054L;

    private Date lastUpdatedDate;

    private Date createdDate;

    private long updatedBy;

    private long createdBy;
}
