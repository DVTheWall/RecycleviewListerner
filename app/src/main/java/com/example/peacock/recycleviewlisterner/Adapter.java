package com.example.peacock.recycleviewlisterner;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by peacock on 26/5/17.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {


    Context context;
    private ArrayList<User> list = null;

    public Adapter(Context context, ArrayList<User> list) {

        this.context = context;
        this.list = list; // All List are present in list

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        User obj = list.get(position); // return  Bean class..

        holder.usernmae.setText(obj.getName());
        holder.content.setText(obj.getContent());
        holder.time.setText(obj.getTime());

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView usernmae, content, time;

        private SparseBooleanArray selectedItems = new SparseBooleanArray();

        public MyViewHolder(View itemView) {
            super(itemView);


            itemView.setOnClickListener(this);
            usernmae = (TextView) itemView.findViewById(R.id.user_name);
            content = (TextView) itemView.findViewById(R.id.content);
            time = (TextView) itemView.findViewById(R.id.time);

        }


        @Override
        public void onClick(View v) {
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedItems.get(getAdapterPosition(), false)) {
                        selectedItems.delete(getAdapterPosition());
                        v.setSelected(false);
                    } else {
                        selectedItems.put(getAdapterPosition(), true);
                        v.setSelected(true);
                    }
                }
            });

        }
    }
}
