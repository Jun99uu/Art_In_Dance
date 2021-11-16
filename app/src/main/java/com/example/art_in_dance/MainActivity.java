package com.example.art_in_dance; //로그인 페이지

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private long backKeyPressedTime = 0;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView set_name = (TextView)findViewById(R.id.name); //유저이름 name
        Button attendance_btn = findViewById(R.id.attendance_btn);
        Button myinfo_btn = findViewById(R.id.my_info_btn);
        Button rank_btn = findViewById(R.id.rank_btn);

        Intent intent = getIntent();

        String name = intent.getStringExtra("UserName");
        set_name.setText(name);

        attendance_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent atdintent = new Intent(MainActivity.this, AttendanceActivity.class);
                atdintent.putExtra("UserName", name);
                startActivity(atdintent);
            }
        });

        myinfo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent infointent = new Intent(MainActivity.this, MyInfoActivity.class);
                infointent.putExtra("UserName", name);
                startActivity(infointent);
            }
        });

        rank_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent rankintent = new Intent(MainActivity.this, ATDRank.class);
                rankintent.putExtra("UserName", name);
                startActivity(rankintent);
            }
        });

    }

    @Override
    public void onBackPressed(){
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            moveTaskToBack(true); //태스크 백그라운드로 이동
            finishAndRemoveTask(); // 액티비티 종료 + 태스크 리스트에서 지우기
            android.os.Process.killProcess(android.os.Process.myPid());
            toast.cancel();
        }
    }
}