package com.example.sproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Item> items;
    private Context context;

    public MyAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item item = items.get(position);
        holder.categoryNameTextView.setText(item.getName());
        holder.categoryImageView.setImageResource(item.getImageResId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TourDetailActivity.class);
                intent.putExtra("tourName", item.getName());
                intent.putExtra("tourDescription", "Description for " + item.getName());
                intent.putExtra("tourImageResId", item.getImageResId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView categoryNameTextView;
        public ImageView categoryImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            categoryNameTextView = itemView.findViewById(R.id.tvCategoryName);
            categoryImageView = itemView.findViewById(R.id.ivCategoryPic);
        }
    }
}
