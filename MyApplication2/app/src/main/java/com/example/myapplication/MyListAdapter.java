package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
    private List<String> listdata;
    Context context;
    // RecyclerView recyclerView;
    public MyListAdapter(Context context, List<String> listdata) {
        this.listdata = listdata;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
//        final MyListData myListData = listdata.get(position);
        holder.textView.setText(listdata.get(position));
        holder.imageView.setImageResource(R.drawable.coronavirus);
        holder.LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Hot news",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context,NewsDetails.class);
                intent.putExtra("description",listdata.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public LinearLayout LinearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.textView = (TextView) itemView.findViewById(R.id.textView);
            LinearLayout = (LinearLayout)itemView.findViewById(R.id.LinearLayout);
        }
    }
}