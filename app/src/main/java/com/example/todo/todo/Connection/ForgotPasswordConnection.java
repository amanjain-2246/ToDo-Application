package com.example.todo.todo.Connection;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.todo.todo.Activity.MainActivity;
import com.example.todo.todo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by amanj on 7/9/2017.
 */

public class ForgotPasswordConnection extends AsyncTask<String, Void, String> {
    String server;
    private static final String TAG = "ForgotPassConnection";
    // String[] title,description,duedate,duetime,priority;
    // int priority2;
    Context context;
    //  SharedPreferences sp;
    String email;
    ProgressDialog progressDialog;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Sending..");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public ForgotPasswordConnection(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        email = params[0];
        server = context.getString(R.string.server_url);
        String data, link, result;
        BufferedReader bufferedReader;
        try {
            data = URLEncoder.encode("user_mail", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
            link = server + "/forgotmail.php";
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            wr.write(data);
            wr.flush();
            wr.close();
            os.close();
           bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            result = bufferedReader.readLine();
            bufferedReader.close();
            return result;

        } catch (Exception ex) {
            return new String("Exception : " + ex.getMessage());
        }


    }


    @Override
    protected void onPostExecute(String res) {
        progressDialog.dismiss();
        String jsonStr = res;
        Log.i(TAG, "ForgotPassConnection: " + res);
       // Toast.makeText(context, "" + jsonStr, Toast.LENGTH_LONG).show();
        if (jsonStr != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                String query_result = jsonObject.getString("mail_sent");
                if (query_result.equals("YES")) {
                    Toast.makeText(context, "Email Sent Successfully...!!", Toast.LENGTH_LONG).show();
                } else if (query_result.equals("NO")) {
                   Toast.makeText(context, "Enter Correct Email id...!!", Toast.LENGTH_LONG).show();
                }
                else
                    {
                   // Toast.makeText(context, "Could not connect to server", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
               // Toast.makeText(context, "Error parsing JSON data." + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
           // Toast.makeText(context, "Could not get JSON data", Toast.LENGTH_LONG).show();
        }
    }
}







