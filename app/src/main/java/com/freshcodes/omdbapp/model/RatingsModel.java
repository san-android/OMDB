package com.freshcodes.omdbapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RatingsModel implements Serializable {
    @SerializedName("Value")
    private String value;

    @SerializedName("Source")
    private String source;

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    @Override
    public String toString() {
        return
                "RatingsModel{" +
                        "value = '" + value + '\'' +
                        ",source = '" + source + '\'' +
                        "}";
    }
}