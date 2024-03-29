package com.example.smsfinal;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;

public class Utils {


    public static void changePage(ActionEvent a, String fileName, String title){
        Parent root = null;
        try {
            FXMLLoader load = new FXMLLoader(Utils.class.getResource(fileName));
            root = load.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) ((Node) a.getSource()).getScene().getWindow();
        assert root != null;
        stage.setScene(new Scene(root, 770, 500));
        stage.setTitle(title);
        stage.show();
    }
    public static void LogInUser(ActionEvent event, String username, String password, String LoginAs){
        if (LoginAs == null){
            LoginAs = "";
        }
        Connection connection = null;
        PreparedStatement psCheck = null;
        ResultSet resultSet = null;
        String page1 = "";
        String passwordKey = "";

        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/swingapp", "root", "");

            if (LoginAs.equals("")){
                System.out.println("Fill in all the fields!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Fill in all the fields!");
                alert.show();
            }

            else if (Objects.equals(LoginAs, "Admin")){
                psCheck = connection.prepareStatement("SELECT password FROM admin WHERE email = ?");
                page1 = "adminMain.fxml";
                passwordKey = "password";
            }

            else if (Objects.equals(LoginAs, "Student")) {
                psCheck = connection.prepareStatement("SELECT password1 FROM users WHERE name = ?");
                page1 = "studentPage.fxml";
                passwordKey = "password1";
            }

            else if (Objects.equals(LoginAs, "Teacher")) {
                psCheck = connection.prepareStatement("SELECT password2 FROM teacher WHERE name = ?");
                if (username == "Mohamed"){
                    page1 = "teacherPage.fxml";
                }else page1 = "teacherPage1.fxml";
                passwordKey = "password2";
            }
//            else {
//                psCheck = connection.prepareStatement("SELECT password FROM admin WHERE email = ?");
//            }

            psCheck.setString(1, username);
            resultSet = psCheck.executeQuery();

            if (!resultSet.isBeforeFirst()){
                System.out.println("Username is not found!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Incorrect input");
                alert.show();
            }else{
                while (resultSet.next()){

                    getData.username = username;

                    String basePass = resultSet.getString(passwordKey);

                    if (basePass.equals(password)){
                        changePage(event, page1, "Menu!");
                    }else {
                        System.out.println("Password is incorrect");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Password is not correct");
                        alert.show();
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (resultSet!=null){
                try {
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psCheck != null){
                try {
                    psCheck.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection != null){
                try {
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void SignUpUser(ActionEvent event, String fName, String sName, String email, String pNumber,
                                  String password){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckExist = null;
        ResultSet resultSet = null;

        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/swingapp", "root", "");
            psCheckExist = connection.prepareStatement("SELECT * FROM users WHERE name = ?");
            psCheckExist.setString(1, fName);
            resultSet = psCheckExist.executeQuery();

            if (resultSet.isBeforeFirst()){
                System.out.println("User is already exist");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("This username is used before, choose another");
                alert.show();
            }else {
                psInsert = connection.prepareStatement("INSERT INTO users (name, surname, email, phone, password1) " +
                        "VALUES (?, ?, ?, ?, ?)");
                psInsert.setString(1, fName);
                psInsert.setString(2, sName);
                psInsert.setString(3, email);
                psInsert.setString(4, pNumber);
                psInsert.setString(5, password);
                psInsert.executeUpdate();

                 changePage(event, "log.fxml", "Login!");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            if (resultSet != null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psCheckExist != null){
                try{
                    psCheckExist.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psInsert != null){
                try{
                    psInsert.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection != null){
                try{
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void AdminSceneOf(ActionEvent e, AnchorPane page){
            page.setVisible(false);
    }

    public static void AdminSceneOn(ActionEvent e, AnchorPane page){
        page.setVisible(true);
    }

    public static ObservableList<studentData> addStudentListData2(){

        Connection connect = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ObservableList<studentData> listData2 = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users2";

        try {
            connect = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/swingapp", "root", "");
            preparedStatement = connect.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            studentData studentD;

            while (resultSet.next()){
                studentD = new studentData(resultSet.getInt("id"), resultSet.getString("name"),
                        resultSet.getString("surname"), resultSet.getString("phone"),
                        resultSet.getString("email"));
                listData2.add(studentD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }return listData2;
    }

    public static ObservableList<studentData> addStudentListData(){

        Connection connect = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ObservableList<studentData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users";

        try {
            connect = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/swingapp", "root", "");
            preparedStatement = connect.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            studentData studentD;

            while (resultSet.next()){
                studentD = new studentData(resultSet.getInt("id"), resultSet.getString("name"),
                        resultSet.getString("surname"), resultSet.getString("phone"),
                        resultSet.getString("email"));
                listData.add(studentD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }return listData;
        }

    public static ObservableList<teacherData> addTeacherListData(){

        Connection connect = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ObservableList<teacherData> listData1 = FXCollections.observableArrayList();
        String sql = "SELECT * FROM teacher";

        try {
            connect = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/swingapp", "root", "");
            preparedStatement = connect.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            teacherData

                    teacherD;

            while (resultSet.next()){
                teacherD = new teacherData(resultSet.getInt("teacherID"), resultSet.getString("name"),
                        resultSet.getString("surname"), resultSet.getString("phone"),
                        resultSet.getString("email"));
                listData1.add(teacherD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }return listData1;
    }

    public static ObservableList<courseData> courseListData(){

        Connection connect = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ObservableList<courseData> listData2 = FXCollections.observableArrayList();
        String sql = "SELECT * FROM courses ";

        try {
            connect = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/swingapp", "root", "");
            preparedStatement = connect.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            courseData courseD;

            while (resultSet.next()){
                courseD = new courseData(resultSet.getInt("number"), resultSet.getString("courseName"),
                        resultSet.getString("professor"));
                listData2.add(courseD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }return listData2;
    }

}
