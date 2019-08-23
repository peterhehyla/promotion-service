package com.hylamobile.promotion.repository;

import com.hylamobile.promotion.domain.RequestResponseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestResponseLogRepository extends JpaRepository<RequestResponseLog, Long> {
}
