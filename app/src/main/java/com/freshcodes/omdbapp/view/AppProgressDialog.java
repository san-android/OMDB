package com.freshcodes.omdbapp.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;

import com.freshcodes.omdbapp.R;

public class AppProgressDialog {
    Context context;
    Dialog d;

    public AppProgressDialog(Context context) {
        this.context = context;
        d = new Dialog(context, android.R.style.Theme_Translucent);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.view_app_progress);
        d.setCancelable(false);
    }

    public void showDialog() {
        if (d != null && !d.isShowing()) {
            d.show();
        }
    }

    public void dismissDialog() {
        if (d != null && d.isShowing()) {
            d.dismiss();
        }
    }

    public void onCancel(DialogInterface.OnCancelListener onCancelListener) {
        d.setOnCancelListener(onCancelListener);
    }
}