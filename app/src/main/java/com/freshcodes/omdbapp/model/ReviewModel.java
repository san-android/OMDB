package com.freshcodes.omdbapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReviewModel implements Serializable {
    @SerializedName("imdbID")
    private String imdbID;

    @SerializedName("review")
    private String review;

    @SerializedName("rating")
    private String rating;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private String id;

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReview() {
        return review;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return
                "ReviewModel{" +
                        "imdbID = '" + imdbID + '\'' +
                        ",review = '" + review + '\'' +
                        ",rating = '" + rating + '\'' +
                        ",name = '" + name + '\'' +
                        ",id = '" + id + '\'' +
                        "}";
    }
}