package com.example.smsfinal;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.security.PrivateKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class studentPageController implements Initializable {

    @FXML
    private Label StudentLabelName;

    @FXML
    private Label studentStudentName;

    @FXML
    private Label StudentLabelSurname;

    @FXML
    private Label StudentLabelId;

    @FXML
    private Label StudentLabelEmail;

    @FXML
    private Label StudentLabelPhone;

    public void StudentShowInfo(){
            studentData getData = studentData.getInstance();
            String sql = "SELECT * FROM users WHERE name = '" + getData.getStudentPage() + "'";
            String sql1 = "SELECT * FROM users WHERE id =?;";
            Connection connect = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            System.out.println(getData.getStudentPage());
            try {
                connect = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/swingapp", "root", "");
                preparedStatement = connect.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()){
                    StudentLabelId.setText(String.valueOf(resultSet.getInt("id")));
                    studentStudentName.setText(String.valueOf(resultSet.getString("name")));
                    StudentLabelName.setText(String.valueOf(resultSet.getString("name")));
                    StudentLabelSurname.setText(String.valueOf(resultSet.getString("surname")));
                    StudentLabelEmail.setText(String.valueOf(resultSet.getString("email")));
                    StudentLabelPhone.setText(String.valueOf(resultSet.getString("phone")));
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
     }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StudentShowInfo();

    }
}
