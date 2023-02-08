package com.example.smsfinal;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.*;
import java.util.Optional;

public class teacherControl {

    @FXML
    private TableColumn<studentData, String > col_adminPTeacherId;

    @FXML
    private TableColumn<studentData, String > col_adminPTeacherName;

    @FXML
    private TableColumn<studentData, String > col_adminPTeacherSurname;

    @FXML
    private TableColumn<studentData, String > col_adminPTeacherPhone;

    @FXML
    private TableColumn<studentData, String > col_adminPTeacherEmail;

    @FXML
    private TextField addTeacher_Id;

    @FXML
    private TextField addTeacher_Name;

    @FXML
    private TextField addTeacher_Surname;

    @FXML
    private TextField addTeacher_Phone;

    @FXML
    private TextField addTeacher_Email;


    @FXML
    private TableView<studentData> table_adminTeacher;

    public void addTeacherAdd(){
        String sql = "INSERT INTO teachers (teacherID, name, surname, phone, email, password2) VALUES(?,?,?,?,?,?)";
        Connection connect = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            if (addTeacher_Id.getText().isEmpty() || addTeacher_Name.getText().isEmpty()
                    || addTeacher_Surname.getText().isEmpty() || addTeacher_Phone.getText().isEmpty()
                    || addTeacher_Email.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Please, fill in all the fields");
                alert.showAndWait();
            }else {
                connect = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/swingapp", "root", "");

                String check = "SELECT teacherID FROM teachers WHERE teacherID = '" + addTeacher_Id.getText() + "'";
                Statement statement = connect.createStatement();
                resultSet = statement.executeQuery(check);
                if(resultSet.next()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setContentText("Student ID: " +  addTeacher_Id.getText() + " is already exist");
                    alert.showAndWait();
                }else {
                    preparedStatement = connect.prepareStatement(sql);
                    preparedStatement.setString(1, addTeacher_Id.getText());
                    preparedStatement.setString(2, addTeacher_Name.getText());
                    preparedStatement.setString(3, addTeacher_Surname.getText());
                    preparedStatement.setString(4, addTeacher_Phone.getText());
                    preparedStatement.setString(5, addTeacher_Email.getText());
                    preparedStatement.setString(6, "7274");
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
        String sql = "UPDATE users SET name = '" +addTeacher_Name.getText()+ "', surname = '"
                +addTeacher_Surname.getText() + "', phone = '"+addTeacher_Phone.getText() +"', email = '" +
                addTeacher_Email.getText() + "' WHERE id = '"+ addTeacher_Id.getText()+"'";

        Connection connect = null;


        try {
            connect = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/swingapp", "root", "");

            if (addTeacher_Id.getText().isEmpty() || addStudent_Name.getText().isEmpty()
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

    public void addStudentShowList(){
        addStudentList = Utils.addStudentListData();

        col_adminPStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        col_adminPStudentName.setCellValueFactory(new PropertyValueFactory<>("f1Name"));
        col_adminPStudentSurname.setCellValueFactory(new PropertyValueFactory<>("s1Name"));
        col_adminPStudentPhone.setCellValueFactory(new PropertyValueFactory<>("studentPhone"));
        col_adminPStudentEmail.setCellValueFactory(new PropertyValueFactory<>("studentEmail"));

        table_adminStudent.setItems(addStudentList);
    }

}
