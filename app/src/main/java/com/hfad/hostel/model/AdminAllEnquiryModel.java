package com.hfad.hostel.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdminAllEnquiryModel {
    int Total,Pending,Reviewed;
    boolean error;
    @SerializedName("enquiries")
    List<EnquiryModel> allEnquiry;

    public AdminAllEnquiryModel(int total, int pending, int reviewed, boolean error, List<EnquiryModel> allEnquiry) {
        Total = total;
        this.Pending = pending;
        this.Reviewed = reviewed;
        this.error = error;
        this.allEnquiry = allEnquiry;
    }

    public int getTotal() {
        return Total;
    }

    public int getPending() {
        return Pending;
    }

    public int getReviewed() {
        return Reviewed;
    }

    public boolean isError() {
        return error;
    }

    public List<EnquiryModel> getAllEnquiry() {
        return allEnquiry;
    }
}
