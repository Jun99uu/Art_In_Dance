package com.example.art_in_dance;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ATDRank_Request extends StringRequest {
    final static private String URL = "http://artindance99.ivyro.net/Register.php";
    private Map<String,String> map;

    public ATDRank_Request(int ATD_NUMBER, String UserName, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        String atdnum = Integer.toString(ATD_NUMBER);

        map = new HashMap<>();
        map.put("ATD", atdnum);
        map.put("UserName", UserName);
    } //출석 수가 String 형태로 저장되어있음 중요!!

    @Override
    protected Map<String, String> getParams() throws AuthFailureError{
        return map;
    }
}
