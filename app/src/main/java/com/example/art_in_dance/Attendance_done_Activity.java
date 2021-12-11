package com.example.art_in_dance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Attendance_done_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_done2);

        Intent intent = getIntent();
        String name = intent.getStringExtra("UserName");

        TextView set_name = (TextView)findViewById(R.id.atd_name);
        set_name.setText(name);

        TextView set_date = (TextView)findViewById(R.id.today_date);
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf_y = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf_m = new SimpleDateFormat("MM");
        SimpleDateFormat sdf_d = new SimpleDateFormat("dd");
        String year = sdf_y.format(date);
        String month = sdf_m.format(date);
        String day = sdf_d.format(date);
        String today_date = (year + "년 " + month + "월 " + day + "일");
        set_date.setText(today_date); //날짜값 받아오기 완료

    }

    @Override
    public void onBackPressed(){
        Intent getintent = getIntent();
        String name = getintent.getStringExtra("UserName");
        String rank = getintent.getStringExtra("rank");
        String atd = getintent.getStringExtra("atd");
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("UserName", name);
        intent.putExtra("rank", rank);
        intent.putExtra("atd", atd);
        startActivity(intent);
    }
}