package com.example.shivang.mas;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class uplfile  extends AsyncTask <String, Void, String> {
    String sourceFileUri = "/data/data/com.example.shivang.mas/databases/";
    ProgressDialog br;
    AlertDialog.Builder ad;
    Context ct;
    String msg="noerr";
    @Override
    protected String doInBackground(String... params) {

        try {
            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            File sourceFile = new File(sourceFileUri);

            if (sourceFile.isFile()) {

                try {
                    String upLoadServerUri = "http://qfrat.co.in/php/mas_sync.php?";

                    // open a URL connection to the Servlet
                    FileInputStream fileInputStream = new FileInputStream(
                            sourceFile);
                    URL url = new URL(upLoadServerUri);

                    // Open a HTTP connection to the URL
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // Allow Inputs
                    conn.setDoOutput(true); // Allow Outputs
                    conn.setUseCaches(false); // Don't use a Cached Copy
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("ENCTYPE",
                            "multipart/form-data");
                    conn.setRequestProperty("Content-Type",
                            "multipart/form-data;boundary=" + boundary);
                    conn.setRequestProperty("bill", sourceFileUri);

                    dos = new DataOutputStream(conn.getOutputStream());

                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"bill\";filename=\""
                            + sourceFileUri + "\"" + lineEnd);

                    dos.writeBytes(lineEnd);

                    // create a buffer of maximum size
                    bytesAvailable = fileInputStream.available();

                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];

                    // read file and write it into form...
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {

                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math
                                .min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0,
                                bufferSize);

                    }

                    // send multipart form data necesssary after file
                    // data...
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens
                            + lineEnd);

                    // Responses from the server (code and message)
                    int serverResponseCode = conn.getResponseCode();
                    String serverResponseMessage = conn
                            .getResponseMessage();

                    if (serverResponseCode == 200) {

                        Log.d("Upload","SUCCESS SYNC WITH SERVER");

                    }

                    // close the streams //
                    fileInputStream.close();
                    dos.flush();
                    dos.close();

                } catch (Exception e) {

                    Log.e("INTNET",e.toString());

                }
            }


        } catch (Exception ex) {
            msg=ex.toString();
            alrtmsg("FAILURE","Failed to resolve hostname for the server. Please check your internet connection.");
            Log.e("INTNET", ex.toString());
        }
        return "Executed";
    }

    @Override
    protected void onPostExecute(String result) {
        br.dismiss();
    }

    @Override
    protected void onPreExecute() {
        br.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }
    protected void setfile(String nme)
    {
        sourceFileUri+=nme;
    }
    protected void setbar(ProgressDialog bre,Context adb)
    {
        this.ct=adb;
        this.br=bre;
    }
    protected void alrtmsg(String tag, String msg)
    {
        ad = new AlertDialog.Builder(ct);
        ad.setMessage(msg);
        ad.setTitle(tag);
        ad.create();
        ad.show();
    }
}
