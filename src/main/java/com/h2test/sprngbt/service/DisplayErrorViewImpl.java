package com.h2test.sprngbt.service;

public class DisplayErrorViewImpl implements DisplayErrorView {
    private String userId;
    private String errorCode;
    private String errorDescription;

    public DisplayErrorViewImpl(String userId, String errorCode, String errorDescription) {
        this.userId = userId;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
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
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getErrorCode() {
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
