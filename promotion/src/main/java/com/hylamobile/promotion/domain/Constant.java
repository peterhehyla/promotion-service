package com.hylamobile.promotion.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the ref_constant database table.
 *
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name="ref_constant")
@NamedQuery(name="Constant.findAll", query="SELECT c FROM Constant c")
public class Constant implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="constantid", unique=true, nullable=false)
    private Long constantId;

    @Column(nullable=false, length=255)
    private String code;
    @Column(name="createddate")
    private Timestamp createdDate;
    @Column(name="lastupdateddate")
    private Timestamp lastUpdatedDate;

    @Column(nullable=false, length=50)
    private String type;

    @Column(length=10000)
    private String value;
    @Version
    @Column(nullable=false)
    private Integer version;

}
