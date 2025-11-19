package com.poke_frontend.dto;

public class BaseResponse {
    public boolean success;
    public String message;
    public int errorCode;

    public BaseResponse() {}

    public BaseResponse(boolean success) {
        this.success = success;
    }

    public BaseResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public BaseResponse(boolean success, String message, int errorCode) {
        this.success = success;
        this.message = message;
        this.errorCode = errorCode;
    }
}