package com.freshcodes.omdbapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class GetReviewResponseModel implements Serializable {
    @SerializedName("data")
    private ArrayList<ReviewModel> data;

    @SerializedName("avgrate")
    private String avgrate;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private int status;

    public ArrayList<ReviewModel> getData() {
        return data;
    }

    public void setData(ArrayList<ReviewModel> data) {
        this.data = data;
    }

    public String getAvgrate() {
        return avgrate;
    }

    public void setAvgrate(String avgrate) {
        this.avgrate = avgrate;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return
                "GetReviewResponseModel{" +
                        "data = '" + data + '\'' +
                        "avgrate = '" + avgrate + '\'' +
                        ",message = '" + message + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}