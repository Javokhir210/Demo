package com.example.smsfinal;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;

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
        stage.setScene(new Scene(root, 600, 500));
        stage.setTitle(title);
        stage.show();
    }
    public static void LogInUser(ActionEvent event, String username, String password){
        Connection connection = null;
        PreparedStatement psCheck = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/swingapp", "root", "");
            psCheck = connection.prepareStatement("SELECT password1 FROM users WHERE name = ?");
            psCheck.setString(1, username);
            resultSet = psCheck.executeQuery();

            if (!resultSet.isBeforeFirst()){
                System.out.println("Username is not found!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Incorrect input");
                alert.show();
            }else{
                while (resultSet.next()){
                    String basePass = resultSet.getString("password1");

                    if (basePass.equals(password)){
                        changePage(event, "adminMain.fxml", "Menu");
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
}
