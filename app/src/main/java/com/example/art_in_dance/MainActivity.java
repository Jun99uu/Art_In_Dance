package com.example.art_in_dance; //로그인 페이지

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private long backKeyPressedTime = 0;
    private Toast toast;
    String CONDATE;
    SwipeRefreshLayout swipeRefreshLayout;
    String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        TextView set_name = (TextView)findViewById(R.id.name); //유저이름 name
        Button attendance_btn = findViewById(R.id.attendance_btn);
        Button myinfo_btn = findViewById(R.id.my_info_btn);
        Button rank_btn = findViewById(R.id.rank_btn);
        Button class_check = findViewById(R.id.class_check);
        Button reserved_class = findViewById(R.id.reservation);

        Intent intent = getIntent();

        String name = intent.getStringExtra("UserName");
        set_name.setText(name);
        String ID = intent.getStringExtra("UserID");
        UserID = ID;

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

        class_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ID.equals("admin")){
                    Intent class_make_intent = new Intent(MainActivity.this, ClassMaker.class);
                    class_make_intent.putExtra("UserName", name);
                    startActivity(class_make_intent);
                }else{
                    Intent class_reserve_intent = new Intent(MainActivity.this, ClassReserver.class);
                    class_reserve_intent.putExtra("UserName", name);
                    class_reserve_intent.putExtra("UserID", ID);
                    startActivity(class_reserve_intent);
                }
            }
        });

        //id에 따른 예약날짜구하기
        Response.Listener<String> responseListenerfirst = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObjectfirst = new JSONObject(response);
                    boolean success1 = jsonObjectfirst.getBoolean("success");

                    if(success1){
                        CONDATE = jsonObjectfirst.getString("Class");
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
        RequestQueue queuefirst = Volley.newRequestQueue(MainActivity.this);
        queuefirst.add(classReaderRequest);

        reserved_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ID.equals("admin")){
                    Intent user_check_intent = new Intent(MainActivity.this, ClassMaker.class);
                    user_check_intent.putExtra("UserName", name);
                    startActivity(user_check_intent);
                }else{Intent class_check_intent = new Intent(MainActivity.this, ReserveCheckLoading.class);
                    if(CONDATE.equals("0")) {
                        Toast.makeText(getApplicationContext(), "아직 예약한 수업이 없어요.\n수업을 먼저 예약해주세요!\n만약 예약을 하셨다면, 새로고침해주세요.", Toast.LENGTH_SHORT).show();
                    }else{
                        class_check_intent.putExtra("UserName", name);
                        class_check_intent.putExtra("UserID", ID);
                        class_check_intent.putExtra("CONDATE", CONDATE);
                        startActivity(class_check_intent);
                    }
                }
            }
        });


    }

    @Override
    public void onRefresh(){
        updateLayoutView();
        //새로 고침 완
        swipeRefreshLayout.setRefreshing(false);
    }

    public void updateLayoutView(){
        Response.Listener<String> responseListenerfirst = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObjectfirst = new JSONObject(response);
                    boolean success1 = jsonObjectfirst.getBoolean("success");

                    if(success1){
                        CONDATE = jsonObjectfirst.getString("Class");
                    }else{
                        Toast.makeText( getApplicationContext(), "Error.", Toast.LENGTH_SHORT ).show();
                        return;
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };
        UserClassReaderRequest classReaderRequest = new UserClassReaderRequest(UserID, responseListenerfirst);
        RequestQueue queuefirst = Volley.newRequestQueue(MainActivity.this);
        queuefirst.add(classReaderRequest);
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