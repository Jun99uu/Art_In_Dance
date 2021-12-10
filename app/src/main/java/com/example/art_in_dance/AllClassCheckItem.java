package com.example.art_in_dance;

public class AllClassCheckItem {
    String info;
    String date;
    String time;
    String person;
    String subject;
    String teacher;
    String content;
    String current;

    public AllClassCheckItem(){}

    public AllClassCheckItem(String info, String date, String time, String person, String subject, String teacher, String content, String current){
        this.info = info;
        this.date = date;
        this.time = time;
        this.person = person;
        this.subject = subject;
        this.teacher = teacher;
        this.content = content;
        this.current = current;
    }

    public String getinfo(){
        return info;
    }

    public String getconDate(){
        return date;
    }

    public String getTime(){
        return time;
    }

    public String getPerson(){
        return person;
    }

    public String getSubject(){
        return subject;
    }

    public String getTeacher(){
        return teacher;
    }

    public String getContent(){
        return content;
    }

    public String getCurrent(){
        return current;
    }

}
