package com.freshcodes.omdbapp.ws;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.URLUtil;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.freshcodes.omdbapp.MyApplication;
import com.freshcodes.omdbapp.R;
import com.freshcodes.omdbapp.globle.AppConstant;
import com.freshcodes.omdbapp.globle.AppMethod;
import com.freshcodes.omdbapp.view.AppProgressDialog;

import org.json.JSONObject;

import java.util.Map;

public class HttpRequest {
    Context context;
    int method;
    JSONObject reqBody;
    String wsTag;
    WSListener wsListener;

    Map<String, String> headers;

    AppProgressDialog appProgressDialog;
    boolean showProgress = true;

    public static int GET = 1;
    public static int POST = 2;

    public HttpRequest(Context context, int method, String wsTag, WSListener wsListener) {
        this.context = context;
        this.method = method;
        this.wsTag = wsTag;
        this.wsListener = wsListener;
    }

    public HttpRequest(Context context, int method, JSONObject reqBody, String wsTag, WSListener wsListener) {
        this.context = context;
        this.method = method;
        this.reqBody = reqBody;
        this.wsTag = wsTag;
        this.wsListener = wsListener;
    }

    public void execute(String url) {
        try {
            if (URLUtil.isValidUrl(url)) {
                if (showProgress) {
                    appProgressDialog = new AppProgressDialog(context);
                    appProgressDialog.showDialog();
                }

                int reqMethod = Request.Method.GET;
                if (method == GET) {
                    reqMethod = Request.Method.GET;
                } else if (method == POST) {
                    reqMethod = Request.Method.POST;
                }

                StringRequest jsonReq = new StringRequest(reqMethod, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (showProgress && appProgressDialog != null)
                            appProgressDialog.dismissDialog();

                        try {
                            if (context != null && AppMethod.isJSONValid(response)) {
                                wsListener.onWSSuccess(wsTag, response);
                            } else {
                                AppMethod.showAlertWithOk(context, context.getString(R.string.app_name), "Invalid Response : " + response);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (context != null)
                                AppMethod.showAlertWithOk(context, context.getString(R.string.app_name), AppConstant.SOMETHING_WRONG_TRY_AGAIN);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (showProgress && appProgressDialog != null)
                            appProgressDialog.dismissDialog();

                        try {
                            String errorMessage = "";
                            if (error instanceof NetworkError) {
                                errorMessage = AppConstant.NO_INTERNET_CONNECTION;
                            } else if (error instanceof ServerError) {
                                errorMessage = AppConstant.SOMETHING_WRONG_TRY_AGAIN;
                            } else if (error instanceof AuthFailureError) {
                                errorMessage = "Authentication Error!";
                            } else if (error instanceof ParseError) {
                                errorMessage = AppConstant.SOMETHING_WRONG_TRY_AGAIN;
                            } else if (error instanceof NoConnectionError) {
                                errorMessage = "Connection can not be established, Please try again!";
                            } else if (error instanceof TimeoutError) {
                                errorMessage = AppConstant.TIME_OUT;
                            } else {
                                errorMessage = TextUtils.isEmpty(error.getMessage()) ? "Unknown Error" : error.getMessage();
                            }

                            if (context != null)
                                AppMethod.showAlertWithOk(context, context.getString(R.string.app_name), errorMessage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }) {
                /*@Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new Gson().fromJson(reqBody, new TypeToken<HashMap<String, String>>() {
                    }.getType());
                    return params;
                }*/

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        if (reqBody != null && AppMethod.isJSONValid(reqBody.toString())) {
                            return reqBody.toString().getBytes();
                        }
                        return super.getBody();
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        if (headers != null && headers.size() > 0) {
                            return headers;
                        }
                        return super.getHeaders();
                    }
                };

                MyApplication.getInstance().addToRequestQueue(jsonReq, wsTag);
            } else {
                Log.e("WS Error", wsTag + " : Invalid URL");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public boolean isShowProgress() {
        return showProgress;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
    }
}