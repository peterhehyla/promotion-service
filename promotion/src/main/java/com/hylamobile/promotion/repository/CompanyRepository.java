package com.hylamobile.promotion.repository;

import com.hylamobile.promotion.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository  extends JpaRepository<Company, Long> {
}
