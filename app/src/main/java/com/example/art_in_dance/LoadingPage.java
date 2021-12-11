package com.example.art_in_dance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LoadingPage extends AppCompatActivity {

    ImageView loading;
    Intent intent;
    String UserID, UserName;
    String atd, rank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);

        Intent xintent = new Intent(this, MainActivity.class);

        Handler handler = new Handler();

        intent = getIntent();
        UserName = intent.getStringExtra("UserName");
        UserID = intent.getStringExtra("UserID");
        xintent.putExtra("UserName", UserName);
        xintent.putExtra("UserID",UserID);

        loading = findViewById(R.id.startloading);
        Glide.with(this).load(R.raw.loading).into(loading);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
                String day = dateFormat.format(date);
                if(day.equals("01")){
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
                    RequestQueue requestQueue2 = Volley.newRequestQueue(LoadingPage.this);
                    requestQueue2.add(jsonArrayRequest2);

                    rank = "0";
                    atd = "0";
                    xintent.putExtra("rank", rank);
                    xintent.putExtra("atd", atd);
                    startActivity(xintent);
                }else{
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
                                        xintent.putExtra("rank", rank);
                                        xintent.putExtra("atd", atd);
                                        System.out.println(rank + atd);
                                        startActivity(xintent);
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
                    RequestQueue requestQueue = Volley.newRequestQueue(LoadingPage.this);
                    requestQueue.add(jsonArrayRequest);
                }
            }
        }, 3000);
    }
}