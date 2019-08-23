package com.hylamobile.promotion.utils;

import com.hylamobile.promotion.domain.CompanyCategory;

import java.io.Serializable;
import java.util.Comparator;

public class CompanyCategoryComparator implements Comparator<CompanyCategory>, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public int compare(CompanyCategory o1, CompanyCategory o2) {
        return o1.getCategory().compareTo(o2.getCategory());
    }
}
