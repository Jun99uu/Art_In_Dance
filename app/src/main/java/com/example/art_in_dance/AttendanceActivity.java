package com.example.art_in_dance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AttendanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        TextView set_name = (TextView)findViewById(R.id.atd_name);
        Intent intent = getIntent();
        String name = intent.getStringExtra("UserName");
        set_name.setText(name);

        Intent two_intent = new Intent(AttendanceActivity.this, AttendanceListMake.class);
        two_intent.putExtra("UserName", name);
        startActivity(two_intent); //로딩 완료

        AttendanceTableMaker tableMaker;

        Intent three_intent = new Intent(AttendanceActivity.this, AttendanceListMake.class);
        three_intent.putExtra("UserName", name);

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

        int version = Integer.parseInt(month); //그 달이 해당 버젼
        int today = Integer.parseInt(day);

        Button atd_btn = findViewById(R.id.atd_btn);

        //db생성 및 버전 관리
        tableMaker = new AttendanceTableMaker(this, version);

        //출석 db 삽입
        atd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int atd_check = 1; //출석 여부 확인(1값 들어갈 시 출석)

                SQLiteDatabase db;
                String insertquery;
                int result = tableMaker.getNumber(); //출석했다면 가장최근 출석횟수, 안했다면 0 불러옴

                if(result > 0){
                    int atd_number = result + 1;
                    insertquery = "INSERT INTO AttendanceTABLE VALUES(" + today + "," + atd_check + "," + atd_number + ");";
                    db = tableMaker.getWritableDatabase();
                    db.execSQL(insertquery);
                    Toast.makeText(getApplicationContext(), "오늘도 화이팅!", Toast.LENGTH_SHORT).show();
                    startActivity(three_intent);
                }else{
                    int atd_number = 1;
                    insertquery = "INSERT INTO AttendanceTABLE VALUES(" + today + "," + atd_check + "," + atd_number + ");";
                    db = tableMaker.getWritableDatabase();
                    db.execSQL(insertquery);
                    Toast.makeText(getApplicationContext(), "이번 달도 화이팅이에요!", Toast.LENGTH_SHORT).show();
                    startActivity(three_intent);
                }
            }
        });

    }
}