package com.hfad.hostel.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("error")
    private boolean err;

    @SerializedName("message")
    private String msg;

   private User user;

    @SerializedName("keys")
    private String key;

    public LoginResponse(boolean err, String msg, User user, String key) {
        this.err = err;
        this.msg = msg;
        this.user = user;
        this.key = key;
    }

    public boolean isErr() {
        return err;
    }

    public String getMsg() {
        return msg;
    }

    public User getUser() {
        return user;
    }

    public String getKey() {
        return key;
    }
}
