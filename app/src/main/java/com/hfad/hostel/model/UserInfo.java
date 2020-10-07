package com.hfad.hostel.model;

public class UserInfo {
    private int user_phone_number, user_guardian_number;
    private String user_address, user_dob, user_guardian_name, user_about_you, user_gender, user_level, user_stream, user_education;

    public UserInfo(int user_phone_number, int user_guardian_number, String user_address, String user_dob, String user_guardian_name, String user_about_you, String user_gender, String user_level, String user_stream, String user_education) {
        this.user_phone_number = user_phone_number;
        this.user_guardian_number = user_guardian_number;
        this.user_address = user_address;
        this.user_dob = user_dob;
        this.user_guardian_name = user_guardian_name;
        this.user_about_you = user_about_you;
        this.user_gender = user_gender;
        this.user_level = user_level;
        this.user_stream = user_stream;
        this.user_education = user_education;
    }

    public int getUser_phone_number() {
        return user_phone_number;
    }

    public int getUser_guardian_number() {
        return user_guardian_number;
    }

    public String getUser_address() {
        return user_address;
    }

    public String getUser_dob() {
        return user_dob;
    }

    public String getUser_guardian_name() {
        return user_guardian_name;
    }

    public String getUser_about_you() {
        return user_about_you;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public String getUser_level() {
        return user_level;
    }

    public String getUser_stream() {
        return user_stream;
    }

    public String getUser_education() {
        return user_education;
    }
}
