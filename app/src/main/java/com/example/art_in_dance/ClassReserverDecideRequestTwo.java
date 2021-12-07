package com.example.art_in_dance;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ClassReserverDecideRequestTwo extends StringRequest {

    final static private String URL = "http://artindance99.ivyro.net/Reserve2.php";
    private Map<String, String> map;

    public ClassReserverDecideRequestTwo(String CONDATE, String UpdateCurrent, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("CONDATE", CONDATE);
        map.put("UpdateCurrent", UpdateCurrent);
    }

    @Override
    protected  Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
