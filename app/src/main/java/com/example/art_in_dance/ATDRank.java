package com.example.art_in_dance;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ATDRank extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<ATDRank_item> items = new ArrayList<>();
    ATDRank_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atdrank);

        recyclerView = findViewById(R.id.rank_recycler);
        adapter = new ATDRank_adapter(items);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        String Url = "http://artindance99.ivyro.net/ATDGet.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                items.clear();
                adapter.notifyDataSetChanged();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String name = Integer.toString(response.length() - i) + "등 : " + jsonObject.getString("UserName");

                        items.add(0, new ATDRank_item(name));
                        adapter.notifyItemInserted(0);
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

        Intent intent = getIntent();
        String name = intent.getStringExtra("UserName");
        recyclerView.addItemDecoration(new ATDRank.myItemDecoration());

        String text = name + "님,";
        TextView rank_name = findViewById(R.id.rank_name);
        rank_name.setText(text);

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf_m = new SimpleDateFormat("MM");
        String month = sdf_m.format(date);
        int imonth = Integer.parseInt(month);

        AttendanceTableMaker tableMaker = new AttendanceTableMaker(this, imonth);
        int atd_number = tableMaker.getNumber();

        Button refresh = findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
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
                ATDRank_Request atdRank_request = new ATDRank_Request(atd_number, name, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ATDRank.this);
                queue.add(atdRank_request);
                Toast.makeText(getApplicationContext(),String.format("뒤로 갔다, 다시 와주세요!"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class myItemDecoration extends RecyclerView.ItemDecoration{
        private final int padding = 60;

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state){
            super.getItemOffsets(outRect, view, parent, state);
            outRect.top = padding;
        }
    }
}