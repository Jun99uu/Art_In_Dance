package com.example.art_in_dance;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ClassReserver_adapter extends RecyclerView.Adapter<ClassReserver_adapter.ViewHolder> {

    private ArrayList<ClassReserver_item> items = null;
    private Context context;

    ClassReserver_adapter(ArrayList<ClassReserver_item> items, Context context){
        this.items = items;
        this.context = context;
    }

    //아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.class_item, parent, false);
        ClassReserver_adapter.ViewHolder vh = new ClassReserver_adapter.ViewHolder(view);

        return vh;
    }

    //position에 해당하는 데이터를 뷰홀더의 아이템 뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClassReserver_adapter.ViewHolder vh = (ClassReserver_adapter.ViewHolder)holder;
        ClassReserver_item item = items.get(position);
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
                String mID = item.getID();

                Intent intent;
                intent = new Intent(context, ClassReserverDecide.class);
                intent.putExtra("mdate", mdate);
                intent.putExtra("mtime", mtime);
                intent.putExtra("mperson", mperson);
                intent.putExtra("msubject", msubject);
                intent.putExtra("mteacher", mteacher);
                intent.putExtra("mcontent", mcontent);
                intent.putExtra("mcurrent", mcurrent);
                intent.putExtra("mID", mID);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //아이템뷰를 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            //뷰 객체에 대한 참조
            item = itemView.findViewById(R.id.class_item);
        }
    }
}
