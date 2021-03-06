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

import com.example.todo.todo.Activity.AllTasksActivity;
import com.example.todo.todo.helper.Content;
import com.example.todo.todo.R;

import java.util.ArrayList;

/**
 * Created by amanj on 6/6/2017.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyRecyclerViewHolder>
{
    public ArrayList<Content> contentlist;
    AllTasksActivity allTasksActivity;
     Context context;


    public MyRecyclerAdapter(ArrayList<Content> contentlist,Context context)
    {
        this.contentlist=contentlist;
        this.context=context;
        allTasksActivity=(AllTasksActivity)context;
    }

    @Override
    public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)

    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_todo,parent,false);
        MyRecyclerViewHolder recyclerViewHolder =  new MyRecyclerViewHolder(view,allTasksActivity,contentlist);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewHolder holder, int position)
    {
        Content content=contentlist.get(position);
        holder.TextTitle.setText(content.getTitle());

        holder.TextDate.setText(content.getDuedate());
        holder.TextTime.setText(content.getDuetime());
    }

    @Override    public int getItemCount() {
        return contentlist.size();
    }

    public static class MyRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener
    {
        public ArrayList<Content> contentlist=new ArrayList<Content>();
        public Context context;
        AllTasksActivity allTasksActivity;
        TextView TextTitle,TextDate,TextTime;
        CardView cardView;


        public MyRecyclerViewHolder(View itemView,AllTasksActivity allTasksActivity,ArrayList<Content> list) {
            super(itemView);

            TextTitle = (TextView) itemView.findViewById(R.id.TextTitle);
            TextDate = (TextView) itemView.findViewById(R.id.TextDate);
            TextTime = (TextView) itemView.findViewById(R.id.TextTime);
            this.allTasksActivity=allTasksActivity;
            context=allTasksActivity;
            contentlist=list;
            cardView=(CardView)itemView.findViewById(R.id.cardview);
            cardView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (AllTasksActivity.longClick == false) {
                int position = getAdapterPosition();
                Content data = contentlist.get(position);

                //data.toString();
                //Toast.makeText(context,"position :"+data.getDescription(),Toast.LENGTH_LONG).show();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle(data.getTitle());
                alertDialog.setMessage(data.getDescription() + "\n" + data.duedate + "\n" + data.duetime);


                alertDialog.show();
            }
            else
            {
                int position = getAdapterPosition();
                Content data = contentlist.get(position);
              //  String titlesend=data.getTitle().toString();
                allTasksActivity.prepareSelection(v,getAdapterPosition(),data.getTitle());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            AllTasksActivity.count++;
            if(AllTasksActivity.count==1) {
               cardView.setBackgroundColor(Color.GRAY);
               // cardView.requestFocus();
                int position = getAdapterPosition();
                Content data = contentlist.get(position);
                String title = data.getTitle();
                allTasksActivity.funcCall(title);
                return true;
            }
            else {
              allTasksActivity.showerror();
                return false;
            }
        }
    }


}