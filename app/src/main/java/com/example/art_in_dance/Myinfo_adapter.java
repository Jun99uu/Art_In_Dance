package com.example.art_in_dance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Myinfo_adapter extends RecyclerView.Adapter<Myinfo_adapter.ViewHolder> {

    private ArrayList<String> mData = null;

    //아이템 뷰 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView yet;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //뷰 객체 참조
            yet = itemView.findViewById(R.id.info_yet);
        }
    }

    Myinfo_adapter(ArrayList<String> list){
        mData = list;
    }

    @NonNull
    @Override
    public Myinfo_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.myinfo_yet, parent, false);
        Myinfo_adapter.ViewHolder vh = new Myinfo_adapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Myinfo_adapter.ViewHolder holder, int position) {
        String text = mData.get(position);
        holder.yet.setText(text);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
