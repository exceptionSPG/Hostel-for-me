package com.hfad.hostel.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllUserResponse {
    boolean error;
    @SerializedName("users")
    List<User> userList;

    public AllUserResponse(boolean error, List<User> userList) {
        this.error = error;
        this.userList = userList;
    }

    public boolean isError() {
        return error;
    }

    public List<User> getUserList() {
        return userList;
    }
}
