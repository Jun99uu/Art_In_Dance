package com.example.art_in_dance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ATDRank_adapter extends RecyclerView.Adapter<ATDRank_adapter.ViewHolder> {

    private ArrayList<ATDRank_item> items;

    ATDRank_adapter(ArrayList<ATDRank_item> items){
        this.items = items;
    }


    @NonNull
    @Override
    public ATDRank_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.rank_item,parent,false);
        ATDRank_adapter.ViewHolder vh = new ATDRank_adapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ATDRank_adapter.ViewHolder holder, int position) {
        ATDRank_adapter.ViewHolder vh = (ATDRank_adapter.ViewHolder)holder;
        ATDRank_item item = items.get(position);
        vh.item.setText(item.getname());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.rank);
        }
    }
}
