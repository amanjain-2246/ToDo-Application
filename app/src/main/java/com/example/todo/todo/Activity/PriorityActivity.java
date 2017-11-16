package com.example.todo.todo.Activity;

import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.todo.todo.helper.Content;
import com.example.todo.todo.helper.DBHelper;
import com.example.todo.todo.R;
import com.example.todo.todo.Adapter.TodayRecyclerAdapter;

import java.util.ArrayList;

public class PriorityActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Toolbar toolbar;
    Spinner sp;
    int prioritythis, prioritydatabase;
    Cursor cor;
    String title,description,duedate,duetime;
    public ArrayList<Content> contentList= new ArrayList<Content>();
    public ArrayList<Content> contentList1= new ArrayList<Content>();
    public ArrayList<Content> contentList2= new ArrayList<Content>();

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter1;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_priority_);
        toolbar=(Toolbar)findViewById(R.id.toolbar6);
        setSupportActionBar(toolbar);
        ActionBar ab=getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("SORT BY PRIORITY");
        prioritythis =0;
        recyclerView = (RecyclerView) findViewById(R.id.recycler_priority);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        sp = (Spinner) findViewById(R.id.spinner_peiority);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.spinner_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(this);
        DBHelper dbHelper = new DBHelper(this);
        cor= dbHelper.prioritySort(dbHelper);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position==0)
        {
            contentList.clear();
             if(cor.moveToFirst())
            {
                prioritythis =1;
                do {

                    title = cor.getString(1);
                    duedate = cor.getString(2);
                    duetime = cor.getString(3);
                    description = cor.getString(4);
                    prioritydatabase = cor.getInt(5);

                    if (prioritydatabase == prioritythis) {
                        Content content = new Content(title, duedate, duetime, description);
                        contentList.add(content);
                    }
                }
                while (cor.moveToNext());

                adapter1 =new TodayRecyclerAdapter(contentList);
                recyclerView.setAdapter(adapter1);

            }
         //   Toast.makeText(this, "Low Priority", Toast.LENGTH_LONG).show();


        }
        else if(position==1)
        {
            contentList1.clear();
            if(cor.moveToFirst())
            {
                prioritythis =2;
                do {

                    title = cor.getString(1);
                    duedate = cor.getString(2);
                    duetime = cor.getString(3);
                    description = cor.getString(4);
                    prioritydatabase = cor.getInt(5);

                    if (prioritydatabase == prioritythis) {
                        Content content = new Content(title, duedate, duetime, description);
                        contentList1.add(content);
                    }
                }
                while (cor.moveToNext());

                adapter1 =new TodayRecyclerAdapter(contentList1);
                recyclerView.setAdapter(adapter1);

            }
         //   Toast.makeText(this, "Medium Priority", Toast.LENGTH_LONG).show();


        }
        else if(position==2)
        {
            contentList2.clear();
            if(cor.moveToFirst())
            {
                prioritythis =3;
                do {

                    title = cor.getString(1);
                    duedate = cor.getString(2);
                    duetime = cor.getString(3);
                    description = cor.getString(4);
                    prioritydatabase = cor.getInt(5);

                    if (prioritydatabase == prioritythis) {
                        Content content = new Content(title, duedate, duetime, description);
                        contentList2.add(content);
                    }
                }
                while (cor.moveToNext());

                adapter1 =new TodayRecyclerAdapter(contentList2);
                recyclerView.setAdapter(adapter1);

            }
         //   Toast.makeText(this, "High Priority", Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
