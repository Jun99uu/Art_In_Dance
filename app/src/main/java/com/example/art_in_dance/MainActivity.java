package com.example.art_in_dance; //로그인 페이지

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private long backKeyPressedTime = 0;
    private Toast toast;
    String CONDATE;
    SwipeRefreshLayout swipeRefreshLayout;
    String UserID, UserName;
    Context context;
    String rank, atd;
    TextView sumatd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;

        autoDelete();

        swipeRefreshLayout = findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        TextView set_name = (TextView)findViewById(R.id.name); //유저이름 name
        Button attendance_btn = findViewById(R.id.attendance_btn);
        Button myinfo_btn = findViewById(R.id.my_info_btn);
        Button rank_btn = findViewById(R.id.rank_btn);
        Button class_check = findViewById(R.id.class_check);
        Button reserved_class = findViewById(R.id.reservation);

        sumatd = (TextView)findViewById(R.id.atd_info);

        Intent intent = getIntent();

        String name = intent.getStringExtra("UserName");
        UserName = name;
        set_name.setText(name+"님,");
        String ID = intent.getStringExtra("UserID");
        UserID = ID;
        rank = intent.getStringExtra("rank");
        atd = intent.getStringExtra("atd");
        sumatd.setText("이번 달에 " + atd + "회 출석하셨고,\n현재 " + rank + "등이시네요!");
        if(rank == null && atd == null){
            rank = intent.getStringExtra("rank");
            atd = intent.getStringExtra("atd");
            sumatd.setText("이번 달에 " + atd + "회 출석하셨고,\n현재 " + rank + "등이시네요!");
        }

        attendance_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent atdintent = new Intent(MainActivity.this, AttendanceActivity.class);
                atdintent.putExtra("UserName", name);
                atdintent.putExtra("rank", rank);
                atdintent.putExtra("atd", atd);
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
                    Intent user_check_intent = new Intent(MainActivity.this, AllClassCheck.class);
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

        autoDelete();

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
        String day = dateFormat.format(date);
        if(day.equals("01")){
            resetATD();
        }else{
            getsummarize();
        }
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

    public void autoDelete(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmm");
        String currenttime = dateFormat.format(date);
        long time = Long.parseLong(currenttime);

        String Url = "http://artindance99.ivyro.net/ReadClass.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String CONDATE = jsonObject.getString("CONDATE");
                        long savedtime = Long.parseLong(CONDATE);
                        if(savedtime < time){
                            ReadUserDeleteClass(CONDATE);
                        }else{
                            continue;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),String.format("Error"), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }

    public void ReadUserDeleteClass(String CONDATE){
        Response.Listener<String> responseListenertwo = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObjecttwo = new JSONObject(response);
                    boolean success2 = jsonObjecttwo.getBoolean("success");
                    Toast.makeText(getApplicationContext(),String.format("수업 최신화가 완료되었습니다."), Toast.LENGTH_SHORT).show();
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        };
        AdminClassDeleteRequest decideRequestTwo = new AdminClassDeleteRequest(CONDATE, responseListenertwo);
        RequestQueue queuetwo = Volley.newRequestQueue(context);
        queuetwo.add(decideRequestTwo);
    }

    private void getsummarize(){
        String Url = "http://artindance99.ivyro.net/ATDGet.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        if(UserName.equals(jsonObject.getString("UserName"))){
                            rank = Integer.toString(response.length() - i);
                            atd = jsonObject.getString("ATD");
                            sumatd.setText("이번 달에 " + atd + "회 출석하셨고,\n현재 " + rank + "등이시네요!");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),String.format("Error"), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void resetATD(){
        String Url2 = "http://artindance99.ivyro.net/resetATD.php";
        JsonArrayRequest jsonArrayRequest2 = new JsonArrayRequest(Request.Method.POST, Url2, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),String.format("Error"), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue2 = Volley.newRequestQueue(this);
        requestQueue2.add(jsonArrayRequest2);

        rank = "0";
        atd = "0";
        sumatd.setText("이번 달에 " + atd + "회 출석하셨고,\n현재 " + rank + "등이시네요!");
    }

}