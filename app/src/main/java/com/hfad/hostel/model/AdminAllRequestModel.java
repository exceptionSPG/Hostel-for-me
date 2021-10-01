package com.hfad.hostel.model;

import java.util.List;

public class AdminAllRequestModel {
    // "Total": 7,
    //    "Pending": 0,
    //    "Approved": 7,
    //    "error": false,
    //    "requests
    int Total,Pending,Approved;
    boolean error;
    List<RequestModel> requests;

    public AdminAllRequestModel(int total, int pending, int approved, boolean error, List<RequestModel> requests) {
        Total = total;
        Pending = pending;
        Approved = approved;
        this.error = error;
        this.requests = requests;
    }

    public int getTotal() {
        return Total;
    }

    public int getPending() {
        return Pending;
    }

    public int getApproved() {
        return Approved;
    }

    public boolean isError() {
        return error;
    }

    public List<RequestModel> getRequests() {
        return requests;
    }
}
