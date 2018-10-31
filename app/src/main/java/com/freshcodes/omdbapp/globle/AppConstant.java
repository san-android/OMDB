package com.freshcodes.omdbapp.globle;

public class AppConstant {
    // regular expressions

    public static final String EMAIL_EXPRESS = "^[A-Za-z0-9]+([\\.\\-_]{1}[A-Za-z0-9]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+){0,1}(\\.[A-Za-z]{2,4})$";
    public static final String MOBILE_EXPRESS = "^[+]?[0-9]{10,13}$";
    public static final String IND_TEN_DIGIT_MOBILE_EXPRESS = "^[6789]\\d{9}$";
    public static final String NAME_EXPRESS = "^[A-Za-z]{3,}";
    public static final String IND_ZIP_EXPRESS = "^[1-9][0-9]{5}$";
    public static final String NUMBER_EXPRESS = "\\d+(?:\\.\\d+)?";

    // app constant

    public static final String PREFS_NAME = "omdb";
    public static final String DATABASE_NAME = "omdb.db";

    public static final String success = "success";
    public static final String fail = "fail";

    public static final long PULL_TO_REFRESH = 0;

    public static final String PLEASE_WAIT = "Please wait";
    public static final String NO_INTERNET_CONNECTION = "No internet connection";

    public static final String TIME_OUT = "Connection Timeout.Please try again.";

    public static final String SOMETHING_WRONG_TRY_AGAIN = "Something may went wrong, Please try again";
    public static final int SPLASH_TIME_OUT = 3000;

    public static final String GOOGLE_MAP_API_KEY = "AIzaSyD3Knhw37__LWW-9T1XnzhtwwwtfVhFUgY";

    public static final int LOAD_LIMIT = 30;

    public static final String TEST_DEVICE_ID = "CD0BEAA5552212264F6F2EB07B66FBCC";

    public static final String PREF_DOWNLOADING_ENABLE = "pref_downloading_enable";

    public static final String PREF_USER_ID = "pref_user_id";
    public static final String PREF_USER_NAME = "pref_user_name";
    public static final String PREF_USER_EMAIL = "pref_user_email";
}