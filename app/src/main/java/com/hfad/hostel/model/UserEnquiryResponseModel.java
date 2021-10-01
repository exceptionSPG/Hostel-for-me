package com.hfad.hostel.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserEnquiryResponseModel {
    int Total;
    boolean error;
    @SerializedName("enquiries")
    List<EnquiryModel> allEnquiry;

    public UserEnquiryResponseModel(int total, boolean error, List<EnquiryModel> allEnquiry) {
        Total = total;
        this.error = error;
        this.allEnquiry = allEnquiry;
    }

    public int getTotal() {
        return Total;
    }

    public boolean isError() {
        return error;
    }

    public List<EnquiryModel> getAllEnquiry() {
        return allEnquiry;
    }
}
