package com.example.art_in_dance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AttendanceTableMaker extends SQLiteOpenHelper {
    static final String DB_NAME = "USER_INFO"; //데이터베이스 이름
    static final String TABLE_NAME = "AttendanceTABLE"; //테이블 이름
    static final String DATE = "DATE"; //요소1 _ 날짜(정수)
    static final String ATTENDANCE = "ATTENDANCE"; //요소2 _ 출석여부(정수)
    static final String ATD_NUMBER = "ATD_NUMBER"; //요소3 _ 이번 달 출석 횟수(실수)

    private final String createQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + DATE + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ATTENDANCE + " INTEGER, " + ATD_NUMBER + " INTEGER)";

    //생성자
    public AttendanceTableMaker(Context context, int DATABASE_VERSION) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    //테이블 생성
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(createQuery);
    }

    private final String deleteQuery = "DELETE FROM " + TABLE_NAME;

    //테이블 업데이트 _ 새로운 버전 주면 기존 테이블 행 싹 비움
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(deleteQuery);
    }

    //테이블에 값 삽입
    public void insertContent(int date, int attendance, int atd_number){
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();
        contentValues.put(DATE, date);
        contentValues.put(ATTENDANCE, attendance);
        contentValues.put(ATD_NUMBER, atd_number);
        db.insert(TABLE_NAME, null, contentValues);
    }

    //오늘자 db가 있나없나 확인 -> 가장 마지막 줄 DB를 불러오면 됨
    public int getResult() {
        SQLiteDatabase db = getReadableDatabase();
        int result = 0;

        Cursor cursor = db.rawQuery("SELECT DATE FROM AttendanceTABLE ORDER BY ROWID DESC LIMIT 1", null);
        if(cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                result = cursor.getInt(0);
            }
        }else
            return 0;
        return result;
    }

    //출석 횟수 확인해주는 함수
    public int getNumber(){
        SQLiteDatabase db = getReadableDatabase();
        int result = 0;

        Cursor cursor = db.rawQuery("SELECT ATD_NUMBER FROM AttendanceTABLE ORDER BY ROWID DESC LIMIT 1", null);
        if(cursor.getCount() > 0){
            while (cursor.moveToNext()) {
                result = cursor.getInt(0);
            }
        }else
            return 0;
        return result;
    }

    //출석한 날짜(일만) 배열로 받아오는 함수
    public ArrayList<Integer> getATDday(){
        ArrayList<Integer> getdaydata = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT DATE FROM AttendanceTABLE", null);
        if(cursor.getCount() > 0){
            for(int i = 0; cursor.moveToNext(); i++){
                getdaydata.add(cursor.getInt(0));
                System.out.println(getdaydata.get(i));
            }
        }

        return getdaydata;
    }

}
