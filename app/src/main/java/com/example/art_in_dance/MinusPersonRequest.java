package com.example.art_in_dance;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MinusPersonRequest extends StringRequest {
    final static private String URL = "http://artindance99.ivyro.net/MiunsPerson.php";
    private Map<String,String> map;

    public MinusPersonRequest(String CURRENT, String CONDATE, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("CURRENT", CURRENT);
        map.put("CONDATE", CONDATE);
    }

    @Override
    protected  Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
