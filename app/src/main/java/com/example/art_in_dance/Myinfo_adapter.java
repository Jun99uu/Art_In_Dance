package com.example.art_in_dance;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.database.sqlite.SQLiteDatabase;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Myinfo_adapter extends RecyclerView.Adapter<Myinfo_adapter.ViewHolder> {

    private ArrayList<String> mData = null;
    ArrayList<Integer> daydata = null;
    Context context;

    //아이템 뷰 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView yet;
        TextView not;
        TextView done;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //뷰 객체 참조
            yet = itemView.findViewById(R.id.info_yet);
            not = itemView.findViewById(R.id.info_not);
            done = itemView.findViewById(R.id.info_done);
        }
    }

    Myinfo_adapter(Context cont, ArrayList<String> list, ArrayList<Integer> day){
        mData = list;
        daydata = day;
        context = cont;
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
    public void onBindViewHolder(@NonNull Myinfo_adapter.ViewHolder holder, int position) { //여기서 색 지정해줄거임
        //position에 해당하는 데이터를 viewholder가 관리하는 view에 바인딩
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf_d = new SimpleDateFormat("dd");
        int today = Integer.parseInt(sdf_d.format(date));

        String text = mData.get(position);
        int position_data = Integer.parseInt(text);
        if(position_data <= today){
            for(int i = 0; i < daydata.size(); i++){ //들어온 값이 출석 배열에 있는 값과 같다면?
                if(position_data == daydata.get(i)){
                    holder.yet.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.art_green)));
                    break;
                }else{
                    holder.yet.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.art_red)));
                }
            }
        }
        holder.yet.setText(text);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
