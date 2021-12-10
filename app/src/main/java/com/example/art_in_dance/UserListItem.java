package com.example.art_in_dance;

public class UserListItem {
    String UserName;
    String UserID;
    String UserBD;
    String Class;
    String CURRENT;

    public UserListItem(){}

    public UserListItem(String UserName, String UserID, String UserBD, String Class, String CURRENT){
        this.UserName = UserName;
        this.UserID = UserID;
        this.UserBD = UserBD;
        this.Class = Class;
        this.CURRENT = CURRENT;
    }

    public String getName(){
        return UserName;
    }

    public String getID(){
        return UserID;
    }

    public String getBD(){
        return UserBD;
    }

    public String getDate(){
        return Class;
    }

    public String getCURRENT(){
        return Integer.toString(Integer.parseInt(CURRENT)-1);
    }
}
