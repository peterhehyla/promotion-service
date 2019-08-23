package com.hylamobile.promotion.repository;

import com.hylamobile.promotion.domain.PromotionTypeDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PromotionTypeRepository extends JpaRepository<PromotionTypeDomain, Integer> {
    List<PromotionTypeDomain> findByActive(boolean active);
    List<PromotionTypeDomain> findByCode(String code);
}
