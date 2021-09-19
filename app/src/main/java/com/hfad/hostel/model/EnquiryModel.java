package com.hfad.hostel.model;

import java.util.Date;

public class EnquiryModel {
    int eid, userid, ownerid;
    String user_name, user_email,user_phone,owner_name,hostel_name,hostel_address,enquiry_message;
    String enquiry_date,enquiry_status,enquiry_status_update_date;

    public EnquiryModel(int eid, int userid, int ownerid, String user_name, String user_email, String user_phone, String owner_name, String hostel_name, String hostel_address, String enquiry_message, String enquiry_date, String enquiry_status, String enquiry_status_update_date) {
        this.eid = eid;
        this.userid = userid;
        this.ownerid = ownerid;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_phone = user_phone;
        this.owner_name = owner_name;
        this.hostel_name = hostel_name;
        this.hostel_address = hostel_address;
        this.enquiry_message = enquiry_message;
        this.enquiry_date = enquiry_date;
        this.enquiry_status = enquiry_status;
        this.enquiry_status_update_date = enquiry_status_update_date;
    }

    public int getEid() {
        return eid;
    }

    public int getUserid() {
        return userid;
    }

    public int getOwnerid() {
        return ownerid;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public String getHostel_name() {
        return hostel_name;
    }

    public String getHostel_address() {
        return hostel_address;
    }

    public String getEnquiry_message() {
        return enquiry_message;
    }

    public String getEnquiry_date() {
        return enquiry_date;
    }

    public String getEnquiry_status() {
        return enquiry_status;
    }

    public String getEnquiry_status_update_date() {
        return enquiry_status_update_date;
    }
}
