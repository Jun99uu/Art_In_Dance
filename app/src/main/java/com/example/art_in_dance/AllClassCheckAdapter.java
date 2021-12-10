package com.example.art_in_dance;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AllClassCheckAdapter extends RecyclerView.Adapter<AllClassCheckAdapter.ViewHolder> {

    private ArrayList<AllClassCheckItem> items = null;
    private Context context;

    AllClassCheckAdapter(ArrayList<AllClassCheckItem> items, Context context){
        this.items = items;
        this.context = context;
    }

    //아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.all_class_item, parent, false);
        AllClassCheckAdapter.ViewHolder vh = new AllClassCheckAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AllClassCheckAdapter.ViewHolder vh = (AllClassCheckAdapter.ViewHolder)holder;
        AllClassCheckItem item = items.get(position);
        vh.item.setText(item.getinfo());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mdate = item.getconDate();
                String mtime = item.getTime();
                String mperson = item.getPerson();
                String msubject = item.getSubject();
                String mteacher = item.getTeacher();
                String mcontent = item.getContent();
                String mcurrent = item.getCurrent();

                Intent intent;
                intent = new Intent(context, AdminClassInfo.class);
                intent.putExtra("mdate", mdate);
                intent.putExtra("mtime", mtime);
                intent.putExtra("mperson", mperson);
                intent.putExtra("msubject", msubject);
                intent.putExtra("mteacher", mteacher);
                intent.putExtra("mcontent", mcontent);
                intent.putExtra("mcurrent", mcurrent);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //아이템뷰를 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.all_class_item);
        }
    }
}
