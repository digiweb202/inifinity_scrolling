package com.mart.infinity_scrolling;

import com.google.gson.annotations.SerializedName;

public class SingleProductModel {
    @SerializedName("Item_Name")
    private String Itemname;
    @SerializedName("Main_Image_URL")
    private String Mainimageurl;

    public String getMainimageurl() {
        return Mainimageurl;
    }

    public void setMainimageurl(String mainimageurl) {
        Mainimageurl = mainimageurl;
    }

    public String getItemname() {
        return Itemname;
    }

    public void setItemname(String itemname) {
        Itemname = itemname;
    }
//    @SerializedName("your_field_name2")
//    private String field2;

    // Add other fields as per your database schema

    // Create getters and setters for the fields
}
