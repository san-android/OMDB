package com.freshcodes.omdbapp.globle;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.freshcodes.omdbapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class AppMethod {
    public static boolean setStringPreference(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(AppConstant.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String getStringPreference(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(AppConstant.PREFS_NAME, Context.MODE_PRIVATE);
        String value = settings.getString(key, "");
        return value;
    }

    public static boolean setIntegerPreference(Context context, String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(AppConstant.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public static int getIntegerPreference(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(AppConstant.PREFS_NAME, Context.MODE_PRIVATE);
        int value = settings.getInt(key, -1);
        return value;
    }

    public static boolean setBooleanPreference(Context context, String key, Boolean value) {
        SharedPreferences settings = context.getSharedPreferences(AppConstant.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public static Boolean getBooleanPreference(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(AppConstant.PREFS_NAME, Context.MODE_PRIVATE);
        Boolean value = settings.getBoolean(key, false);
        return value;
    }

    public static boolean setLongPreference(Context context, String key, long value) {
        SharedPreferences settings = context.getSharedPreferences(AppConstant.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public static long getLongPreference(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(AppConstant.PREFS_NAME, Context.MODE_PRIVATE);
        long value = settings.getLong(key, -1);
        return value;
    }

    public static boolean clearPreference(Context context) {
        SharedPreferences settings = context.getSharedPreferences(AppConstant.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.apply();
        return editor.commit();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String getString(String text, String defaultText) {
        if (!TextUtils.isEmpty(text)) {
            return text;
        }
        return defaultText;
    }

    public static void openFile(Context context, String filepath) {
        File openFile = new File(filepath);

        Uri uri = Uri.fromFile(openFile);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (openFile.toString().contains(".doc") || openFile.toString().contains(".docx")) {
            // Word document
            intent.setDataAndType(uri, "application/msword");
        } else if (openFile.toString().contains(".pdf")) {
            // PDF file
            intent.setDataAndType(uri, "application/pdf");
        } else if (openFile.toString().contains(".ppt") || openFile.toString().contains(".pptx")) {
            // Powerpoint file
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        } else if (openFile.toString().contains(".xls") || openFile.toString().contains(".xlsx")) {
            // Excel file
            intent.setDataAndType(uri, "application/vnd.ms-excel");
        } else if (openFile.toString().contains(".zip") || openFile.toString().contains(".rar")) {
            // WAV audio file
            intent.setDataAndType(uri, "application/zip");
        } else if (openFile.toString().contains(".rtf")) {
            // RTF file
            intent.setDataAndType(uri, "application/rtf");
        } else if (openFile.toString().contains(".wav") || openFile.toString().contains(".mp3")) {
            // WAV audio file
            intent.setDataAndType(uri, "audio/x-wav");
        } else if (openFile.toString().contains(".gif")) {
            // GIF file
            intent.setDataAndType(uri, "image/gif");
        } else if (openFile.toString().contains(".jpg") || openFile.toString().contains(".jpeg")
                || openFile.toString().contains(".png")) {
            // JPG file
            intent.setDataAndType(uri, "image/jpeg");
        } else if (openFile.toString().contains(".txt")) {
            // Text file
            intent.setDataAndType(uri, "text/plain");
        } else if (openFile.toString().contains(".3gp") || openFile.toString().contains(".mpg")
                || openFile.toString().contains(".mpeg") || openFile.toString().contains(".mpe")
                || openFile.toString().contains(".mp4") || openFile.toString().contains(".avi")) {
            // Video files
            intent.setDataAndType(uri, "video/*");
        } else {
            // if you want you can also define the intent type for any other
            // file

            // additionally use else clause below, to manage other unknown
            // extensions
            // in this case, Android will show all applications installed on the
            // device
            // so you can choose which application to use
            intent.setDataAndType(uri, "*/*");
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void openFileFromURL(Activity activity, String url) {
        Uri uri = Uri.parse(url);
        String openFile = url;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (openFile.toString().contains(".doc") || openFile.toString().contains(".docx")) {
            // Word document
            intent.setDataAndType(uri, "application/msword");
        } else if (openFile.toString().contains(".pdf")) {
            // PDF file
            intent.setDataAndType(uri, "application/pdf");
        } else if (openFile.toString().contains(".ppt") || openFile.toString().contains(".pptx")) {
            // Powerpoint file
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        } else if (openFile.toString().contains(".xls") || openFile.toString().contains(".xlsx")) {
            // Excel file
            intent.setDataAndType(uri, "application/vnd.ms-excel");
        } else if (openFile.toString().contains(".zip") || openFile.toString().contains(".rar")) {
            // WAV audio file
            intent.setDataAndType(uri, "application/zip");
        } else if (openFile.toString().contains(".rtf")) {
            // RTF file
            intent.setDataAndType(uri, "application/rtf");
        } else if (openFile.toString().contains(".wav") || openFile.toString().contains(".mp3")
                || openFile.toString().contains(".m4a")) {
            // WAV audio file
            intent.setDataAndType(uri, "audio/x-wav");
        } else if (openFile.toString().contains(".gif")) {
            // GIF file
            intent.setDataAndType(uri, "image/gif");
        } else if (openFile.toString().contains(".jpg") || openFile.toString().contains(".jpeg")
                || openFile.toString().contains(".png")) {
            // JPG file
            intent.setDataAndType(uri, "image/jpeg");
        } else if (openFile.toString().contains(".txt")) {
            // Text file
            intent.setDataAndType(uri, "text/plain");
        } else if (openFile.toString().contains(".3gp") || openFile.toString().contains(".mpg")
                || openFile.toString().contains(".mpeg") || openFile.toString().contains(".mpe")
                || openFile.toString().contains(".mp4") || openFile.toString().contains(".avi")) {
            // Video files
            intent.setDataAndType(uri, "video/*");
        } else {
            intent.setDataAndType(uri, "*/*");
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public static void playAudio(String path, String fileName, Boolean looping) {
        // set up VitamioMediaPlayer
        MediaPlayer mp = new MediaPlayer();
        mp.setLooping(looping);

        try {
            mp.setDataSource(path + File.separator + fileName);
            mp.prepare();
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showInfoDialog(Context context, String title, String message, boolean cancelable, String
            positiveBtnTitle, String nagativeBtnTitle, DialogInterface.OnClickListener positiveButtonListener,
                                      DialogInterface.OnClickListener nagativeButtonListener) {
        try {
            if (context instanceof Activity) {
                if (!((Activity) context).isFinishing()) {
                    new AlertDialog.Builder(context)
                            .setTitle(title)
                            .setMessage(message)
                            .setCancelable(cancelable)
                            .setPositiveButton(positiveBtnTitle, positiveButtonListener)
                            .setNegativeButton(nagativeBtnTitle, nagativeButtonListener)
                            .show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showAlertWithOk(Context activity, String title, String msg) {
        try {
            if (activity instanceof Activity) {
                if (!((Activity) activity).isFinishing()) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                    alert.setTitle(title);
                    alert.setMessage(msg);
                    alert.setPositiveButton("OK", null);
                    alert.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showPositiveActionDialog(Context context, String title, String message, boolean cancelable, DialogInterface.OnClickListener positiveButtonListener) {
        try {
            if (context instanceof Activity) {
                if (!((Activity) context).isFinishing()) {
                    new AlertDialog.Builder(context)
                            .setTitle(title)
                            .setMessage(message)
                            .setCancelable(cancelable)
                            .setPositiveButton("Ok", positiveButtonListener)
                            .show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String imageToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteImg = stream.toByteArray();
        String imgString = Base64.encodeToString(byteImg, Base64.DEFAULT);
        return imgString;
    }

    private static Bitmap takeScreenShot(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay()
                .getHeight();

        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        Log.e("Screenshot", "taken successfully");
        return b;
    }

    public static void setDrawableColor(View view, String colorCode) {
        Drawable background = view.getBackground();
        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable) background).getPaint().setColor(Color.parseColor(colorCode));
        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable) background).setColor(Color.parseColor(colorCode));
        } else if (background instanceof ColorDrawable) {
            ((ColorDrawable) background).setColor(Color.parseColor(colorCode));
        }
    }

    public static int getRandomNo(int min, int max) {
        Random r = new Random();
        int randomNo = r.nextInt((max - min) + 1) + min;
        return randomNo;
    }

    public static String parseDateFormat(String time, String inputPattern, String outputPattern) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = "";

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String readRawFileAsString(Context context, int rawFile)
            throws IOException {
        InputStream inputStream = context.getResources().openRawResource(
                rawFile);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));
        StringBuffer result = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        reader.close();
        return result.toString();
    }

    public static boolean isJSONValid(String json) {
        try {
            new JSONObject(json);
        } catch (JSONException ex) {
            try {
                new JSONArray(json);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    public static String getUDID(Context context) {
        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return android_id;
    }

    public static String getDeviceType(Context context) {
        if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE) {
            return "Tablet";
        } else {
            return "Phone";
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if (listItem instanceof ViewGroup) {
                listItem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static void setExpListViewHeight(ExpandableListView listView, int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    public static String extractDigits(String src) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < src.length(); i++) {
            char c = src.charAt(i);
            if (Character.isDigit(c)) {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    public static void setForceShowIconPopupMenu(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static JSONArray removeJsonObjectAtJsonArrayIndex(JSONArray source, int index) throws
            JSONException {
        if (index < 0 || index > source.length() - 1) {
            throw new IndexOutOfBoundsException();
        }

        final JSONArray copy = new JSONArray();
        for (int i = 0, count = source.length(); i < count; i++) {
            if (i != index) copy.put(source.get(i));
        }
        return copy;
    }

    public static String addDayToDate(String date, String dateFormat, int days) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, days);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        SimpleDateFormat sdf1 = new SimpleDateFormat(dateFormat);
        return sdf1.format(c.getTime());
    }

    public static String getCurrentDate(String dateFormat) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public static int getDayDiff(String strDate1, String strDate2, String dateFormat) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(dateFormat);
            Date date1 = df.parse(strDate1);
            Date date2 = df.parse(strDate2);

            int difference = ((int) ((date1.getTime() / (24 * 60 * 60 * 1000)) - (int) (date2.getTime() / (24 * 60 * 60 * 1000))));
            return Math.abs(difference);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static File saveIamge(Context context, Bitmap finalBitmap) {
        String directoryName = context.getString(R.string.app_name);
        directoryName = directoryName.replace(" ", "");
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/" + directoryName);
        myDir.mkdirs();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fname = "Image-" + timeStamp + ".png";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    public static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isAllPermissionGranted(int[] grantResults) {
        if (grantResults.length == 0) {
            return true;
        } else {
            for (int element : grantResults) {
                if (element != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
            return true;
        }
    }

    public static void clearForm(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
            }

            if (view instanceof ViewGroup && (((ViewGroup) view).getChildCount() > 0))
                clearForm((ViewGroup) view);
        }
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static Spanned fromHtml(String html) {
        Spanned result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return new SpannableString((TextUtils.isEmpty(result.toString()) ? "" : result.toString()));
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String makeHighlightText(String text, String searchText) {
        try {
            String highlightText = text;
            if (!searchText.isEmpty()) {
                highlightText = highlightText.replaceAll(searchText, "<b><font color='black'>" + searchText + "</font></b>");
                return highlightText;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }

    public static long getAppInstallTime(Context context, String packageName) {
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo appInfo = pm.getApplicationInfo(packageName, 0);
            String appFile = appInfo.sourceDir;
            return new File(appFile).lastModified();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void setDateFromDatePicker(Context context, final EditText editText) {
        Calendar calendar;
        int date, month, year;
        calendar = Calendar.getInstance();
        date = calendar.get(Calendar.DATE);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                String myDate = String.format("%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                editText.setText(myDate);
            }
        }, year, month, date);
        datePickerDialog.show();
    }

    public static String convertSecondsToHMmSs(long seconds) {
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        long h = (seconds / (60 * 60)) % 24;
        return String.format("%d:%02d:%02d", h, m, s);
    }

    public static boolean isImageFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("image");
    }

    public static boolean isVideoFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("video");
    }
}