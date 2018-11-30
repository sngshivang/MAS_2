package com.example.shivang.mas;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class dwnfile  extends AsyncTask <String, Void, String> {
    String urll="http://qfrat.co.in/php/uploads/";
    ProgressDialog br;
    @Override
    protected String doInBackground(String... params) {
        int count;
        try {
            URL url = new URL(urll);
            URLConnection conection = url.openConnection();
            conection.connect();

            // this will be useful so that you can show a tipical 0-100%
            // progress bar
            int lenghtOfFile = conection.getContentLength();

            // download the file
            InputStream input = new BufferedInputStream(url.openStream(),
                    8192);

            // Output stream
            File file = new File("data/data/com.example.shivang.mas/databases/MAS");
            boolean deleted = file.delete();
            Log.d("Isdeleted",String.valueOf(deleted));
            OutputStream output = new FileOutputStream("/data/data/com.example.shivang.mas/databases/MAS");

            byte data[] = new byte[1024];

            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                // After this onProgressUpdate will be called

                // writing data to file
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        br.dismiss();
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }
    protected void setfile(String nme)
    {
        urll+=nme;
    }
    protected void setbar(ProgressDialog bre)
    {
        this.br=bre;
    }
}
