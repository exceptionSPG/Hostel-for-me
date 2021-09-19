package com.hfad.hostel.model;

import java.util.List;

public class AdminAllEnquiryModel {
    int Total,pending,reviewed;
    boolean error;
    List<EnquiryModel> allEnquiry;

    public AdminAllEnquiryModel(int total, int pending, int reviewed, boolean error, List<EnquiryModel> allEnquiry) {
        Total = total;
        this.pending = pending;
        this.reviewed = reviewed;
        this.error = error;
        this.allEnquiry = allEnquiry;
    }

    public int getTotal() {
        return Total;
    }

    public int getPending() {
        return pending;
    }

    public int getReviewed() {
        return reviewed;
    }

    public boolean isError() {
        return error;
    }

    public List<EnquiryModel> getAllEnquiry() {
        return allEnquiry;
    }
}
