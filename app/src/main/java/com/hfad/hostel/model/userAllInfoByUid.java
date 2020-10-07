package com.hfad.hostel.model;

import com.google.gson.annotations.SerializedName;

public class userAllInfoByUid {
    private int uiid;
    @SerializedName("uid")
    private int user_id;
    private String email;
    private String username;
    private String reg_date;
    private String Registration_updation_date;
    @SerializedName("user_phone_number")
    private String user_phone_number;
    private String user_address;
    private String user_DOB;
    private String user_gender;
    private String user_guardian_name;
    @SerializedName("user_guardian_contact_number")
    private String user_guardian_contact_number;
    private String education;
    private String about_you;
    private String Info_updation_date;
    private String message;

    public userAllInfoByUid(int uiid, int user_id, String email, String username, String reg_date, String registration_updation_date, String user_phone_number, String user_address, String user_DOB, String user_gender, String user_guardian_name, String user_guardian_contact_number, String education, String about_you, String info_updation_date, String message) {
        this.uiid = uiid;
        this.user_id = user_id;
        this.email = email;
        this.username = username;
        this.reg_date = reg_date;
        this.Registration_updation_date = registration_updation_date;
        this.user_phone_number = user_phone_number;
        this.user_address = user_address;
        this.user_DOB = user_DOB;
        this.user_gender = user_gender;
        this.user_guardian_name = user_guardian_name;
        this.user_guardian_contact_number = user_guardian_contact_number;
        this.education = education;
        this.about_you = about_you;
        this.Info_updation_date = info_updation_date;
        this.message = message;
    }

    public int getUiid() {
        return uiid;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getEmail() {
        return email;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public String getUsername() {
        return username;
    }

    public String getReg_date() {
        return reg_date;
    }

    public String getRegistration_updation_date() {
        return Registration_updation_date;
    }

    public String getUser_phone_number() {
        return user_phone_number;
    }

    public String getUser_address() {
        return user_address;
    }

    public String getUser_DOB() {
        return user_DOB;
    }

    public String getUser_guardian_name() {
        return user_guardian_name;
    }

    public String getUser_guardian_contact_number() {
        return user_guardian_contact_number;
    }

    public String getEducation() {
        return education;
    }

    public String getAbout_you() {
        return about_you;
    }

    public String getInfo_updation_date() {
        return Info_updation_date;
    }

    public String getMessage() {
        return message;
    }
}
