package com.hfad.hostel.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("uid")  //yedi database table ko column_name ra ehako variable name different rakhne vaye par @serialized("database ko nam) ani naya variable define garna parxa
    private int user_id;

    private String email;
    private String username;
    private String reg_date;
    private String updation_date;

    public User(int user_id, String email, String username, String reg_date, String updation_date) {
        this.user_id = user_id;
        this.email = email;
        this.username = username;
        this.reg_date = reg_date;
        this.updation_date = updation_date;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getReg_date() {
        return reg_date;
    }

    public String getUpdation_date() {
        return updation_date;
    }
}
