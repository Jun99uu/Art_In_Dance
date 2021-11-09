package com.example.art_in_dance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        TextView info_info = findViewById(R.id.info_info);
        Intent intent = getIntent();
        String name = intent.getStringExtra("UserName");
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf_y = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf_m = new SimpleDateFormat("MM");
        SimpleDateFormat sdf_d = new SimpleDateFormat("dd");
        String year = sdf_y.format(date);
        String month = sdf_m.format(date);
        String day = sdf_d.format(date);
        int iyear = Integer.parseInt(year);
        int imonth = Integer.parseInt(month);
        int iday = Integer.parseInt(day);

        String title = name + "님의\n" + month + "출석부";

        info_info.setText(title);

        ImageView dancing = (ImageView)findViewById(R.id.walking_gif);
        Glide.with(this).load(R.raw.dancing).into(dancing);

        ArrayList<String> list = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.set(iyear, imonth - 1, iday);

        for(int i=1; i<=cal.getActualMaximum(cal.DAY_OF_MONTH); i++){
            list.add(String.format("%d", i));
        }

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 7));

        Myinfo_adapter adapter = new Myinfo_adapter(list);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new myItemDecoration());
    }

    public class myItemDecoration extends RecyclerView.ItemDecoration{
        private final int padding = 40;

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state){
            super.getItemOffsets(outRect, view, parent, state);
            outRect.top = padding;
        }
    }
}