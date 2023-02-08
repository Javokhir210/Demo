package com.example.smsfinal;

public class teacherData {
    private int teacherId;
    private String f1Name;
    private String s1Name;
    private String teacherPhone;
    private String teacherEmail;

    public teacherData(int teacherId, String fName, String sName, String teacherPhone, String teacherEmail) {
        this.teacherId = teacherId;
        this.f1Name = fName;
        this.s1Name = sName;
        this.teacherPhone = teacherPhone;
        this.teacherEmail = teacherEmail;
    }

    public int getStudentId() {
        return teacherId;
    }

    public String getF1Name() {
        return f1Name;
    }

    public String getS1Name() {
        return s1Name;
    }

    public String getStudentPhone() {
        return teacherPhone;
    }

    public String getStudentEmail() {
        return teacherEmail;
    }
}
