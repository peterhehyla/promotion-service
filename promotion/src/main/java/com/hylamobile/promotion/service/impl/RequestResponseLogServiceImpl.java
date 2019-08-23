package com.hylamobile.promotion.service.impl;

import com.hylamobile.promotion.domain.RequestResponseLog;
import com.hylamobile.promotion.enums.ConstantCode;
import com.hylamobile.promotion.repository.ConstantRepository;
import com.hylamobile.promotion.repository.RequestResponseLogRepository;
import com.hylamobile.promotion.service.RequestResponseLogService;
import com.hylamobile.promotion.utils.Constants;
import com.hylamobile.promotion.utils.ObjectToByteArrayConverter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static com.hylamobile.promotion.utils.Constants.ERROR_RESPONSE;
import static com.hylamobile.promotion.utils.Constants.MAX_RETRIED_AND_FAILED;

@Service("requestResponseLogService")
public class RequestResponseLogServiceImpl implements RequestResponseLogService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestResponseLogServiceImpl.class);
    @Autowired
    private RequestResponseLogRepository requestResponseLogRepository;

    @Resource
    private ConstantRepository constantRepository;

    @Resource
    private TaskExecutor asyncRequestResponseLoggingExecutor;

    public long getMaxRetryCount() {
        return Long.parseLong(
                constantRepository.findByCode(ConstantCode.GLOBAL_ROUTER_SERVICE_RETRY_COUNT.name()).get(0).getValue());
    }

    @Override
    public void logRequestResponseAsynchronously(String serviceType, String identifier, String mtn, String loanNumber,
            String imei, Object request, Object response, long retryCount, String errorCode) {
        RequestResponseLog logDetail = createRequestResponseLog(serviceType, identifier, mtn, loanNumber,
                imei, request, response, retryCount, errorCode);

        asyncRequestResponseLoggingExecutor.execute(() -> log(logDetail, identifier, request, response));
    }

    private RequestResponseLog createRequestResponseLog(String serviceType, String identifier, String mtn,
            String loanNumber, String imei, Object request, Object response, long retryCount, String errorCode) {
        RequestResponseLog logDetail = new RequestResponseLog();
        logDetail.setIdentifierId(identifier);
        logDetail.setMtn(mtn);
        logDetail.setLoanNumber(loanNumber);
        logDetail.setImei(imei);
        logDetail.setErrorCode(errorCode);
        logDetail.setServiceType(serviceType);
        if (request != null) {
            logDetail.setRequest(ObjectToByteArrayConverter.convert(request));
        }

        if (response != null) {
            logDetail.setResponse(ObjectToByteArrayConverter.convert(response));
        }

        if (StringUtils.isBlank(errorCode) || StringUtils.equalsIgnoreCase(Constants.SUCCESS_MSG, errorCode)) {
            logDetail.setStatus(Constants.SUCCESS_MSG);
        } else {
            if (retryCount == getMaxRetryCount()) {
                logDetail.setStatus(MAX_RETRIED_AND_FAILED);
            } else {
                logDetail.setStatus(ERROR_RESPONSE);
            }
        }
        return logDetail;
    }

    @Transactional(readOnly = false)
    public void log(RequestResponseLog detail, String identifier, Object request, Object response) {
        try {
            requestResponseLogRepository.save(detail);
        } catch (Exception e) {
            LOGGER.error("Saving RequestResponseLog Failed. Identifier : " + identifier);
            LOGGER.error("Request : " + (request != null ? request.toString() : "null"));
            LOGGER.error("Response : " + (response != null ? response.toString() : "null"));
        }
    }

}
