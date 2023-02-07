package com.example.smsfinal;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class UniControl implements Initializable {

    @FXML
    private Button btn_AdminHome;

    @FXML
    private Button btn_AdminStudent;

    @FXML
    private Button btn_AdminTeacher;

    @FXML
    private AnchorPane AdminHome_page;

    @FXML
    private AnchorPane AdminStudent_page;

    @FXML
    private AnchorPane AdminTeacher_page;

    @FXML
    private TableColumn<studentData, String > col_adminPStudentId;

    @FXML
    private TableColumn<studentData, String > col_adminPStudentName;

    @FXML
    private TableColumn<studentData, String > col_adminPStudentSurname;

    @FXML
    private TableColumn<studentData, String > col_adminPStudentPhone;

    @FXML
    private TableColumn<studentData, String > col_adminPStudentEmail;

    @FXML
    private TextField addStudent_Id;

    @FXML
    private TextField addStudent_Name;

    @FXML
    private TextField addStudent_Surname;

    @FXML
    private TextField addStudent_Phone;

    @FXML
    private TextField addStudent_Email;


    @FXML
    private TableView<studentData> table_adminStudent;

    public void addStudentAdd(){
        String sql = "INSERT INTO users (id, name, surname, phone, email, password1) VALUES(?,?,?,?,?,?)";
        Connection connect = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            if (addStudent_Id.getText().isEmpty() || addStudent_Name.getText().isEmpty()
            || addStudent_Surname.getText().isEmpty() || addStudent_Phone.getText().isEmpty()
            || addStudent_Email.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Please, fill in all the fields");
                alert.showAndWait();
            }else {
                connect = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/swingapp", "root", "");

                String check = "SELECT id FROM users WHERE id = '" + addStudent_Id.getText() + "'";
                Statement statement = connect.createStatement();
                resultSet = statement.executeQuery(check);
                if(resultSet.next()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setContentText("Student ID: " +  addStudent_Id.getText() + " is already exist");
                    alert.showAndWait();
                }else {
                    preparedStatement = connect.prepareStatement(sql);
                    preparedStatement.setString(1, addStudent_Id.getText());
                    preparedStatement.setString(2, addStudent_Name.getText());
                    preparedStatement.setString(3, addStudent_Surname.getText());
                    preparedStatement.setString(4, addStudent_Phone.getText());
                    preparedStatement.setString(5, addStudent_Email.getText());
                    preparedStatement.setString(6, "123456");
                    preparedStatement.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setContentText("Successfully Added");
                    alert.showAndWait();

                    addStudentShowList();
                    addStudentReset();

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addStudentUpdate(){
        String sql = "UPDATE users SET name = '" +addStudent_Name.getText()+ "', surname = '"
                +addStudent_Surname.getText() + "', phone = '"+addStudent_Phone.getText() +"', email = '" +
                addStudent_Email.getText() + "' WHERE id = '"+ addStudent_Id.getText()+"'";

        Connection connect = null;


        try {
            connect = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/swingapp", "root", "");

            if (addStudent_Id.getText().isEmpty() || addStudent_Name.getText().isEmpty()
                    || addStudent_Surname.getText().isEmpty() || addStudent_Phone.getText().isEmpty()
                    || addStudent_Email.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Please, fill in all the fields");
                alert.showAndWait();
            }else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setContentText("Are you sure");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)){
                    Statement statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setContentText("Successfully Added");
                    alert.showAndWait();

                    addStudentShowList();
                    addStudentReset();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addStudentDelete(){
        String sql = "DELETE FROM users WHERE id = '"+ addStudent_Id.getText() + "'";
        Connection connect = null;
        try {

            connect = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/swingapp", "root", "");

            if (addStudent_Id.getText().isEmpty() || addStudent_Name.getText().isEmpty()
                    || addStudent_Surname.getText().isEmpty() || addStudent_Phone.getText().isEmpty()
                    || addStudent_Email.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Please, fill in all the fields");
                alert.showAndWait();
            }else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setContentText("Are you sure");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)){
                    Statement statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setContentText("Successfully Deleted");
                    alert.showAndWait();

                    addStudentShowList();
                    addStudentReset();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addStudentReset(){
        addStudent_Id.setText("");
        addStudent_Name.setText("");
        addStudent_Surname.setText("");
        addStudent_Phone.setText("");
        addStudent_Email.setText("");
    }



    public void setAddStudentSelect(){
        studentData studentD = table_adminStudent.getSelectionModel().getSelectedItem();
        int num = table_adminStudent.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1)return;

        addStudent_Id.setText(String.valueOf(studentD.getStudentId()));
        addStudent_Name.setText(String.valueOf(studentD.getF1Name()));
        addStudent_Surname.setText(String.valueOf(studentD.getS1Name()));
        addStudent_Phone.setText(String.valueOf(studentD.getStudentPhone()));
        addStudent_Email.setText(String.valueOf(studentD.getStudentEmail()));
    }

    private ObservableList<studentData> addStudentList;
//
//    public UniControl() {
//    }

    public void addStudentShowList(){
        addStudentList = Utils.addStudentListData();

        col_adminPStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        col_adminPStudentName.setCellValueFactory(new PropertyValueFactory<>("f1Name"));
        col_adminPStudentSurname.setCellValueFactory(new PropertyValueFactory<>("s1Name"));
        col_adminPStudentPhone.setCellValueFactory(new PropertyValueFactory<>("studentPhone"));
        col_adminPStudentEmail.setCellValueFactory(new PropertyValueFactory<>("studentEmail"));

        table_adminStudent.setItems(addStudentList);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_AdminHome.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utils.AdminSceneOn(event, AdminHome_page);
                Utils.AdminSceneOf(event, AdminStudent_page);
                Utils.AdminSceneOf(event, AdminTeacher_page);
                btn_AdminHome.setStyle("-fx-background-color:linear-gradient(to bottom right,#121d4a, #8597ed)");
                btn_AdminStudent.setStyle("-fx-background-color:transparent");
                btn_AdminTeacher.setStyle("-fx-background-color:transparent");
            }
        });

        btn_AdminStudent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utils.AdminSceneOf(event, AdminHome_page);
                Utils.AdminSceneOn(event, AdminStudent_page);
                Utils.AdminSceneOf(event, AdminTeacher_page);
                btn_AdminStudent.setStyle("-fx-background-color:linear-gradient(to bottom right,#121d4a, #8597ed)");
                btn_AdminHome.setStyle("-fx-background-color:transparent");
                btn_AdminTeacher.setStyle("-fx-background-color:transparent");
            }
        });

        btn_AdminTeacher.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utils.AdminSceneOf(event, AdminHome_page);
                Utils.AdminSceneOf(event, AdminStudent_page);
                Utils.AdminSceneOn(event, AdminTeacher_page);
                btn_AdminTeacher.setStyle("-fx-background-color:linear-gradient(to bottom right,#121d4a, #8597ed)");
                btn_AdminStudent.setStyle("-fx-background-color:transparent");
                btn_AdminHome.setStyle("-fx-background-color:transparent");
            }
        });

        addStudentShowList();
    }
}
