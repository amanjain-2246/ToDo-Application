package com.example.todo.todo.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todo.todo.helper.Content;
import com.example.todo.todo.helper.DBHelper;
import com.example.todo.todo.Adapter.MyRecyclerCalenderView;
import com.example.todo.todo.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalenderActivity extends AppCompatActivity {

    public static int count;
    Toolbar toolbar;
    long startDate; ActionBar ab;
    public static boolean longClick=false;
    public  String getTitle;
    public  int pos;
    Context context;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    CompactCalendarView compactCalendarView;
    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MMMM-yyyy",Locale.US);
    public ArrayList<Content> contentList= new ArrayList<Content>();
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_task);
        count = 0;
        toolbar = (Toolbar) findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("CALENDER VIEW");
         context=getApplicationContext();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_calaneder);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        constraintLayout = (ConstraintLayout) findViewById(R.id.constcal);


         compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendarView.setFirstDayOfWeek(Calendar.SUNDAY);
        compactCalendarView.setUseThreeLetterAbbreviation(true);

        DBHelper myhelper = new DBHelper(this);
        Cursor cursorOB = myhelper.getALlRows(myhelper);
        if (cursorOB.moveToFirst()) {
            do {

                String title = cursorOB.getString(1);
                String duedate = cursorOB.getString(2);
                String duetime = cursorOB.getString(3);
                String description = cursorOB.getString(4);

                try {
                    String dateString = duedate;
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    Date date = sdf.parse(dateString);
                    startDate = date.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Event ev1 = new Event(Color.WHITE, startDate, title);
                compactCalendarView.addEvent(ev1);

                Content content = new Content(title, duedate, duetime, description);

                contentList.add(content);


            } while (cursorOB.moveToNext());




        }


            compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
                @Override
                public void onDayClick(Date dateClicked)
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String mydate=sdf.format(dateClicked);


                     ArrayList<Content> contentList1= new ArrayList<Content>();

                    for(int i=0;i<contentList.size();++i)
                    {
                       Content data=contentList.get(i);
                        String date=data.getDuedate();

                        if(mydate.equals(date))
                        {

                            String title=data.getTitle();
                            String duedate=data.getDuedate();
                            String duetime=data.getDuetime();
                            String description=data.getDescription();

                            Content content = new Content(title, duedate, duetime, description);
                            contentList1.add(content);

                        }
                        else
                        {
//                            Snackbar snackbar = Snackbar.make(constraintLayout, "No Data Found", Snackbar.LENGTH_LONG);
//
//// Changing action button text color
//                            View sbView = snackbar.getView();
//                            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
//                            textView.setTextColor(Color.YELLOW);
//                            snackbar.show();
                        }

                    }
                    adapter =new MyRecyclerCalenderView(contentList1,CalenderActivity.this);
                    recyclerView.setAdapter(adapter);


          // Toast.makeText(getApplicationContext(),""+mydate,Toast.LENGTH_SHORT).show();


                }

                @Override
                public void onMonthScroll(Date firstDayOfNewMonth) {
                    ab.setTitle(simpleDateFormat.format(firstDayOfNewMonth));

                }
            });
    }


    public void funcCall(String title){
        longClick=true;
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.edit_delete_appbar);
        adapter.notifyDataSetChanged();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getTitle=title;
    }

    public  void prepareSelection(View view,int position,String title)
    {
        getTitle=title;
        pos=position;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final DBHelper dbHelper=new DBHelper(this);
        int id = item.getItemId();

        if(id==R.id.edit_task)
        {
            Intent intent=new Intent(this,EditTasksActivity.class);
            intent.putExtra("Title",getTitle);
            startActivity(intent);
        }
        if(id==R.id.delete_task)
        {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("DELETING THE ITEN");
            alertDialog.setMessage("Are you sure you want to delete.");
            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dbHelper.delete(dbHelper,getTitle);
                    Intent intent1 = new Intent(CalenderActivity.this,CalenderActivity.class);
                    startActivity(intent1);
                }
            });
            alertDialog.show();
          //  Toast.makeText(getApplicationContext(), "deleted "+getTitle, Toast.LENGTH_LONG).show();
            toolbar.getMenu().clear();

        }

        return super.onOptionsItemSelected(item);
    }


    public  void showerror()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("ERROR");
        alertDialog.setMessage("you cannot select more then one option");
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent1 = new Intent(CalenderActivity.this,CalenderActivity.class);
                startActivity(intent1);
            }
        });
        alertDialog.show();

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
    }
