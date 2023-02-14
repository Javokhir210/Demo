package com.example.smsfinal;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
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

        teacher_StudentShowList();

    }
}
