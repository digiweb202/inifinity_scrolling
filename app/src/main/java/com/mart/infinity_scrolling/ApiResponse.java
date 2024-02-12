package com.mart.infinity_scrolling;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private List<ProductTypeModel> data;

    public boolean isSuccess() {
        return success;
    }

    public List<ProductTypeModel> getData() {
        return data;
    }
}
