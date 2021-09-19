package com.hfad.hostel.model;

public class AdminDashboardResponse {

    // "error": false,
    //    "usersCount": 19,
    //    "ownersCount": 6,
    //    "enquiryTotal": 5,
    //    "enquiryPending": 3,
    //    "enquiryReviewed": 2,
    //    "allOwnerRequest": 7,
    //    "allOwnerPendingRequest": 0,
    //    "allOwnerApprovedRequest": 7,
    //    "userShownHostelCount": 6

    boolean error;
    int usersCount, ownersCount, enquiryCount,enquiryTotal, enquiryPending, enquiryReview, allOwnerRequest;
    int allOwnerPendingRequest, allOwnerApprovedRequest, userShownHostelCount;

    public AdminDashboardResponse(boolean error, int usersCount, int ownersCount, int enquiryCount, int enquiryTotal, int enquiryPending, int enquiryReview, int allOwnerRequest, int allOwnerPendingRequest, int allOwnerApprovedRequest, int userShownHostelCount) {
        this.error = error;
        this.usersCount = usersCount;
        this.ownersCount = ownersCount;
        this.enquiryCount = enquiryCount;
        this.enquiryTotal = enquiryTotal;
        this.enquiryPending = enquiryPending;
        this.enquiryReview = enquiryReview;
        this.allOwnerRequest = allOwnerRequest;
        this.allOwnerPendingRequest = allOwnerPendingRequest;
        this.allOwnerApprovedRequest = allOwnerApprovedRequest;
        this.userShownHostelCount = userShownHostelCount;
    }

    public boolean isError() {
        return error;
    }

    public int getUsersCount() {
        return usersCount;
    }

    public int getOwnersCount() {
        return ownersCount;
    }

    public int getEnquiryCount() {
        return enquiryCount;
    }

    public int getEnquiryTotal() {
        return enquiryTotal;
    }

    public int getEnquiryPending() {
        return enquiryPending;
    }

    public int getEnquiryReview() {
        return enquiryReview;
    }

    public int getAllOwnerRequest() {
        return allOwnerRequest;
    }

    public int getAllOwnerPendingRequest() {
        return allOwnerPendingRequest;
    }

    public int getAllOwnerApprovedRequest() {
        return allOwnerApprovedRequest;
    }

    public int getUserShownHostelCount() {
        return userShownHostelCount;
    }
}
