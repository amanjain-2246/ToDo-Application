package com.example.todo.todo.Fragment;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.todo.todo.helper.Content;
import com.example.todo.todo.helper.DBHelper;
import com.example.todo.todo.R;
import com.example.todo.todo.Adapter.TodayRecyclerAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by amanj on 6/4/2017.
 */

public class TomorrowTabFragment extends android.support.v4.app.Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Context context;
    public ArrayList<Content> contentList= new ArrayList<Content>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context=container.getContext();
        View rootView = inflater.inflate(R.layout.tomorrow_tab, container, false);
        recyclerView=(RecyclerView)rootView.findViewById(R.id.recycle_tomorrow_tab);
       layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        DBHelper myhelper = new DBHelper(context);
        Cursor cursorOB = myhelper.getALlRows(myhelper);

        String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());


        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        //String todayAsString = dateFormat.format(today);
        String tomorrowAsString = dateFormat.format(tomorrow);


         if(cursorOB.moveToFirst())
        {

            do {
                String duedate = cursorOB.getString(2);

               if (duedate.equals(tomorrowAsString)) {
                    String title = cursorOB.getString(1);
                     duedate = cursorOB.getString(2);
                    String duetime = cursorOB.getString(3);
                    String description = cursorOB.getString(4);

                    Content content = new Content(title, duedate, duetime, description);
                    contentList.add(content);
                }

            }while (cursorOB.moveToNext());

            adapter =new TodayRecyclerAdapter(contentList);
            recyclerView.setAdapter(adapter);

        }
        else
        {
            Snackbar snackbar = Snackbar.make(container, "No Data Found", Snackbar.LENGTH_LONG);

// Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }


        return rootView;
    }

}
