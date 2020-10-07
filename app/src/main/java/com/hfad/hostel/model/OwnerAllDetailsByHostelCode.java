package com.hfad.hostel.model;

public class OwnerAllDetailsByHostelCode {
    private int hdid;
    private int sid;
    private String hostel_code;
    private String total_room_number;
    private String facility;
    private String pricing;
    private String hostel_owner_name;
    private String hostel_name;
    private String hostel_location;
    private String hostel_type;
    private String contact_number;
    private String hostel_email;
    private String login_pwd;
    private String hostel_registered_date;
    private String message;

    public OwnerAllDetailsByHostelCode(int hdid, int sid, String hostel_code, String total_room_number, String facility, String pricing, String hostel_owner_name, String hostel_name, String hostel_location, String hostel_type, String contact_number, String hostel_email, String login_pwd, String hostel_registered_date, String message) {
        this.hdid = hdid;
        this.sid = sid;
        this.hostel_code = hostel_code;
        this.total_room_number = total_room_number;
        this.facility = facility;
        this.pricing = pricing;
        this.hostel_owner_name = hostel_owner_name;
        this.hostel_name = hostel_name;
        this.hostel_location = hostel_location;
        this.hostel_type = hostel_type;
        this.contact_number = contact_number;
        this.hostel_email = hostel_email;
        this.login_pwd = login_pwd;
        this.hostel_registered_date = hostel_registered_date;
        this.message = message;
    }

    public int getHdid() {
        return hdid;
    }

    public int getSid() {
        return sid;
    }

    public String getHostel_code() {
        return hostel_code;
    }

    public String getTotal_room_number() {
        return total_room_number;
    }

    public String getFacility() {
        return facility;
    }

    public String getPricing() {
        return pricing;
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

    public String getLogin_pwd() {
        return login_pwd;
    }

    public String getHostel_registered_date() {
        return hostel_registered_date;
    }

    public String getMessage() {
        return message;
    }
}
