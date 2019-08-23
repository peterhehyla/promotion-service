package com.hylamobile.promotion.service.impl;

import com.hylamobile.promotion.domain.Company;
import com.hylamobile.promotion.repository.CompanyRepository;
import com.hylamobile.promotion.service.CompanyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Resource
    private CompanyRepository companyRepository;
    @Override public Company findById(Long companyId) {
        return companyRepository.findById(companyId).orElse(null);
    }
}
