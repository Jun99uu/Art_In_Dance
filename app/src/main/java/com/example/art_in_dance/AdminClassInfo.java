package com.example.art_in_dance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class AdminClassInfo extends AppCompatActivity {
    private Intent intent;
    String mdate;
    String mtime;
    String mperson;
    String msubject;
    String mteacher;
    String mcontent;
    String mcurrent;
    String ID;

    String UserReservedClass;
    String start = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_class_info);

        intent = getIntent();
        mdate = intent.getStringExtra("mdate");
        mtime = intent.getStringExtra("mtime");
        mperson = intent.getStringExtra("mperson");
        msubject = intent.getStringExtra("msubject");
        mteacher = intent.getStringExtra("mteacher");
        mcontent = intent.getStringExtra("mcontent");
        mcurrent = intent.getStringExtra("mcurrent");
        ID = intent.getStringExtra("mID");

        ImageView gif_image = (ImageView)findViewById(R.id.dance);
        Glide.with(this).load(R.raw.dance).into(gif_image);

        Button delete = findViewById(R.id.admin_delete);
        Button cancel = findViewById(R.id.cancel);
        Button list = findViewById(R.id.UserList);

        TextView main = findViewById(R.id.admin_main);
        TextView info = findViewById(R.id.admin_info);
        TextView content = findViewById(R.id.admin_content);

        main.setText(mteacher + "선생님의 \n♥" + msubject + "♥");
        info.setText(mdate.substring(0,4) + "." + mdate.substring(4,6) + "." + mdate.substring(6,8) + " " + mdate.substring(8,10) + ":" + mdate.substring(10,12) + "~\n" + mtime + "분 수업\n최대인원 : " + mperson + "명\n현재인원 : " + mcurrent + "명");
        content.setText(mcontent);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListenertwo = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObjecttwo = new JSONObject(response);
                            boolean success2 = jsonObjecttwo.getBoolean("success");
                            Toast.makeText(getApplicationContext(),String.format("수업이 삭제되었습니다."), Toast.LENGTH_SHORT).show();
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                AdminClassDeleteRequest decideRequestTwo = new AdminClassDeleteRequest(mdate, responseListenertwo);
                RequestQueue queuetwo = Volley.newRequestQueue(AdminClassInfo.this);
                queuetwo.add(decideRequestTwo);
            }
        });

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminClassInfo.super.onBackPressed();
            }
        });
    }
}