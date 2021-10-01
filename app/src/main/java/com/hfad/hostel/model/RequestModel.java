package com.hfad.hostel.model;

public class RequestModel {


    // "hrid": 9,
    //            "hostel_name": "Spikes Boys Hostel",
    //            "hostel_location": "Butwal",
    //            "hostel_type": "Boys",
    //            "hostel_owner_name": "Biwas Kunwar",
    //            "contact_number": 9867676320,
    //            "owner_name": "spikeshostel@gmail.com",
    //            "request_date": "2020-10-16 09:50:14",
    //            "status": "Approved"

    int hrid;
    String hostel_name, hostel_location,hostel_type,hostel_owner_name,contact_number,hostel_email,request_date,status;
    // String request_approved_date;


    public RequestModel(int hrid, String hostel_name, String hostel_location, String hostel_type, String hostel_owner_name, String contact_number, String hostel_email, String request_date, String status) {
        this.hrid = hrid;
        this.hostel_name = hostel_name;
        this.hostel_location = hostel_location;
        this.hostel_type = hostel_type;
        this.hostel_owner_name = hostel_owner_name;
        this.contact_number = contact_number;
        this.hostel_email = hostel_email;
        this.request_date = request_date;
        this.status = status;
    }

    public int getHrid() {
        return hrid;
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

    public String getHostel_owner_name() {
        return hostel_owner_name;
    }

    public String getContact_number() {
        return contact_number;
    }

    public String getHostel_email() {
        return hostel_email;
    }

    public String getRequest_date() {
        return request_date;
    }

    public String getStatus() {
        return status;
    }
}
