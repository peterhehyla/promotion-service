package com.hylamobile.promotion.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the fs_apirequestresponselog database table.
 *
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="fs_apirequestresponselog")
public class RequestResponseLog implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="FS_APIREQUESTRESPONSELOG_LOGID_GENERATOR", sequenceName="APIREQUESTRESPONSELOG_SEQ")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FS_APIREQUESTRESPONSELOG_LOGID_GENERATOR")
    @Column(unique=true, nullable=false, name = "logid")
    private Long logId;
    @Column(name = "createdby", nullable=true)
    private Long createdBy;
    @Column(name = "createddate", nullable=true)
    private Timestamp createdDate;

    @Column(length=50, name = "errorcode", nullable=true)
    private String errorCode;

    @Column(name = "identifierid", nullable=false, length=50)
    private String identifierId;

    @Column(length=50)
    private String imei;
    @Column(name = "lastupdateddate", nullable=true)
    private Timestamp lastUpdatedDate;

    @Column(name = "loannumber", length=50)
    private String loanNumber;

    @Column(length=50)
    private String mtn;
    @Column(name = "request")
    private byte[] request;
    @Column(name = "response")
    private byte[] response;

    @Column(name = "servicetype", length=50)
    private String serviceType;

    @Column(nullable=false, length=50)
    private String status;
    @Column(name = "updatedby")
    private Long updatedBy;
    @Version
    @Column(nullable=false)
    private Integer version;

}
