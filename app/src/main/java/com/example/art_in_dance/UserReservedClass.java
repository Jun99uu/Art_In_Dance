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

public class UserReservedClass extends AppCompatActivity {
    String UserName;
    String UserID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reserved_class);

        ImageView mainimage = findViewById(R.id.mad);
        Glide.with(this).load(R.raw.mad).into(mainimage);

        Button cancel = findViewById(R.id.cancelclass);

        Intent intent = getIntent();
        String start = "0";
        String ID = intent.getStringExtra("UserID");
        String Name = intent.getStringExtra("UserName");
        String CONDATE = intent.getStringExtra("CONDATE");
        String CONTIME = intent.getStringExtra("CONTIME");
        String PERSON = intent.getStringExtra("PERSON");
        String SUBJECT = intent.getStringExtra("SUBJECT");
        String CURRENT = intent.getStringExtra("CURRENT");
        String TEACHER = intent.getStringExtra("TEACHER");
        String CONTENT = intent.getStringExtra("CONTENT");
        String info = CONDATE.substring(0,4) + "." + CONDATE.substring(4,6) + "." + CONDATE.substring(6,8) + " " + CONDATE.substring(8,10) + ":" + CONDATE.substring(10,12) + "~\n" + CONTIME + "분 수업\n최대인원 : " + PERSON + "명\n현재인원 : " + CURRENT + "명";
        String change = Integer.toString(Integer.parseInt(CURRENT) - 1);

        UserName = Name;
        UserID = ID;

        TextView explain = findViewById(R.id.explain2);
        explain.setText(Name + "님이 예약하신 수업은...");

        TextView title = findViewById(R.id.info_title);
        TextView person = findViewById(R.id.info_person2);
        TextView content = findViewById(R.id.info_content2);

        if(CONDATE.equals(start)){
            title.setText("아직 예약하신 수업이 없네요!\n예약 먼저 해주세요!");
            person.setText("수업을 예약하면\n빠르고 간편하게\n운동을 즐길 수 있어요.");
            content.setText("계정당 수업은 1개만 예약할 수 있어요.\n다른수업을 예약하고싶다면, 예약을 취소하고 다시 예약해주세요.");
        }else{
            title.setText(TEACHER + "선생님의 \n♥" + SUBJECT + "♥");
            person.setText(info);
            content.setText(CONTENT);
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                DeleteClassRequest deleteClassRequest = new DeleteClassRequest(ID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(UserReservedClass.this);
                queue.add(deleteClassRequest);

                Response.Listener<String> responseListenersecond = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObjectsecond = new JSONObject(response);
                            boolean success2 = jsonObjectsecond.getBoolean("success");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                MinusPersonRequest minusPersonRequest = new MinusPersonRequest(change, CONDATE, responseListenersecond);
                RequestQueue queuesecond = Volley.newRequestQueue(UserReservedClass.this);
                queuesecond.add(minusPersonRequest);

                Toast.makeText(getApplicationContext(),String.format("예약한 수업이 취소되었습니다.\n 새로고침해주세요."), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("UserName", UserName);
        intent.putExtra("UserID", UserID);
        startActivity(intent);
    }
}