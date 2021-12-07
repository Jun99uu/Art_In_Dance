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

public class ClassReserverDecide extends AppCompatActivity {
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
        setContentView(R.layout.activity_class_reserver_decide);
        intent = getIntent();
        mdate = intent.getStringExtra("mdate");
        mtime = intent.getStringExtra("mtime");
        mperson = intent.getStringExtra("mperson");
        msubject = intent.getStringExtra("msubject");
        mteacher = intent.getStringExtra("mteacher");
        mcontent = intent.getStringExtra("mcontent");
        mcurrent = intent.getStringExtra("mcurrent");
        ID = intent.getStringExtra("mID");

        TextView main = findViewById(R.id.teacher_name);
        TextView info = findViewById(R.id.person);
        TextView content = findViewById(R.id.content);

        main.setText(mteacher + "선생님의 \n♥" + msubject + "♥");
        info.setText(mdate.substring(0,4) + "." + mdate.substring(4,6) + "." + mdate.substring(6,8) + " " + mdate.substring(8,10) + ":" + mdate.substring(10,12) + "~\n" + mtime + "분 수업\n최대인원 : " + mperson + "명\n현재인원 : " + mcurrent + "명");
        content.setText(mcontent);

        ImageView gif_image = (ImageView)findViewById(R.id.dance);
        Glide.with(this).load(R.raw.dance).into(gif_image);

        Button reserve = findViewById(R.id.reserve);
        Button cancel = findViewById(R.id.cancel);

        Response.Listener<String> responseListenerfirst = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObjectfirst = new JSONObject(response);
                    boolean success1 = jsonObjectfirst.getBoolean("success");

                    if(success1){
                        UserReservedClass = jsonObjectfirst.getString("Class");
                    }else{
                        Toast.makeText( getApplicationContext(), "Error.", Toast.LENGTH_SHORT ).show();
                        return;
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };
        UserClassReaderRequest classReaderRequest = new UserClassReaderRequest(ID, responseListenerfirst);
        RequestQueue queuefirst = Volley.newRequestQueue(ClassReserverDecide.this);
        queuefirst.add(classReaderRequest);

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(UserReservedClass.equals(mdate)){
                    Toast.makeText(getApplicationContext(),String.format("동일한 수업을 이미 예약하셨습니다."), Toast.LENGTH_SHORT).show();
                    return;
                }else if(!UserReservedClass.equals(start)){
                    Toast.makeText(getApplicationContext(),String.format("이미 예약하신 수업이 있습니다."), Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    if(Integer.parseInt(mcurrent) < Integer.parseInt(mperson)){
                        String UpdateCurrent = Integer.toString(Integer.parseInt(mcurrent)+1);

                        Response.Listener<String> responseListenertwo = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try{
                                    JSONObject jsonObjecttwo = new JSONObject(response);
                                    boolean success2 = jsonObjecttwo.getBoolean("success");
                                }catch(JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        };
                        ClassReserverDecideRequestTwo decideRequestTwo = new ClassReserverDecideRequestTwo(mdate, UpdateCurrent, responseListenertwo);
                        RequestQueue queuetwo = Volley.newRequestQueue(ClassReserverDecide.this);
                        queuetwo.add(decideRequestTwo);


                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try{
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                }catch(JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        };
                        ClassReserverDecideRequest decideRequest = new ClassReserverDecideRequest(mdate, ID, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(ClassReserverDecide.this);
                        queue.add(decideRequest);

                        Toast.makeText(getApplicationContext(),String.format("예약이 완료되었습니다!"), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),String.format("이미 예약이 가득 찬 수업입니다. T^T"), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClassReserverDecide.super.onBackPressed();
            }
        });
    }
}