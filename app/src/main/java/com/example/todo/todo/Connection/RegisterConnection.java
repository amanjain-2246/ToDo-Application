package com.example.todo.todo.Connection;

import android.app.AlertDialog;
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
 * Created by .hp on 13-06-2017.
 */

public class RegisterConnection extends AsyncTask<String,Void, String> {
    private static final String TAG = "RegisterConnection";
    Context context;
    String server,result;
   // public static String chklogin=null;
    ProgressDialog progressDialog;


    public RegisterConnection(Context context)
    {
        this.context = context;
    }
    @Override
    protected void onPreExecute()
    {
//        progressDialog =new ProgressDialog(context);
//        progressDialog.setMessage("Connecting..");
//        progressDialog.setIndeterminate(false);
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setCancelable(false);
//        progressDialog.show();

    }

    @Override
    protected  String doInBackground(String... params) {


        server = context.getString(R.string.server_url);

        String reg_url = server + "/register1.php";

        String method = params[0];
        if(method.equals("Register"))
        {
            String name = params[1];
            String email = params[2];
            String upass = params[3];
            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"
                        +URLEncoder.encode("user_email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"
                        +URLEncoder.encode("user_pass","UTF-8")+"="+URLEncoder.encode(upass,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = "";
                String line = "";
                while((line = bufferedReader.readLine())!=null)
                {
                    response = line;
                }

                bufferedReader.close();
                inputStream.close();
                return response;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
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
       Log.i(TAG, "RegisterConnection: " + res);
      // progressDialog.dismiss();
        if (jsonStr != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                String query_result = jsonObject.getString("query_result");
                 if (query_result.equals("EXIST")) {
                     Toast.makeText(context, "User Already EXIST...!!", Toast.LENGTH_LONG).show();
                 }
                else if (query_result.equals("SUCCESS")) {
                     Toast.makeText(context, "Thankyou for Registration...!!", Toast.LENGTH_LONG).show();
                }
                else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "User Not Added...!!", Toast.LENGTH_LONG).show();
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
