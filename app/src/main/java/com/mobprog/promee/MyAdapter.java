package com.mobprog.promee;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mobprog.promee.R;
import com.mobprog.promee.model.TaskDataClass;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<TaskDataClass> dataList;

    public MyAdapter(Context context, List<TaskDataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.recTitle.setText(dataList.get(position).getTname());
        holder.recDate.setText(dataList.get(position).getTdate());


        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("Task name", dataList.get(holder.getAdapterPosition()).getTname());
                intent.putExtra("Date", dataList.get(holder.getAdapterPosition()).getTdate());
                intent.putExtra("Start Date", dataList.get(holder.getAdapterPosition()).getTstart());
                intent.putExtra("End Time", dataList.get(holder.getAdapterPosition()).getTend());
                intent.putExtra("Notes", dataList.get(holder.getAdapterPosition()).getTnote());
                intent.putExtra("Key", dataList.get(holder.getAdapterPosition()).getKey());

                context .startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return dataList.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{
    TextView recTitle, recDate, recStartTime, recEndTime, recNotes;
    CardView recCard;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        recCard = itemView.findViewById(R.id.recCard);
        recDate = itemView.findViewById(R.id.recDate);
        recStartTime = itemView.findViewById(R.id.startTime);
        recEndTime = itemView.findViewById(R.id.endTime);
        recNotes = itemView.findViewById(R.id.taskNote);
        recTitle = itemView.findViewById(R.id.recTitle);
    }
}