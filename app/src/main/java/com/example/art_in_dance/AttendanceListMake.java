package com.example.art_in_dance;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.SimpleDateFormat;
import java.util.Date;


public class AttendanceListMake extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_list_make);

        Intent intent = getIntent();
        String name = intent.getStringExtra("UserName");
        TextView set_name = (TextView)findViewById(R.id.atd_name);
        set_name.setText(name);

        ImageView gif_image = (ImageView)findViewById(R.id.gif_image);
        Glide.with(this).load(R.raw.giphy).into(gif_image);


        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf_m = new SimpleDateFormat("MM");
        SimpleDateFormat sdf_d = new SimpleDateFormat("dd");
        String month = sdf_m.format(date);
        String day = sdf_d.format(date);
        int version = Integer.parseInt(month);
        int today = Integer.parseInt(day);

        AttendanceTableMaker tableMaker = new AttendanceTableMaker(this, version);
        int result = tableMaker.getResult();

        if (today == result){
            Loading();
        }else{
            startLoading();
        }



    }

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 3000);
    }

    private void Loading() {
        Intent intent = getIntent();
        String name = intent.getStringExtra("UserName");
        Intent xintent = new Intent(AttendanceListMake.this, Attendance_done_Activity.class);
        xintent.putExtra("UserName", name);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(xintent);
            }
        }, 3000);
    }
}