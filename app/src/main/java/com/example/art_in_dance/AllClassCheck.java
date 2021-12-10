package com.example.art_in_dance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
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

import java.util.ArrayList;

public class AllClassCheck extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<AllClassCheckItem> items = new ArrayList<>();
    AllClassCheckAdapter adapter;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_class_check);
        scrollView = findViewById(R.id.allclass_scroll);
        recyclerView = findViewById(R.id.all_class);
        adapter = new AllClassCheckAdapter(items, this);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        String Url = "http://artindance99.ivyro.net/ReadClass.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                items.clear();
                adapter.notifyDataSetChanged();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String CONDATE = jsonObject.getString("CONDATE");
                        String CONTIME = jsonObject.getString("CONTIME");
                        String PERSON = jsonObject.getString("PERSON");
                        String SUBJECT = jsonObject.getString("SUBJECT");
                        String TEACHER = jsonObject.getString("TEACHER");
                        String CONTENT = jsonObject.getString("CONTENT");
                        String CURRENT = jsonObject.getString("CURRENT");
                        String info = CONDATE.substring(0,4) + "." + CONDATE.substring(4,6) + "." + CONDATE.substring(6,8) + " " + CONDATE.substring(8,10) + ":" + CONDATE.substring(10,12) + "~\n(" + CONTIME + "분)\n" + SUBJECT + "\n" + TEACHER + "쌤 (" + CURRENT + "/" + PERSON + ")";
                        items.add(0, new AllClassCheckItem(info, CONDATE, CONTIME, PERSON, SUBJECT, TEACHER, CONTENT, CURRENT));
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
        RequestQueue requestQueue = Volley.newRequestQueue(AllClassCheck.this);
        requestQueue.add(jsonArrayRequest);

        recyclerView.addItemDecoration(new AllClassCheck.myItemDecoration());

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
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