package com.hylamobile.promotion.repository;

import com.hylamobile.promotion.domain.Constant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ConstantRepository extends JpaRepository<Constant, Long> {
    List<Constant> findByCode(String code);
}
