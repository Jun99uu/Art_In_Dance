package com.example.art_in_dance;

public class ClassReserver_item {
    String info;
    String date;
    String time;
    String person;
    String subject;
    String teacher;
    String content;
    String current;
    String ID;

    public ClassReserver_item(){}

    public ClassReserver_item(String info, String ID, String date, String time, String person, String subject, String teacher, String content, String current){
        this.info = info;
        this.date = date;
        this.time = time;
        this.person = person;
        this.subject = subject;
        this.teacher = teacher;
        this.content = content;
        this.current = current;
        this.ID = ID;
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

    public String getID(){
        return ID;
    }
}
