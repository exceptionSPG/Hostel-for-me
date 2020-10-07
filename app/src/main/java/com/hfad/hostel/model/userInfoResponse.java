package com.hfad.hostel.model;

import com.google.gson.annotations.SerializedName;

public class userInfoResponse {
    private boolean error;
    @SerializedName("users")
    userAllInfoByUid userAllInfo;

    public userInfoResponse(boolean error, userAllInfoByUid userAllInfo) {
        this.error = error;
        this.userAllInfo = userAllInfo;
    }

    public boolean isError() {
        return error;
    }

    public userAllInfoByUid getUserAllInfo() {
        return userAllInfo;
    }
}
