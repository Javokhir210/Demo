package com.example.smsfinal;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;


public class teacherPageController implements Initializable {

    @FXML
    private Button btn_TeacherHome;

    @FXML
    private Button btn_TeacherStudent;

    @FXML
    private Button btn_TeacherTeacher;

    @FXML
    private AnchorPane TeacherHome_page;

    @FXML
    private AnchorPane TeacherStudent_page;

    @FXML
    private AnchorPane TeacherTeacher_page;

    @FXML
    private TableView<studentData> table_teacherStudent;

    @FXML
    private TableColumn<studentData, String > col_teacherPStudentId;

    @FXML
    private TableColumn<studentData, String > col_teacherPStudentName;

    @FXML
    private TableColumn<studentData, String > col_teacherPStudentSurname;

    @FXML
    private TableColumn<studentData, String > col_teacherPStudentGroup;

    @FXML
    private TableColumn<studentData, String > col_teacherPStudentMark;

    @FXML
    private Label teacher_studentNumber;

    @FXML
    private Button teacher_bt_group1;

    @FXML
    private Button teacher_bt_group2;

    @FXML
    private Button teacher_back;

    public void groupNum(String groupType){
        String sql = "SELECT COUNT(id) FROM "+ groupType;
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

            teacher_studentNumber.setText(String.valueOf(countData));

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ObservableList<studentData> StudentList2;

    public void teacher_StudentShowList2(){
        StudentList2 = Utils.addStudentListData2();

        col_teacherPStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        col_teacherPStudentName.setCellValueFactory(new PropertyValueFactory<>("f1Name"));
        col_teacherPStudentSurname.setCellValueFactory(new PropertyValueFactory<>("s1Name"));
        col_teacherPStudentGroup.setCellValueFactory(new PropertyValueFactory<>("group2"));
        col_teacherPStudentMark.setCellValueFactory(new PropertyValueFactory<>("mark"));

        table_teacherStudent.setItems(StudentList2);
//        AdminStudentSearch1();
    }



    private ObservableList<studentData> StudentList;

    public void teacher_StudentShowList(){
        StudentList = Utils.addStudentListData();

        col_teacherPStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        col_teacherPStudentName.setCellValueFactory(new PropertyValueFactory<>("f1Name"));
        col_teacherPStudentSurname.setCellValueFactory(new PropertyValueFactory<>("s1Name"));
        col_teacherPStudentGroup.setCellValueFactory(new PropertyValueFactory<>("group"));
        col_teacherPStudentMark.setCellValueFactory(new PropertyValueFactory<>("mark"));

        table_teacherStudent.setItems(StudentList);
//        AdminStudentSearch1();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        teacher_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utils.changePage(event, "log.fxml", "Login!");
            }
        });

        teacher_bt_group1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            groupNum("users");
            teacher_StudentShowList();
            }
        });

        teacher_bt_group2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                groupNum("users2");
                teacher_StudentShowList2();
            }
        });

        btn_TeacherHome.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utils.AdminSceneOn(event, TeacherHome_page);
                Utils.AdminSceneOf(event, TeacherStudent_page);
                Utils.AdminSceneOf(event, TeacherTeacher_page);
                btn_TeacherHome.setStyle("-fx-background-color:linear-gradient(to bottom right,#121d4a, #8597ed)");
                btn_TeacherStudent.setStyle("-fx-background-color:transparent");
                btn_TeacherTeacher.setStyle("-fx-background-color:transparent");
            }
        });

        btn_TeacherStudent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utils.AdminSceneOf(event, TeacherHome_page);
                Utils.AdminSceneOn(event, TeacherStudent_page);
                Utils.AdminSceneOf(event, TeacherTeacher_page);
                btn_TeacherStudent.setStyle("-fx-background-color:linear-gradient(to bottom right,#121d4a, #8597ed)");
                btn_TeacherHome.setStyle("-fx-background-color:transparent");
                btn_TeacherTeacher.setStyle("-fx-background-color:transparent");
            }
        });

        btn_TeacherTeacher.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utils.AdminSceneOf(event, TeacherHome_page);
                Utils.AdminSceneOf(event, TeacherStudent_page);
                Utils.AdminSceneOn(event, TeacherTeacher_page);
                btn_TeacherTeacher.setStyle("-fx-background-color:linear-gradient(to bottom right,#121d4a, #8597ed)");
                btn_TeacherStudent.setStyle("-fx-background-color:transparent");
                btn_TeacherHome.setStyle("-fx-background-color:transparent");

            }
        });


    }
}
