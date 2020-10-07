package com.hfad.hostel.model;

public class RecyclerModel {
    int image;
    String hostel_name,hostel_location;

    public RecyclerModel(int image, String hostel_name, String hostel_location) {
        this.image = image;
        this.hostel_name = hostel_name;
        this.hostel_location = hostel_location;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getHostel_name() {
        return hostel_name;
    }

    public void setHostel_name(String hostel_name) {
        this.hostel_name = hostel_name;
    }

    public String getHostel_location() {
        return hostel_location;
    }

    public void setHostel_location(String hostel_location) {
        this.hostel_location = hostel_location;
    }
}
