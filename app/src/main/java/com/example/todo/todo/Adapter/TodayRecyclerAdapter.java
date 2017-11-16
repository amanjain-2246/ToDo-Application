package com.example.todo.todo.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.todo.todo.helper.Content;
import com.example.todo.todo.R;

import java.util.ArrayList;

public class TodayRecyclerAdapter extends RecyclerView.Adapter<TodayRecyclerAdapter.TodayRecyclerViewHolder>
{
    public ArrayList<Content> contentlist;

   // MainActivity mainActivity;
    //Context context;


    public TodayRecyclerAdapter(ArrayList<Content> contentlist)
    {
        this.contentlist=contentlist;

    }

    @Override
    public TodayRecyclerAdapter.TodayRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)

    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_todo,parent,false);
        TodayRecyclerAdapter.TodayRecyclerViewHolder recyclerViewHolder =  new TodayRecyclerAdapter.TodayRecyclerViewHolder(view,contentlist);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(TodayRecyclerAdapter.TodayRecyclerViewHolder holder, int position)
    {
        Content content=contentlist.get(position);
        holder.TextTitle.setText(content.getTitle());

        holder.TextDate.setText(content.getDuedate());
        holder.TextTime.setText(content.getDuetime());
    }

    @Override    public int getItemCount() {
        return contentlist.size();
    }

    public static class TodayRecyclerViewHolder extends RecyclerView.ViewHolder
    {
        public ArrayList<Content> contentlist=new ArrayList<Content>();
        TextView TextTitle,TextDate,TextTime;
        CardView cardView;

        public TodayRecyclerViewHolder(View itemView,ArrayList<Content> list) {
            super(itemView);

            TextTitle = (TextView) itemView.findViewById(R.id.TextTitle);
            TextDate = (TextView) itemView.findViewById(R.id.TextDate);
            TextTime = (TextView) itemView.findViewById(R.id.TextTime);
            contentlist=list;
            cardView=(CardView)itemView.findViewById(R.id.cardview);

        }


    }


}