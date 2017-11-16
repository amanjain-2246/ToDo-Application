package com.example.todo.todo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.todo.todo.helper.DBHelper;
import com.example.todo.todo.R;

public class SelectionActivity extends AppCompatActivity {
    Toolbar toolbar;
    String title;
    TextView t1,t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        t1=(TextView)findViewById(R.id.textView14);
        t2=(TextView)findViewById(R.id.textView15);


        toolbar = (Toolbar) findViewById(R.id.toolbar7);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("SELECT YOUR OPTIONS");


        Intent intent = getIntent();
        title = intent.getStringExtra("Title");
        String description = intent.getStringExtra("Description");

        t1.setText(title);
        t2.setText(description);
    }

    public void complete(View view)
    {
        DBHelper dbHelper = new DBHelper(this);
        dbHelper.statusUpdate(dbHelper,title,"COMPLETED");
        Intent intent = new Intent(this,CompletedTaskActivity.class);
        startActivity(intent);
    }

    public void delay(View view)
    {
        DBHelper dbHelper = new DBHelper(this);
        dbHelper.statusUpdate(dbHelper,title,"REMAINING");
        Intent i = new Intent(this,EditTasksActivity.class);
        i.putExtra("Title",title);
        startActivity(i);
    }
}
