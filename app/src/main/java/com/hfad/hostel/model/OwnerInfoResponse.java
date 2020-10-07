package com.hfad.hostel.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OwnerInfoResponse {
    boolean error;
    @SerializedName("users")
    List<Owner> owner;

    public OwnerInfoResponse(boolean error, List<Owner> owner) {
        this.error = error;
        this.owner = owner;
    }

    public boolean isError() {
        return error;
    }

    public List<Owner> getOwner() {
        return owner;
    }
}
