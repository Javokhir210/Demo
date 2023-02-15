package com.example.smsfinal;

public class courseData {
    private int courseId;
    private String courseName;
    private String profName;
    private int grade= (int) (5.7 - Math.random());
    private int attendance = (int) ((Math.random()+.7) * 59);


    public courseData(int courseId, String courseName, String profName) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.profName = profName;
    }


    public int getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getProfName() {
        return profName;
    }

    public int getGrade() {
        return grade;
    }

    public int getAttendance() {
        return attendance;
    }
}
