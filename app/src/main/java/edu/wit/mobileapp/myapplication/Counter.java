package edu.wit.mobileapp.myapplication;

public class Counter {
    Integer count;
    String course;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String toString(Integer count) {
        return count.toString();
    }
}
