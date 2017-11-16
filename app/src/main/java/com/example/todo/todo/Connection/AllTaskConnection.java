package com.example.todo.todo.Connection;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.todo.todo.R;
import com.example.todo.todo.Reciever.AlarmReceiver;
import com.example.todo.todo.helper.DBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by amanj on 7/6/2017.
 */

public class AllTaskConnection extends AsyncTask<String, Void, String> {
    String  server;
    String[] title,description,duedate,duetime,priority;
    int priority2;
    Context context;
    static int count = 0;
    SharedPreferences sp;
    Long startDate;
    String email;

    public  AllTaskConnection(Context context)
    {
        this.context = context;
    }
    @Override
    protected String doInBackground(String... params) {
        sp=  context.getSharedPreferences("Login",0);
        email=sp.getString("email","");
        //Toast.makeText(context,email,Toast.LENGTH_LONG).show();
        server = context.getString(R.string.server_url);

        String data, link, result;
        BufferedReader bufferedReader;
        try
        {
            data = URLEncoder.encode("user_mail", "UTF-8")+"="+URLEncoder.encode(email, "UTF-8");
            link = server+"/showtodo.php";
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            OutputStream os=connection.getOutputStream();
            BufferedWriter wr=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            wr.write( data );
            wr.flush();
            wr.close();
            os.close();

            //     HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            result = bufferedReader.readLine();
            bufferedReader.close();
            return result;

        }
        catch (Exception ex)
        {

            //Toast.makeText(context,"Contact developer:"+ex.getMessage(),Toast.LENGTH_LONG).show();
            return new String("Exception : "+ex.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String res) {
        String jSonres = res;


        // Toast.makeText(context,res,Toast.LENGTH_LONG).show();
        JSONObject jsonObject;
        if (jSonres != null) {
            try {
                jsonObject = new JSONObject(jSonres);
                JSONArray jsonArray = (JSONArray) jsonObject.get("server_response");
                title = new String[jsonArray.length()];
                description = new String[jsonArray.length()];
                duedate = new String[jsonArray.length()];
                duetime = new String[jsonArray.length()];
                priority = new String[jsonArray.length()];
                DBHelper dbHelper=new DBHelper(context);
                //Content content;
                for (int i = 0; i < jsonArray.length(); i++) {
                    count++;
                    title[i] = jsonArray.getJSONObject(i).getString("todo_title");
                    description[i] = jsonArray.getJSONObject(i).getString("todo_description");
                    duedate[i] = jsonArray.getJSONObject(i).getString("todo_due_date");
                    duetime[i] = jsonArray.getJSONObject(i).getString("todo_due_time");
                    priority[i] = jsonArray.getJSONObject(i).getString("todo_priority");
                    if(priority[i].equals("1"))
                    {
                        priority2=1;
                    }
                    else if(priority[i].equals("2"))
                    {
                        priority2=2;
                    }
                    else if(priority[i].equals("3"))
                    {
                        priority2=3;
                    }

                    String dateTime = duedate[i] + " " + duetime[i];

                    try {
                        String dateString = dateTime;
                        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy H:m");
                        Date date = sdf1.parse(dateString);
                        startDate = date.getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(context, AlarmReceiver.class);
                    intent.putExtra("Title", title[i]);
                    intent.putExtra("Description", description[i]);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, count, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, startDate, pendingIntent);
                    dbHelper.insert(dbHelper,title[i],description[i], duedate[i], duetime[i],priority2);


                }

            }
            catch (JSONException e) {
                e.printStackTrace();
               // Toast.makeText(context, "error:"+e.getMessage(), Toast.LENGTH_LONG).show();
            }

        }
        else
        {
           // Toast.makeText(context, "Could not get JSON data", Toast.LENGTH_LONG).show();
        }
    }
}
