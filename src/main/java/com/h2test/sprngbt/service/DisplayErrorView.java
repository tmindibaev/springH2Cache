package com.h2test.sprngbt.service;

import org.json.JSONString;
import org.springframework.http.HttpStatus;
import org.json.JSONObject;

public interface DisplayErrorView {
    void setHttpStatus(HttpStatus status);

    HttpStatus getHttpStatus();

    void setUserId(String userId);

    String getUserId();

    void setErrorCode(ErrorCode userName);

    ErrorCode getErrorCode();

    void setErrorDescription(String userPassport);

    String getErrorDescription();
}
