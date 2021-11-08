package com.example.art_in_dance;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest{
    final static private String URL = "http://artindance99.ivyro.net/Login.php";
    private Map<String, String> map;

    public LoginRequest(String UserID, String UserPWD, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("UserID", UserID);
        map.put("UserPWD", UserPWD);
    }

    @Override
    protected  Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
