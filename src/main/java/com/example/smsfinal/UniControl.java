package com.example.smsfinal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import java.util.concurrent.ExecutionException;

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

    @FXML
    private TableColumn<teacherData, String > col_adminPTeacherId;

    @FXML
    private TableColumn<teacherData, String > col_adminPTeacherName;

    @FXML
    private TableColumn<teacherData, String > col_adminPTeacherSurname;

    @FXML
    private TableColumn<teacherData, String > col_adminPTeacherPhone;

    @FXML
    private TableColumn<teacherData, String > col_adminPTeacherEmail;

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
    private TableView<teacherData> table_adminTeacher;

    @FXML
    private TextField adminStudent_Search;

    @FXML
    private TextField adminTeacherSearch;

    @FXML
    private Label homeTotalStudent;

    @FXML
    private Label homeTotalTeacher;

    @FXML
    private Label homeTotalCourses;

    @FXML
    private Button admin_back;


    public void totalCourses(){
        String sql = "SELECT COUNT(number) FROM courses";
        Connection connect = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int countData = 0;
        try {
            connect = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/swingapp", "root", "");
            preparedStatement = connect.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                countData = resultSet.getInt("COUNT(number)");
            }

            homeTotalCourses.setText(String.valueOf(countData));

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void totalTeachers(){
        String sql = "SELECT COUNT(teacherID) FROM teacher";
        Connection connect = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int countData = 0;
        try {
            connect = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/swingapp", "root", "");
            preparedStatement = connect.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                countData = resultSet.getInt("COUNT(teacherID)");
            }

            homeTotalTeacher.setText(String.valueOf(countData));

        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void totalStudents(){
        String sql = "SELECT COUNT(id) FROM users";
        Connection connect = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int countData = 0;
        try {
            connect = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/swingapp", "root", "");
            preparedStatement = connect.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                countData = resultSet.getInt("COUNT(id)");
            }

            homeTotalStudent.setText(String.valueOf(countData));

        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void AdminTeacherSearch1(){
        addTeacherList = Utils.addTeacherListData();
        FilteredList<teacherData> filter = new FilteredList<>(addTeacherList, e-> true);
        adminTeacherSearch.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(predicateTeacherData ->{
                String Id = Integer.toString(predicateTeacherData.getStudentId());
                if (newValue == null || newValue.isEmpty()){
                    return true;
                }
                String searchKey = newValue.toLowerCase();

                if (Id.contains(searchKey)){
                    return true;
                }else if (predicateTeacherData.getF1Name().toLowerCase().contains(searchKey)){
                    return true;
                }else if (predicateTeacherData.getS1Name().toLowerCase().contains(searchKey)){
                    return true;
                }else if (predicateTeacherData.getStudentEmail().toLowerCase().contains(searchKey)){
                    return true;
                }else if (predicateTeacherData.getStudentPhone().toLowerCase().contains(searchKey)){
                    return true;
                }else return false;
            });
        });

        SortedList<teacherData> sortedList = new SortedList<>(filter);
        sortedList.comparatorProperty().bind(table_adminTeacher.comparatorProperty());
        table_adminTeacher.setItems(sortedList);
    }

    public void AdminStudentSearch1(){
        FilteredList<studentData> filter = new FilteredList<>(addStudentList, e-> true);
        adminStudent_Search.textProperty().addListener((Observable, oldValue, newValue) -> filter.setPredicate(predicateStudentData ->{
            String Id = Integer.toString(predicateStudentData.getStudentId());
            if (newValue == null || newValue.isEmpty()){
                return true;
            }
            String searchKey = newValue.toLowerCase();

            if (Id.contains(searchKey)){
                return true;
            }else if (predicateStudentData.getF1Name().toLowerCase().contains(searchKey)){
                return true;
            }else if (predicateStudentData.getS1Name().toLowerCase().contains(searchKey)){
                return true;
            }else if (predicateStudentData.getStudentEmail().toLowerCase().contains(searchKey)){
                return true;
            }else if (predicateStudentData.getStudentPhone().toLowerCase().contains(searchKey)){
                return true;
            }else return false;
        }));

        SortedList<studentData> sortedList = new SortedList<>(filter);
        sortedList.comparatorProperty().bind(table_adminStudent.comparatorProperty());
        table_adminStudent.setItems(sortedList);
    }

    public void addTeacherAdd(){
        String sql = "INSERT INTO teacher (teacherID, name, surname, phone, email, password2) VALUES(?,?,?,?,?,?)";
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

                String check = "SELECT teacherID FROM teacher WHERE teacherID = '" + addTeacher_Id.getText() + "'";
                Statement statement = connect.createStatement();
                resultSet = statement.executeQuery(check);
                if(resultSet.next()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setContentText("Teacher ID: " +  addTeacher_Id.getText() + " is already exist");
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

                    addTeacherShowList();
                    addTeacherReset();
                    AdminTeacherSearch1();

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addTeacherUpdate(){
        AdminTeacherSearch1();
        String sql = "UPDATE teacher SET name = '" +addTeacher_Name.getText()+ "', surname = '"
                +addTeacher_Surname.getText() + "', phone = '"+addTeacher_Phone.getText() +"', email = '" +
                addTeacher_Email.getText() + "' WHERE TeacherID = '"+ addTeacher_Id.getText()+"'";

        Connection connect = null;


        try {
            connect = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/swingapp", "root", "");

            if (addTeacher_Id.getText().isEmpty() || addTeacher_Name.getText().isEmpty()
                    || addTeacher_Surname.getText().isEmpty() || addTeacher_Phone.getText().isEmpty()
                    || addTeacher_Email.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Please, fill in all the fields");
                alert.showAndWait();
            }
//            else if (!addTeacher_Id.getText().isEmpty()){
//                ResultSet resultSet = null;
//                String check = "SELECT id FROM users WHERE id = '" + addTeacher_Id.getText() + "'";
//
//                Statement statement = connect.createStatement();
//                resultSet = statement.executeQuery(check);
//                if(resultSet.next()){
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setTitle("Error Message");
//                    alert.setContentText("Teacher ID: " +  addTeacher_Id.getText() + " is already exist");
//                    alert.showAndWait();}
//            }
            else {
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

                    addTeacherShowList();
                    addTeacherReset();
                    AdminTeacherSearch1();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addTeacherDelete(){
        String sql = "DELETE FROM teacher WHERE teacherID = '"+ addTeacher_Id.getText() + "'";
        Connection connect = null;
        try {

            connect = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/swingapp", "root", "");

            if (addTeacher_Id.getText().isEmpty() || addTeacher_Name.getText().isEmpty()
                    || addTeacher_Surname.getText().isEmpty() || addTeacher_Phone.getText().isEmpty()
                    || addTeacher_Email.getText().isEmpty()){
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

                    addTeacherShowList();
                    addTeacherReset();
                    AdminTeacherSearch1();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addTeacherReset(){
        addTeacher_Id.setText("");
        addTeacher_Name.setText("");
        addTeacher_Surname.setText("");
        addTeacher_Phone.setText("");
        addTeacher_Email.setText("");
        AdminTeacherSearch1();
    }



    public void setAddTeacherSelect(){
        teacherData teacherD = table_adminTeacher.getSelectionModel().getSelectedItem();
        int num = table_adminTeacher.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1)return;

        addTeacher_Id.setText(String.valueOf(teacherD.getStudentId()));
        addTeacher_Name.setText(String.valueOf(teacherD.getF1Name()));
        addTeacher_Surname.setText(String.valueOf(teacherD.getS1Name()));
        addTeacher_Phone.setText(String.valueOf(teacherD.getStudentPhone()));
        addTeacher_Email.setText(String.valueOf(teacherD.getStudentEmail()));
        AdminTeacherSearch1();
    }

    private ObservableList<teacherData> addTeacherList = FXCollections.observableArrayList();

    public void addTeacherShowList(){
        addTeacherList = Utils.addTeacherListData();

        col_adminPTeacherId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        col_adminPTeacherName.setCellValueFactory(new PropertyValueFactory<>("f1Name"));
        col_adminPTeacherSurname.setCellValueFactory(new PropertyValueFactory<>("s1Name"));
        col_adminPTeacherPhone.setCellValueFactory(new PropertyValueFactory<>("studentPhone"));
        col_adminPTeacherEmail.setCellValueFactory(new PropertyValueFactory<>("studentEmail"));

        table_adminTeacher.setItems(addTeacherList);
        AdminTeacherSearch1();

    }

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
//                    AdminStudentSearch1();

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
            }
//            else if (!addStudent_Id.getText().isEmpty()){
//                String check = "SELECT id FROM users WHERE id = '" + addStudent_Id.getText() + "'";
//
//                Statement statement = connect.createStatement();
//                resultSet = statement.executeQuery(check);
//                if(resultSet.next()){
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setTitle("Error Message");
//                    alert.setContentText("Student ID: " +  addStudent_Id.getText() + " is already exist");
//                    alert.showAndWait();}
//            }
            else {
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
                    AdminStudentSearch1();
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
                    AdminStudentSearch1();
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
        AdminStudentSearch1();
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
        AdminStudentSearch1();
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
        AdminStudentSearch1();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        admin_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utils.changePage(event, "log.fxml", "Login!");
            }
        });

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
        addTeacherShowList();
        AdminStudentSearch1();
        AdminTeacherSearch1();
        totalStudents();
        totalTeachers();
        totalCourses();

    }
}
