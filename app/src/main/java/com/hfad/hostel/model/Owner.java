package com.hfad.hostel.model;

public class Owner {

    private int sid;
    private String hostel_owner_name;
    private String hostel_name;
    private String hostel_location;
    private String hostel_type;
    private String contact_number;
    private String hostel_email;
    private String hostel_code;
    private String login_pwd;
    private String hostel_registered_date;
    private String updation_date;

    public Owner(int sid, String hostel_owner_name, String hostel_name, String hostel_location, String hostel_type, String contact_number, String hostel_email, String hostel_code, String login_pwd, String hostel_registered_date, String updation_date) {
        this.sid = sid;
        this.hostel_owner_name = hostel_owner_name;
        this.hostel_name = hostel_name;
        this.hostel_location = hostel_location;
        this.hostel_type = hostel_type;
        this.contact_number = contact_number;
        this.hostel_email = hostel_email;
        this.hostel_code = hostel_code;
        this.login_pwd = login_pwd;
        this.hostel_registered_date = hostel_registered_date;
        this.updation_date = updation_date;
    }

    public int getSid() {
        return sid;
    }

    public String getHostel_owner_name() {
        return hostel_owner_name;
    }

    public String getHostel_name() {
        return hostel_name;
    }

    public String getHostel_location() {
        return hostel_location;
    }

    public String getHostel_type() {
        return hostel_type;
    }

    public String getContact_number() {
        return contact_number;
    }

    public String getHostel_email() {
        return hostel_email;
    }

    public String getHostel_code() {
        return hostel_code;
    }

    public String getLogin_pwd() {
        return login_pwd;
    }

    public String getHostel_registered_date() {
        return hostel_registered_date;
    }

    public String getUpdation_date() {
        return updation_date;
    }
}
