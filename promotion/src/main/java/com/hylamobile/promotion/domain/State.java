package com.hylamobile.promotion.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the ref_state database table.
 *
 */
@Entity
@Table(name="ref_state")
@Getter
@Setter
public class State implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="stateid", unique=true, nullable=false)
    private Long stateId;

    @Column(nullable=false, length=255)
    private String code;
    @Column(name="createddate")
    private Timestamp createdDate;

    @Column(nullable=false, length=255)
    private String description;
    @Column(name="lastupdateddate")
    private Timestamp lastUpdatedDate;

    @Column(nullable=false, length=255)
    private String locale;

    @Column(name="timezone", nullable=false, length=100)
    private String timeZone;

    @Column(nullable=false)
    @Version
    private Integer version;
    @Column(name="warehousecompanyid")
    private Long warehouseCompanyId;


}
