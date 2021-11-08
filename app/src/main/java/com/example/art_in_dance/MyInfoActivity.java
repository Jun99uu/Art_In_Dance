package com.example.art_in_dance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        SimpleDateFormat sdf_m = new SimpleDateFormat("MM");
        String month = sdf_m.format(date);
        String title = name + "님의\n" + month + "출석부";

        info_info.setText(title);

        ImageView dancing = (ImageView)findViewById(R.id.walking_gif);
        Glide.with(this).load(R.raw.dancing).into(dancing);

        ArrayList<String> list = new ArrayList<>();
        for(int i=0; i<100; i++){
            list.add(String.format("TEXT %d", i));
        }

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Myinfo_adapter adapter = new Myinfo_adapter(list);
        recyclerView.setAdapter(adapter);
    }
}