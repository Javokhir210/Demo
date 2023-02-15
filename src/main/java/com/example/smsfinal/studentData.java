package com.example.smsfinal;

public class studentData {

    private String StudentPage = "";


    private int studentId;
    private String f1Name;
    private String s1Name;
    private String studentPhone;
    private String studentEmail;
    private String group = "1";
    private String group2 = "2";

    public String getGroup2() {
        return group2;
    }

    private Integer mark = 5;

    studentData(){
    }
    private static final studentData instance = new studentData();

    public static studentData getInstance() {
        return instance;
    }

    public studentData(int studentId, String fName, String sName, String studentPhone, String studentEmail) {
        this.studentId = studentId;
        this.f1Name = fName;
        this.s1Name = sName;
        this.studentPhone = studentPhone;
        this.studentEmail = studentEmail;
    }

    public String getGroup() {
        return group;
    }

    public Integer getMark() {
        return mark;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getF1Name() {
        return f1Name;
    }

    public String getS1Name() {
        return s1Name;
    }

    public String getStudentPhone() {
        return studentPhone;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentPage(String studentPage) {
        StudentPage = studentPage;
    }

    public String getStudentPage() {
        return StudentPage;
    }
}