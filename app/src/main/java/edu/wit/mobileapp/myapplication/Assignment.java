package edu.wit.mobileapp.myapplication;

import java.util.Date;

public class Assignment {
    public String assignmentName;
    public String courseName;
    public long dueDate;
    public String assignmentType;

    //1 = High 2 = Medium 3 = Low
    public Integer priority;

    public Assignment() {
    }

    public Assignment(String assignmentName, String courseName, Date dueDate, String assignmentType, Integer priority) {
        this.assignmentName = assignmentName;
        this.courseName = courseName;
        //this.dueDate = dueDate;
        this.assignmentType = assignmentType;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "assignmentName='" + assignmentName + '\'' +
                ", courseName='" + courseName + '\'' +
                ", dueDate=" + dueDate +
                ", assignmentType='" + assignmentType + '\'' +
                ", priority=" + priority +
                '}';
    }

    public String getAssignmentType() {
        return assignmentType;
    }

    public void setAssignmentType(String assignmentType) {
        this.assignmentType = assignmentType;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

   /* public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    } */
}
