package com.hylamobile.promotion.service;

public interface RequestResponseLogService {
    void logRequestResponseAsynchronously(String serviceType, String identifier, String mtn, String loanNumber,
            String imei, Object request, Object response, long retryCount, String errorCode);
}
