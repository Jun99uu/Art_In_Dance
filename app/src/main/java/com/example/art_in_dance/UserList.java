package com.example.art_in_dance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
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

public class UserList extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recyclerView;
    ArrayList<UserListItem> items = new ArrayList<>();
    UserListAdapter adapter;
    Intent intent;
    String Class, CURRENT;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        intent = getIntent();
        Class = intent.getStringExtra("Class");
        CURRENT = intent.getStringExtra("CURRENT");

        swipeRefreshLayout = findViewById(R.id.swipelist);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = findViewById(R.id.userlistrecycler);
        adapter = new UserListAdapter(items, this);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        String Url = "http://artindance99.ivyro.net/UserList.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                items.clear();
                adapter.notifyDataSetChanged();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String UserClass= jsonObject.getString("Class");
                        if(UserClass.equals(Class)){
                            String UserID = jsonObject.getString("UserID");
                            String UserName = jsonObject.getString("UserName");
                            String UserBD = jsonObject.getString("UserBD");
                            items.add(0, new UserListItem(UserName, UserID, UserBD, Class, CURRENT));
                            adapter.notifyItemInserted(0);
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
        RequestQueue requestQueue = Volley.newRequestQueue(UserList.this);
        requestQueue.add(jsonArrayRequest);

        recyclerView.addItemDecoration(new UserList.myItemDecoration());
    }

    @Override
    public void onRefresh() {
        updateLayoutView();
        swipeRefreshLayout.setRefreshing(false);
    }

    public void updateLayoutView() {
        String Url = "http://artindance99.ivyro.net/UserList.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                items.clear();
                adapter.notifyDataSetChanged();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String UserClass= jsonObject.getString("Class");
                        if(UserClass.equals(Class)){
                            String UserID = jsonObject.getString("UserID");
                            String UserName = jsonObject.getString("UserName");
                            String UserBD = jsonObject.getString("UserBD");
                            items.add(0, new UserListItem(UserName, UserID, UserBD, Class, CURRENT));
                            adapter.notifyItemInserted(0);
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
        RequestQueue requestQueue = Volley.newRequestQueue(UserList.this);
        requestQueue.add(jsonArrayRequest);
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