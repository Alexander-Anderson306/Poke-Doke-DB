package com.poke_backend.dto.response;

public class LoginResponse extends BaseResponse {
    public int userId;
    public LoginResponse() {}
    public LoginResponse(int userId) {
        super(true, "Successfully logged in");
        this.userId = userId;
    }
}