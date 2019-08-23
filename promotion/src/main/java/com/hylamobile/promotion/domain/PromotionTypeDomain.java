package com.hylamobile.promotion.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ref_promotion_type database table.
 *
 */
@Entity
@Table(name="ref_promotion_type")
@NamedQuery(name="PromotionTypeDomain.findAll", query="SELECT p FROM PromotionTypeDomain p")
@Setter
@Getter
public class PromotionTypeDomain implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="promotiontypeid")
    private Integer promotionTypeId;
    @Column(name="active")
    private Boolean active;
    @Column(name="code")
    private String code;
    @Column(name="displaykey")
    private String displayKey;
    @Column(name="sortorder")
    private Integer sortOrder;
    @Version
    @Column(name="version")
    private Integer version;

    public PromotionTypeDomain() {
    }
}
