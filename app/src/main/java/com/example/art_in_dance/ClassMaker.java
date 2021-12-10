package com.example.art_in_dance;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class ClassMaker extends AppCompatActivity {

    EditText gettime, getsubject, getteacher, getexplain, getmax;
    Button selectDate, selectTime, class_make;
    TextView textDate;
    int resultYear, resultMonth, resultDay, resultHour, resultMinute;
    String date = "", time, subject, teacher, explain, max;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_maker);
        selectDate = findViewById(R.id.date_pick);
        selectTime = findViewById(R.id.time_pick);
        textDate = findViewById(R.id.date);
        gettime = findViewById(R.id.time);
        getsubject = findViewById(R.id.subject);
        getteacher = findViewById(R.id.info_main);
        getexplain = findViewById(R.id.explain);
        getmax = findViewById(R.id.info_person);
        class_make = findViewById(R.id.class_make);

        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                resultYear = yy;
                resultMonth = mm+1;
                resultDay =dd;
            }
        }, mYear, mMonth, mDay);

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        int mHour = 0, mMinute = 0;
        selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(ClassMaker.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        resultHour = hourOfDay;
                        resultMinute = minute;
                        textDate.setText(resultYear + "년 " + resultMonth + "월 " + resultDay + "일 " + resultHour + "시 " + resultMinute + "분");
                        date =  change(resultYear, resultMonth, resultDay, resultHour, resultMinute);
                    }
                },mHour, mMinute, true);
                timePickerDialog.show();
            }
        });


        class_make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time = gettime.getText().toString();
                subject = getsubject.getText().toString();
                teacher = getteacher.getText().toString();
                explain = getexplain.getText().toString();
                max = getmax.getText().toString();
                if (date == null || time.length() == 0 || subject.length() == 0 || teacher.length() == 0 || explain.length() == 0 || max.length() == 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ClassMaker.this);
                    dialog = builder.setMessage("모두 입력해주세요.").setNegativeButton("확인",null).create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            //수업등록
                            if (success) {
                                Toast.makeText(getApplicationContext(),String.format("수업 등록이 완료되었습니다."), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ClassMaker.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "수업 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                ClassMakerRequest classMakerRequest = new ClassMakerRequest(date, time, max, subject, teacher, explain, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ClassMaker.this);
                queue.add(classMakerRequest);
            }
        });

    }

    static public String change(int year, int month, int day, int hour, int minute){
        String myear = Integer.toString(year);

        String mmonth;
        if(month<10)
            mmonth = "0"+month;
        else
            mmonth = Integer.toString(month);

        String mday;
        if(day<10)
            mday = "0"+day;
        else
            mday = Integer.toString(day);

        String mhour;
        if(hour<10)
            mhour = "0"+hour;
        else
            mhour = Integer.toString(hour);

        String mminute;
        if(minute<10)
            mminute = "0"+minute;
        else
            mminute = Integer.toString(minute);

        String date = myear + mmonth + mday + mhour + mminute;

        return date;
    }
}