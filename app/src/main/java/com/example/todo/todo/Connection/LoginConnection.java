package com.example.todo.todo.Connection;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.todo.todo.Activity.LoginActivity;
import com.example.todo.todo.Activity.MainActivity;
import com.example.todo.todo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by amanj on 7/13/2017.
 */

public class LoginConnection extends AsyncTask<String,Void, String> {
    private static final String TAG = "LoginConnection";
    Context context;
    String result;
    String server;
    ProgressDialog progressDialog;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loding..");
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    public LoginConnection(Context context)
    {
        this.context = context;
    }
    @Override
    protected String doInBackground(String... params) {
        server = context.getString(R.string.server_url);
        String login_url = server + "/login.php";
        String method = params[0];

        if (method.equals("login")) {
            String login_mame = params[1];
            String login_pass = params[2];
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("login_name", "UTF-8") + "=" + URLEncoder.encode(login_mame, "UTF-8") + "&" +
                        URLEncoder.encode("login_pass", "UTF-8") + "=" + URLEncoder.encode(login_pass, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();



                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                result = bufferedReader.readLine();
                bufferedReader.close();
                return result;

            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
            return null;
        }



    @Override
    protected void  onProgressUpdate(Void... values)
    {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String res) {
        String jsonStr = res;
        progressDialog.dismiss();
        Log.i(TAG, "LoginConnection: " + res);
        if (jsonStr != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                String query_result = jsonObject.getString("query_result");
                if (query_result.equals("SUCCESS")) {
                   String email = jsonObject.getString("email");
                    SharedPreferences sp=context.getSharedPreferences("Login",0);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("email",email);
                    editor.commit();
                    AllTaskConnection allTaskConnection = new AllTaskConnection(context);
                    allTaskConnection.execute();
                    Intent intent = new Intent(context,MainActivity.class);
                    context.startActivity(intent);
                   // Toast.makeText(context, "User  Login...!!"+res, Toast.LENGTH_LONG).show();
                } else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "Enter Correct Email/Password...!!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(context, "Could not connect to server", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                //Toast.makeText(context, "Error parsing JSON data.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(context, "Could not get JSON data", Toast.LENGTH_LONG).show();
        }



    }













}