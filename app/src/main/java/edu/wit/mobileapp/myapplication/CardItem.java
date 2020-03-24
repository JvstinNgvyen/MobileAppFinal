package edu.wit.mobileapp.myapplication;

public class CardItem {
    public String title;
    public String date;
    public String assignmentType;
    public String classes;
    public String priority;

    @Override
    public String toString() {
        return "CardItem{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", assignmentType='" + assignmentType + '\'' +
                ", classes='" + classes + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
}
