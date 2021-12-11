package com.example.art_in_dance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText login_id, login_password;
    private Button login_button, join_button;
    private ImageView cuteleaf1, moveleaf;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cuteleaf1 = findViewById(R.id.cuteleaf1);
        moveleaf = findViewById(R.id.moveleaf);
        Glide.with(this).load(R.raw.cuteleaf).into(cuteleaf1);
        Glide.with(this).load(R.raw.heart).into(moveleaf);
        checkBox = findViewById(R.id.savecheck);

        SharedPreferences sf = getSharedPreferences("File", MODE_PRIVATE);
        String text1 = sf.getString("text1", "");

        if(!(text1.equals("")))
            checkBox.setChecked(true);

        login_id = findViewById(R.id.et_id);
        login_password = findViewById(R.id.et_pass);

        login_id.setText(text1);

        join_button = findViewById(R.id.btn_register);
        join_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        login_button = findViewById(R.id.btn_login);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserID = login_id.getText().toString();
                String UserPWD = login_password.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if(success){
                                String UserID = jsonObject.getString("UserID");
                                String UserPWD = jsonObject.getString("UserPWD");
                                String UserName = jsonObject.getString("UserName");
                                String UserBD = jsonObject.getString("UserBD");

                                Toast.makeText(getApplicationContext(),String.format("%s님 환영합니다.", UserName), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, LoadingPage.class);

                                intent.putExtra("UserID", UserID);
                                intent.putExtra("UserPWD", UserPWD);
                                intent.putExtra("UserName", UserName);
                                intent.putExtra("UserBD", UserBD);

                                startActivity(intent);
                            } else {
                                Toast.makeText( getApplicationContext(), "로그인에 실패하셨습니다.", Toast.LENGTH_SHORT ).show();
                                return;
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest( UserID, UserPWD, responseListener );
                RequestQueue queue = Volley.newRequestQueue( LoginActivity.this );
                queue.add( loginRequest );
            }
        });
    }

    @Override
    protected void onStop(){
        super.onStop();

        SharedPreferences sharedPreferences = getSharedPreferences("File", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(checkBox.isChecked()){
            String ID = login_id.getText().toString();
            editor.putString("text1", ID);
        }else{
            editor.putString("text1", "");
        }

        editor.commit();
    }
}