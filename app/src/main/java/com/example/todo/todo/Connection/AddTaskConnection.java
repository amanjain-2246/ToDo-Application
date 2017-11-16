package com.example.todo.todo.Connection;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.example.todo.todo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by amanj on 7/2/2017.
 */

public class AddTaskConnection extends AsyncTask<String,Void,String> {
    String title, desc, due_date, due_time, status, priority, server;

    Context context;
    SharedPreferences sp;
    String email;

    public AddTaskConnection(Context context) {
        this.context = context;
        sp=context.getSharedPreferences("Login",0);
        email=sp.getString("email","");
    }


    @Override
    protected String doInBackground(String... params) {
        title = params[0];
        desc = params[1];
        due_date = params[2];
        due_time = params[3];
        status = params[4];
        priority = params[5];
        server = context.getString(R.string.server_url);


        String data, link, result;
        BufferedReader bufferedReader;
        try {
            data = URLEncoder.encode("user_mail", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
            data += "&" + URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(title, "UTF-8");
            data += "&" + URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(desc, "UTF-8");
            data += "&" + URLEncoder.encode("duedate", "UTF-8") + "=" + URLEncoder.encode(due_date, "UTF-8");
            data += "&" + URLEncoder.encode("duetime", "UTF-8") + "=" + URLEncoder.encode(due_time, "UTF-8");
            data += "&" + URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode(status, "UTF-8");
            data += "&" + URLEncoder.encode("priority", "UTF-8") + "=" + URLEncoder.encode(priority, "UTF-8");

            link = server + "/addTodo.php";

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

            //     HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            result = bufferedReader.readLine();
            bufferedReader.close();
            return result;
        } catch (Exception ex) {
            //Toast.makeText(context,"Data_not_found",Toast.LENGTH_LONG).show();
            return new String("Exception : " + ex.getMessage());
        }

    }


    @Override
    protected void onPostExecute(String res) {
        String jsonStr = res;
        if (jsonStr != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                String query_result = jsonObject.getString("query_result");
                if (query_result.equals("SUCCESS")) {
                   // Toast.makeText(context, "Todo Added Successfully...!!", Toast.LENGTH_LONG).show();
                } else if (query_result.equals("FAILURE")) {
                   // Toast.makeText(context, "Todo Not Added...!!", Toast.LENGTH_LONG).show();
                } else {
                   // Toast.makeText(context, "Could not connect to server", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                //Toast.makeText(context, "Error parsing JSON data.", Toast.LENGTH_LONG).show();
            }
        } else {
            //Toast.makeText(context, "Could not get JSON data", Toast.LENGTH_LONG).show();
        }
    }

}
