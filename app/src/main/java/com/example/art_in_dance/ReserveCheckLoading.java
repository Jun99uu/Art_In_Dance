package com.example.art_in_dance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class ReserveCheckLoading extends AppCompatActivity {
    private Intent intent;
    String CONDATE;
    String CONTIME;
    String PERSON;
    String SUBJECT;
    String TEACHER;
    String CONTENT;
    String CURRENT;
    String ID;
    String Name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_check_loading);

        intent = getIntent();

        ID = intent.getStringExtra("UserID");
        Name = intent.getStringExtra("UserName");
        CONDATE = intent.getStringExtra("CONDATE");

        TextView page_name = findViewById(R.id.class_check_name);
        page_name.setText(Name);

        ImageView gif_image = (ImageView)findViewById(R.id.loadingimage);
        Glide.with(this).load(R.raw.giphy).into(gif_image);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent xintent = new Intent(ReserveCheckLoading.this, UserReservedClass.class);


                //예약정보 구하기
                Response.Listener<String> responseListenersecond = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObjectsecond = new JSONObject(response);
                            boolean success2 = jsonObjectsecond.getBoolean("success");

                            if(success2){
                                CONDATE = jsonObjectsecond.getString("CONDATE");
                                CONTIME = jsonObjectsecond.getString("CONTIME");
                                PERSON = jsonObjectsecond.getString("PERSON");
                                SUBJECT = jsonObjectsecond.getString("SUBJECT");
                                TEACHER = jsonObjectsecond.getString("TEACHER");
                                CONTENT = jsonObjectsecond.getString("CONTENT");
                                CURRENT = jsonObjectsecond.getString("CURRENT");
                                System.out.println(CONTIME+"\n"+PERSON+"\n"+SUBJECT+"\n"+TEACHER+"\n"+CONTENT+"\n"+CURRENT);
                                xintent.putExtra("UserName", Name);
                                xintent.putExtra("UserID", ID);
                                xintent.putExtra("CONDATE", CONDATE);
                                xintent.putExtra("CONTIME", CONTIME);
                                xintent.putExtra("PERSON", PERSON);
                                xintent.putExtra("SUBJECT", SUBJECT);
                                xintent.putExtra("TEACHER", TEACHER);
                                xintent.putExtra("CONTENT", CONTENT);
                                xintent.putExtra("CURRENT", CURRENT);
                                startActivity(xintent);
                            }else{
                                Toast.makeText( getApplicationContext(), "Error.", Toast.LENGTH_SHORT ).show();
                                return;
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                ClassInfoRequest classInfoRequest = new ClassInfoRequest(CONDATE, responseListenersecond);
                RequestQueue queuesecond = Volley.newRequestQueue(ReserveCheckLoading.this);
                queuesecond.add(classInfoRequest);
            }
        }, 4000);

    }
}