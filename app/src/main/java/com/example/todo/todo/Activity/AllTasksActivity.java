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
import com.example.todo.todo.Adapter.MyRecyclerAdapter;
import com.example.todo.todo.R;

import java.util.ArrayList;

public class AllTasksActivity extends AppCompatActivity{
    Toolbar toolbar;
    Context context;
    public static boolean longClick=false;

    public  String getTitle;
    public  int pos;
    public ArrayList<Content> contentList= new ArrayList<Content>();
    Bundle bundle;
    public static int count;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);
        bundle = savedInstanceState;
        toolbar=(Toolbar)findViewById(R.id.toolbar4);
        count= 0;
        setSupportActionBar(toolbar);
        ActionBar ab=getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("ALL TASKS");
        constraintLayout = (ConstraintLayout) findViewById(R.id.constalltask);
        longClick=false;
//        if(MainActivity.color == 0)
//            ab.setBackgroundDrawable(new ColorDrawable(0xff00DDED));
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        DBHelper myhelper = new DBHelper(this);
        Cursor cursorOB = myhelper.getALlRows(myhelper);
        if(cursorOB.moveToFirst())
        {
            do {

                String title = cursorOB.getString(1);
                String duedate = cursorOB.getString(2);
                String duetime = cursorOB.getString(3);
                String description=cursorOB.getString(4);

                Content content = new Content(title, duedate, duetime,description);
                contentList.add(content);
            }while (cursorOB.moveToNext());

            adapter =new MyRecyclerAdapter(contentList,this);
            recyclerView.setAdapter(adapter);

        }
        else
        {
            Snackbar snackbar = Snackbar.make(constraintLayout, "No Data Found", Snackbar.LENGTH_LONG);

// Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }

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
                    Intent intent1 = new Intent(AllTasksActivity.this,AllTasksActivity.class);
                    startActivity(intent1);
                }
            });
            alertDialog.show();
            //Toast.makeText(getApplicationContext(), "deleted "+getTitle, Toast.LENGTH_LONG).show();
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
                Intent intent1 = new Intent(AllTasksActivity.this,AllTasksActivity.class);
                startActivity(intent1);
            }
        });
        alertDialog.show();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }



























}
