package com.h2test.sprngbt.service;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

public class DisplayErrorViewImpl implements DisplayErrorView {
    private HttpStatus status;
    //@JsonFormat(shape = JsonFormat.Shape.STRING)
    private String userId;
    private ErrorCode errorCode;
    private String errorDescription;

    public DisplayErrorViewImpl(HttpStatus status, String userId, ErrorCode errorCode, String errorDescription) {
        this.status = status;
        this.userId = userId;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public DisplayErrorViewImpl(HttpStatus status,
                                ErrorCode errorCode,
                                String errorDescription) {
        this.status = status;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }
    public DisplayErrorViewImpl(HttpStatus status, ErrorCode errorCode,
                                String errorDescription, Throwable ex) {
        this.status = status;
        this.errorCode = errorCode;
        this.errorDescription = ex.getLocalizedMessage();
    }

    @Override
    public HttpStatus getHttpStatus() {
        return status;
    }

    @Override

    public void setHttpStatus(HttpStatus status) {
        this.status = status;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    @Override
    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    @Override
    public String getErrorDescription() {
        return errorDescription;
    }
}
