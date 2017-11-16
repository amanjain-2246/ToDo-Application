package com.example.todo.todo.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.support.annotation.RequiresApi;

import com.example.todo.todo.Reciever.AlarmReceiver;
import com.example.todo.todo.helper.DBHelper;
import com.example.todo.todo.R;

import java.text.SimpleDateFormat;

public class EditTasksActivity extends AppCompatActivity implements View.OnClickListener{
    ImageButton imgbtn1, imgbtn2;
    EditText et3, et4,et2,et5;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private int day, month, year, hour, minute;
    Toolbar toolbar;
    public  int priority;
    RadioButton rb1,rb2,rb3;
    static int count=0;
    Long startDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tasks);
        Intent intent=getIntent();
        String mytitle=intent.getStringExtra("Title");
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        imgbtn1 = (ImageButton) findViewById(R.id.imageButton);
        imgbtn2 = (ImageButton) findViewById(R.id.imageButton2);
        et3 = (EditText) findViewById(R.id.editText3);
        et2 =(EditText)findViewById(R.id.editText2);
        et4 = (EditText) findViewById(R.id.editText4);
        et5=(EditText)findViewById(R.id.editText5);
        rb1 = (RadioButton) findViewById(R.id.radioButton);
        rb2 = (RadioButton) findViewById(R.id.radioButton2);
        rb3 = (RadioButton) findViewById(R.id.radioButton3);
        imgbtn1.setOnClickListener(this);
        imgbtn2.setOnClickListener(this);
        et2.setEnabled(false);
        et3.setEnabled(false);
        et4.setEnabled(false);
        toolbar=(Toolbar)findViewById(R.id.edittoolbar);
        setSupportActionBar(toolbar);
        ActionBar ab=getSupportActionBar();
        ab.setTitle("EDIT TASK");
         DBHelper dbHelper = new DBHelper(this);
        Cursor con=dbHelper.shoedesc(dbHelper,mytitle);
        if (con.moveToFirst())
        {
            String title = con.getString(0);
            String description=con.getString(1);
            String duedate = con.getString(2);
            String duetime = con.getString(3);
            priority = con.getInt(4);
            if(priority==1)
            {
                rb1.setChecked(true);
            }
            else if(priority==2)
            {
                rb2.setChecked(true);
            }
            else if(priority==3)
            {
                rb3.setChecked(true);
            }
           // String priority=con.getString(5);
            et2.setText(title);
            et3.setText(duedate);
            et4.setText(duetime);
            et5.setText(description);
        }
    }
    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {

            if (v == imgbtn1) {
                Calendar newCalendar =Calendar.getInstance();
                datePickerDialog = new DatePickerDialog(this,R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        et3.setText(dateFormatter.format(newDate.getTime()));
                    }

                },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();



        }
        if (v == imgbtn2) {
            final Calendar c= Calendar.getInstance();
            hour=c.get(Calendar.HOUR_OF_DAY);
            minute=c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    et4.setText(hourOfDay+":"+minute);
                }
            }
                    ,hour,minute,false);
            timePickerDialog.show();
        }



    }

    public void radioSelect(View view)
    {
        boolean checked=((RadioButton) view).isChecked();
        switch(view.getId())
        {
            case R.id.radioButton:
                if (checked)
                {
                    priority=1;
                }break;
            case R.id.radioButton2:
                if (checked)
                {
                    priority=2;
                }break;
            case R.id.radioButton3:
                if(checked){
                    priority=3;
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_button, menu);
        return true;


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
            super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

            if (id == R.id.addtaskindatabase) {
                String title = et2.getText().toString();
                String dueDate = et3.getText().toString();
                String dueTime = et4.getText().toString();
                String description = et5.getText().toString();


                String dateTime = dueDate + " " + dueTime;
                //Toast.makeText(getApplicationContext(),"my date :"+dateTime,Toast.LENGTH_LONG).show();

                Calendar calendar = Calendar.getInstance();
                Date curdate = calendar.getTime();

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy H:m");
                String shortTimeStr = sdf.format(curdate);

                    count++;
                    try {
                        String dateString = dateTime;
                        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy H:m");
                        Date date = sdf1.parse(dateString);
                        startDate = date.getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    DBHelper mydb = new DBHelper(this);
                    mydb.update(mydb, title, description, dueDate, dueTime, priority);
                    Intent intent = new Intent(this, MainActivity.class);


                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    Intent i = new Intent(getBaseContext(), AlarmReceiver.class);
                    i.putExtra("Title", title);
                    i.putExtra("Description", description);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), count, i, PendingIntent.FLAG_UPDATE_CURRENT);

                    alarmManager.set(AlarmManager.RTC_WAKEUP, startDate, pendingIntent);


                    startActivity(intent);
                    finish();

                }


        return super.onOptionsItemSelected(item);
    }
}
