package com.example.art_in_dance;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    final static private String URL = "http://artindance99.ivyro.net/Register.php";
    private Map<String,String> map;

    public RegisterRequest(String UserID, String UserPWD, String UserName, String UserBD, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("UserID", UserID);
        map.put("UserPWD", UserPWD);
        map.put("UserName", UserName);
        map.put("UserBD", UserBD);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError{
        return map;
    }
}
