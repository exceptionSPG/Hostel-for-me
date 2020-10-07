package com.hfad.hostel.model;

public class UserInfoUpdateResponse {

    private boolean error;
    private String message;
    private userInfoResponse infoResponse;

    public UserInfoUpdateResponse(boolean error, String message, userInfoResponse infoResponse) {
        this.error = error;
        this.message = message;
        this.infoResponse = infoResponse;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public userInfoResponse getInfoResponse() {
        return infoResponse;
    }
}
