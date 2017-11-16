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

import com.example.todo.todo.Activity.CompletedTaskActivity;
import com.example.todo.todo.Activity.RemainingTasksActivity;
import com.example.todo.todo.R;
import com.example.todo.todo.helper.Content;

import java.util.ArrayList;

/**
 * Created by amanj on 7/11/2017.
 */

public class CompletedClassAdapter extends RecyclerView.Adapter<CompletedClassAdapter.CompletedClassViewHolder> {

    public ArrayList<Content> contentlist;
    CompletedTaskActivity completedTaskActivity;
    //RemainingTasksActivity remainingTasksActivity;
    Context context;
    public CompletedClassAdapter(ArrayList<Content> contentlist, Context context)
    {
        this.contentlist=contentlist;
        this.context=context;
        completedTaskActivity=(CompletedTaskActivity) context;

    }
    @Override
    public CompletedClassAdapter.CompletedClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType)

    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_todo,parent,false);
        CompletedClassAdapter.CompletedClassViewHolder completedClassViewHolder =  new CompletedClassAdapter.CompletedClassViewHolder(view,completedTaskActivity,contentlist);
        return completedClassViewHolder;
    }
    @Override
    public void onBindViewHolder(CompletedClassAdapter.CompletedClassViewHolder holder, int position)
    {
        Content content=contentlist.get(position);
        holder.TextTitle.setText(content.getTitle());

        holder.TextDate.setText(content.getDuedate());
        holder.TextTime.setText(content.getDuetime());
    }
    @Override    public int getItemCount() {
        return contentlist.size();
    }


    public static class CompletedClassViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener
    {
        public ArrayList<Content> contentlist=new ArrayList<Content>();
        public Context context;
        //RemainingTasksActivity remainingTasksActivity;
        CompletedTaskActivity completedTaskActivity;
        TextView TextTitle,TextDate,TextTime;
        CardView cardView;


        public CompletedClassViewHolder(View itemView,CompletedTaskActivity completedTaskActivity,ArrayList<Content> list) {
            super(itemView);

            TextTitle = (TextView) itemView.findViewById(R.id.TextTitle);
            TextDate = (TextView) itemView.findViewById(R.id.TextDate);
            TextTime = (TextView) itemView.findViewById(R.id.TextTime);
            this.completedTaskActivity=completedTaskActivity;
            context=completedTaskActivity;
            contentlist=list;
            cardView=(CardView)itemView.findViewById(R.id.cardview);
            cardView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (CompletedTaskActivity.longClick == false) {
                int position = getAdapterPosition();
                Content data = contentlist.get(position);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle(data.getTitle());
                alertDialog.setMessage(data.getDescription() + "\n" + data.duedate + "\n" + data.duetime);
                alertDialog.show();
            }
            else
            {
                int position = getAdapterPosition();
                Content data = contentlist.get(position);
                completedTaskActivity.prepareSelection(v,getAdapterPosition(),data.getTitle());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            CompletedTaskActivity.count++;
            if(CompletedTaskActivity.count==1) {
                cardView.setBackgroundColor(Color.GRAY);
                int position = getAdapterPosition();
                Content data = contentlist.get(position);
                String title = data.getTitle();
                completedTaskActivity.funcCall(title);
                return true;
            }
            else {
                completedTaskActivity.showerror();
                return false;
            }
        }
    }

    }
