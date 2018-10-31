package com.freshcodes.omdbapp.ws;

public class WSConfig {
    private static final String API_KEY = "thewdb";
    public static final String URL_DATA = "http://www.omdbapi.com/?apikey=" + API_KEY;
    public static final String WS_DATA = "ws_data";

    public static final String URL_POST_REVIEW = "http://smartonlineexam.freshcodes.in/webservice/addreview.php";
    public static final String WS_POST_REVIEW = "ws_post_review";

    public static final String URL_GET_REVIEW = "http://smartonlineexam.freshcodes.in/webservice/getreviews.php";
    public static final String WS_GET_REVIEW = "ws_get_review";
}