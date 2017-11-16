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
//import android.icu.util.Calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;
import android.support.annotation.RequiresApi;

import com.example.todo.todo.Reciever.AlarmReceiver;
import com.example.todo.todo.helper.DBHelper;
import com.example.todo.todo.R;

public class AddTasksActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton imgbtn1, imgbtn2;
    EditText et3, et4;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private int day, month, year, hour, minute;
    Toolbar toolbar;
    EditText et2, et5;
   // TextView tv2, tv5;
    Long startDate;
    static int count=0;
    String title, description, dueDate, dueTime, priority1;
    int priority;
    String status="CREATED";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tasks);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        imgbtn1 = (ImageButton) findViewById(R.id.imageButton);
        imgbtn2 = (ImageButton) findViewById(R.id.imageButton2);
        et3 = (EditText) findViewById(R.id.editText3);
        et4 = (EditText) findViewById(R.id.editText4);
        et2 = (EditText) findViewById(R.id.editText2);
        et5 = (EditText) findViewById(R.id.editText5);
        et3.setEnabled(false);
        et4.setEnabled(false);
        imgbtn1.setOnClickListener(this);
        imgbtn2.setOnClickListener(this);


        toolbar = (Toolbar) findViewById(R.id.edittoolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("ADD TASK");
        context = getApplicationContext();
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {

        if (et2.getText().toString().equals("")) {
                    et2.requestFocus();
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(context.INPUT_METHOD_SERVICE);
                    inputMethodManager.showSoftInput(et2, InputMethodManager.SHOW_IMPLICIT);
        }

        else if (v == imgbtn1) {
            Calendar newCalendar = Calendar.getInstance();
            datePickerDialog = new DatePickerDialog(this,R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    et3.setText(dateFormatter.format(newDate.getTime()));
                }

            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
            if(et3.getText().equals(""))
            {
                et3.requestFocus();
            }
        }
        else if (v == imgbtn2) {
            final Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    et4.setText(hourOfDay + ":" + minute);
                }
            }
                    , hour, minute, false);
            timePickerDialog.show();
            if(et4.getText().equals(""))
            {
                et4.requestFocus();
            }
        }

    }

    public void radioSelect(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioButton:
                if (checked) {
                    priority = 1;
                    priority1 = "low";
                }
                break;
            case R.id.radioButton2:
                if (checked) {
                    priority = 2;
                    priority1 = "medium";
                }
                break;
            case R.id.radioButton3:
                if (checked) {
                    priority = 3;
                    priority1 = "high";
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int count = 0;
        DBHelper dbHelper = new DBHelper(this);
        Cursor cor = dbHelper.getTitle(dbHelper);
        String title;
        if(cor.moveToFirst())
        {
            do {
                title = cor.getString(0);
                if(title.equals(et2.getText().toString()))
                {
                    count = 1;

                }
            }
            while(cor.moveToNext());
        }

        int id = item.getItemId();
            //noinspection SimplifiableIfStatement
            if (id == R.id.addtaskindatabase) {
                    if (et2.getText().toString().equals("")) {
                    Toast.makeText(this,"Please enter title", Toast.LENGTH_SHORT).show();
                    et2.requestFocus();
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(context.INPUT_METHOD_SERVICE);
                    inputMethodManager.showSoftInput(et2, InputMethodManager.SHOW_IMPLICIT);
                }
                else if (et3.getText().toString().equals("")) {
                    Toast.makeText(this,"Please enter Due Date", Toast.LENGTH_SHORT).show();
                    et3.requestFocus();
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(context.INPUT_METHOD_SERVICE);
                    inputMethodManager.showSoftInput(et2, InputMethodManager.SHOW_IMPLICIT);
                }
                else if (et4.getText().toString().equals("")) {
                    Toast.makeText(this,"Please enter Due Time", Toast.LENGTH_SHORT).show();
                    et4.requestFocus();
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(context.INPUT_METHOD_SERVICE);
                    inputMethodManager.showSoftInput(et2, InputMethodManager.SHOW_IMPLICIT);
                }
                else if(count==1)
                {
                    Toast.makeText(this,"title already exists ", Toast.LENGTH_SHORT).show();
                    et2.requestFocus();
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(context.INPUT_METHOD_SERVICE);
                    inputMethodManager.showSoftInput(et2, InputMethodManager.SHOW_IMPLICIT);
                }
                else {
                    title = et2.getText().toString();
                    dueDate = et3.getText().toString();
                    dueTime = et4.getText().toString();
                    description = et5.getText().toString();


                    String dateTime = dueDate + " " + dueTime;
                    //Toast.makeText(getApplicationContext(),"my date :"+dateTime,Toast.LENGTH_LONG).show();

                    Calendar calendar = Calendar.getInstance();
                    Date curdate = calendar.getTime();

                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy H:m");
                    String shortTimeStr = sdf.format(curdate);


                    //if (dateTime.compareTo(shortTimeStr) < 0) {
                    //Toast.makeText(getApplicationContext(), "Invalid Date/Time", Toast.LENGTH_LONG).show();
                    //} else {

                    count++;
                    try {
                        String dateString = dateTime;
                        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy H:m");
                        Date date = sdf1.parse(dateString);
                        startDate = date.getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    DBHelper mydb = new DBHelper(context);
                    mydb.insert(mydb, title, description, dueDate, dueTime, priority);
                        //new AddTaskConnection(this).execute(title,description,dueDate,dueTime,status,priority1);
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
                }
       // }
            return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }

}