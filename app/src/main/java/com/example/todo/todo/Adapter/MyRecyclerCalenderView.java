package com.example.todo.todo.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.todo.todo.Activity.CalenderActivity;
import com.example.todo.todo.helper.Content;
import com.example.todo.todo.R;

import java.util.ArrayList;

public class MyRecyclerCalenderView extends RecyclerView.Adapter<MyRecyclerCalenderView.MyRecyclerCalenderViewHolder>
        {
public ArrayList<Content> contentlist;
       // AllTasksActivity allTasksActivity;
       CalenderActivity calenderActivity;
        Context context;


public MyRecyclerCalenderView(ArrayList<Content> contentlist,Context context)
        {
        this.contentlist=contentlist;
        this.context=context;
            calenderActivity = (CalenderActivity) context;
        }

@Override
public MyRecyclerCalenderViewHolder onCreateViewHolder(ViewGroup parent, int viewType)

        {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_todo,parent,false);
        MyRecyclerCalenderViewHolder recyclerViewHolder =  new MyRecyclerCalenderViewHolder(view, calenderActivity,contentlist);
        return recyclerViewHolder;
        }

@Override
public void onBindViewHolder(MyRecyclerCalenderViewHolder holder, int position)
        {
        Content content=contentlist.get(position);
        holder.TextTitle.setText(content.getTitle());

        holder.TextDate.setText(content.getDuedate());
        holder.TextTime.setText(content.getDuetime());
        }

@Override    public int getItemCount() {
        return contentlist.size();
        }

public static class MyRecyclerCalenderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener
{
    public ArrayList<Content> contentlist=new ArrayList<Content>();
    public Context context;
   // AllTasksActivity allTasksActivity;
    CalenderActivity calenderActivity;
    TextView TextTitle,TextDate,TextTime;
    CardView cardView;


    public MyRecyclerCalenderViewHolder(View itemView, CalenderActivity calenderActivity, ArrayList<Content> list)  {
        super(itemView);

        TextTitle = (TextView) itemView.findViewById(R.id.TextTitle);
        TextDate = (TextView) itemView.findViewById(R.id.TextDate);
        TextTime = (TextView) itemView.findViewById(R.id.TextTime);
        //this.allTasksActivity=allTasksActivity;
        this.calenderActivity = calenderActivity;
        context= calenderActivity;
        contentlist=list;
        cardView=(CardView)itemView.findViewById(R.id.cardview);
      cardView.setOnLongClickListener(this);
       itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
            int position = getAdapterPosition();
            Content data = contentlist.get(position);

            //data.toString();
            //Toast.makeText(context,"position :"+data.getDescription(),Toast.LENGTH_LONG).show();
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle(data.getTitle());
            alertDialog.setMessage(data.getDescription() + "\n" + data.duedate + "\n" + data.duetime);


            alertDialog.show();

    }

    @Override
    public boolean onLongClick(View v) {
       CalenderActivity.count++;
        if(CalenderActivity.count==1) {
            cardView.setBackgroundColor(Color.GRAY);
            int position = getAdapterPosition();
            Content data = contentlist.get(position);
            String title = data.getTitle();
            calenderActivity.funcCall(title);
            return true;
        }
        else {
            calenderActivity.showerror();
            return false;
        }
    }
}


}