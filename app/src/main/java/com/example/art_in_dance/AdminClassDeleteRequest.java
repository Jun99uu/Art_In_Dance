package com.example.art_in_dance;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AdminClassDeleteRequest extends StringRequest {
    final static private String URL = "http://artindance99.ivyro.net/AdminDeleteClass.php";
    private Map<String, String> map;

    public AdminClassDeleteRequest(String CONDATE, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("CONDATE", CONDATE);
    }
    @Override
    protected  Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
