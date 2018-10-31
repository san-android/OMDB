package com.freshcodes.omdbapp.ws;

public interface WSListener {
    void onWSSuccess(String WSType, String response);
}