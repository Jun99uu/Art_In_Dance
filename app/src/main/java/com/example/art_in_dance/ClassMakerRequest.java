package com.example.art_in_dance;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ClassMakerRequest extends StringRequest {
    final static private String URL = "http://artindance99.ivyro.net/AddClass.php";
    private Map<String,String> map;

    public ClassMakerRequest(String Date, String Time, String Max, String Subject, String Teacher, String Explain, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("CONDATE", Date);
        map.put("CONTIME", Time);
        map.put("PERSON", Max);
        map.put("SUBJECT", Subject);
        map.put("TEACHER", Teacher);
        map.put("CONTENT", Explain);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
