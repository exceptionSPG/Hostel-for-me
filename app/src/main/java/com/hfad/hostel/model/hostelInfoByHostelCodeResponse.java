package com.hfad.hostel.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class hostelInfoByHostelCodeResponse {

    private boolean error;
    @SerializedName("user")
    private OwnerAllDetailsByHostelCode ownerAllDetailsByHostelCode;

    public hostelInfoByHostelCodeResponse(boolean error, OwnerAllDetailsByHostelCode ownerAllDetailsByHostelCode) {
        this.error = error;
        this.ownerAllDetailsByHostelCode = ownerAllDetailsByHostelCode;
    }

    public boolean isError() {
        return error;
    }

    public OwnerAllDetailsByHostelCode getOwnerAllDetailsByHostelCode() {
        return ownerAllDetailsByHostelCode;
    }
}
