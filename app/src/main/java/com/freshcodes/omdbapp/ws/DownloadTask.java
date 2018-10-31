package com.freshcodes.omdbapp.ws;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadTask extends AsyncTask<String, String, String> {
    Context context;
    String directoryPath;
    String fileName;
    DownloadTaskListener downloadTaskListener;
    private ProgressDialog mProgressDialog;

    public DownloadTask(Context context, String directoryPath, String fileName, DownloadTaskListener downloadTaskListener) {
        this.context = context;
        this.directoryPath = directoryPath;
        this.fileName = fileName;
        this.downloadTaskListener = downloadTaskListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("Downloading file..");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    protected String doInBackground(String... aurl) {
        int count;
        try {
            URL url = new URL(aurl[0]);
            URLConnection conexion = url.openConnection();
            conexion.connect();

            int lenghtOfFile = conexion.getContentLength();
            Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

            InputStream input = new BufferedInputStream(url.openStream());
            File f = new File(Environment.getExternalStorageDirectory(), directoryPath);
            if (!f.exists()) {
                f.mkdirs();
            }
            OutputStream output = new FileOutputStream(f.getPath() + File.separator + fileName);

            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            Log.e("Error", TextUtils.isEmpty(e.getMessage()) ? "Unknown" : e.getMessage());
        }
        return aurl[0];
    }

    protected void onProgressUpdate(String... progress) {
        Log.d("ANDRO_ASYNC", progress[0]);
        mProgressDialog.setProgress(Integer.parseInt(progress[0]));
    }

    @Override
    protected void onPostExecute(String url) {
        mProgressDialog.dismiss();
        try {
            File f = new File(Environment.getExternalStorageDirectory(), directoryPath);
            File file = new File(f.getPath() + File.separator + fileName);
            if (file.exists()) {
                downloadTaskListener.onResult(true, file);
            } else
                downloadTaskListener.onResult(false, null);
        } catch (Exception e) {
            e.printStackTrace();
            downloadTaskListener.onResult(false, null);
        }
    }

    public interface DownloadTaskListener {
        void onResult(boolean success, File file);
    }
}