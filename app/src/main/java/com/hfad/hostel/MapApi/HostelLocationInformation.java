package com.hfad.hostel.MapApi;

public class HostelLocationInformation {

    public String hcode,hname;
    public double lat,lon;

    public HostelLocationInformation() {
    }

    public HostelLocationInformation(String hcode, String hname, double lat, double lon) {
        this.hcode = hcode;
        this.hname = hname;
        this.lat = lat;
        this.lon = lon;
    }
}
