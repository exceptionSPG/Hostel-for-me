package com.hfad.hostel.model;

public class OwnerLoginResponse {
    private boolean error;
    private String message;
    private Owner owner;
    private String keys;

    public OwnerLoginResponse(boolean error, String message, Owner owner, String keys) {
        this.error = error;
        this.message = message;
        this.owner = owner;
        this.keys = keys;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Owner getOwner() {
        return owner;
    }

    public String getKeys() {
        return keys;
    }
}
