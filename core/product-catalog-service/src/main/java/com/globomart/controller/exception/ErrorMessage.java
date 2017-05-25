package com.globomart.controller.exception;

public final class ErrorMessage {

    private String reason;

    public ErrorMessage(String reason) {
        this.reason = reason;
    }

    public ErrorMessage(ApiException ex) {
        this.reason = ex.getMessage();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
