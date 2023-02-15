package com.example.smsfinal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.security.PrivateKey;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class studentPageController implements Initializable {

    @FXML
    private Button btn_studentHome;

    @FXML
    private Button btn_studentStudent;

    @FXML
    private Button btn_studentTime;

    @FXML
    private AnchorPane StudentHome_page;

    @FXML
    private AnchorPane StudentStudent_page;

    @FXML
    private AnchorPane StudentTime_page;

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

    @FXML
    private TableView<courseData> table_studentCourse;

    @FXML
    private TableColumn<courseData, String > col_studentPCourseId;

    @FXML
    private TableColumn<courseData, String > col_studentPCourseName;

    @FXML
    private TableColumn<courseData, String > col_studentPProfName;

    @FXML
    private TableColumn<courseData, String > col_studentPCourseGrade;

    @FXML
    private TableColumn<courseData, String > col_studentPCourseAttendance;

    @FXML
    private ComboBox courseStudent;

    @FXML
    private ComboBox profStudent;

    @FXML
    private Button studentSubjectAdd;

    @FXML
    private Button student_back;


    private String[] prof = {"M.Uvaze", "S.Juraev", "F.Ermanov", "O.Qadam"};

    public void profList(){
        List<String> listS = new ArrayList<>();

        for (String data: prof){
            listS.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listS);
        profStudent.setItems(listData);
    }


    private String[] subject = {"Linear Algebra", "Software Eng."};

    public void subjectList(){
        List<String> listS = new ArrayList<>();

        for (String data: subject){
            listS.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listS);
        courseStudent.setItems(listData);
    }


    private ObservableList<courseData> addCourseList = FXCollections.observableArrayList();

    public void addTeacherShowList(){
        addCourseList = Utils.courseListData();

        col_studentPCourseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        col_studentPCourseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        col_studentPProfName.setCellValueFactory(new PropertyValueFactory<>("profName"));
        col_studentPCourseGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        col_studentPCourseAttendance.setCellValueFactory(new PropertyValueFactory<>("attendance"));

        table_studentCourse.setItems(addCourseList);

    }

    public void resetFields(){
        courseStudent.setItems(null);
        profStudent.setItems(null);
    }


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

        student_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utils.changePage(event, "log.fxml", "Login!");
            }
        });

        studentSubjectAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String sql = "INSERT INTO courses (courseName, professor) VALUES(?,?)";
                Connection connect = null;
                PreparedStatement preparedStatement = null;
                ResultSet resultSet = null;

                try {

                    if (courseStudent.getSelectionModel().isEmpty() || profStudent.getSelectionModel().isEmpty()){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setContentText("Please, choose from both boxes");
                        alert.showAndWait();
                    }else {
                        connect = DriverManager.getConnection(
                                "jdbc:mysql://localhost:3306/swingapp", "root", "");

                        String check = "SELECT courseName FROM courses WHERE courseName = '"
                                + courseStudent.getSelectionModel().getSelectedItem()+ "'";
                        Statement statement = connect.createStatement();
                        resultSet = statement.executeQuery(check);
                        if(resultSet.next()){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Message");
                            alert.setContentText("The course: " + (String) courseStudent.getSelectionModel().getSelectedItem()
                                    + " is already exist");
                            alert.showAndWait();
                        }else {
                            preparedStatement = connect.prepareStatement(sql);
                            preparedStatement.setString(1, (String) courseStudent.getSelectionModel().getSelectedItem());
                            preparedStatement.setString(2, (String) profStudent.getSelectionModel().getSelectedItem());
                            preparedStatement.executeUpdate();

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information Message");
                            alert.setContentText("Successfully Added");
                            alert.showAndWait();

                        }

                        addTeacherShowList();
                        resetFields();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btn_studentHome.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utils.AdminSceneOn(event, StudentHome_page);
                Utils.AdminSceneOf(event, StudentStudent_page);
                Utils.AdminSceneOf(event, StudentTime_page);
                btn_studentHome.setStyle("-fx-background-color:linear-gradient(to bottom right,#121d4a, #8597ed)");
                btn_studentStudent.setStyle("-fx-background-color:transparent");
                btn_studentTime.setStyle("-fx-background-color:transparent");
            }
        });

        btn_studentStudent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utils.AdminSceneOf(event, StudentHome_page);
                Utils.AdminSceneOn(event, StudentStudent_page);
                Utils.AdminSceneOf(event, StudentTime_page);
                btn_studentStudent.setStyle("-fx-background-color:linear-gradient(to bottom right,#121d4a, #8597ed)");
                btn_studentHome.setStyle("-fx-background-color:transparent");
                btn_studentTime.setStyle("-fx-background-color:transparent");
            }
        });

        btn_studentTime.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utils.AdminSceneOf(event, StudentHome_page);
                Utils.AdminSceneOf(event, StudentStudent_page);
                Utils.AdminSceneOn(event, StudentTime_page);
                btn_studentTime.setStyle("-fx-background-color:linear-gradient(to bottom right,#121d4a, #8597ed)");
                btn_studentStudent.setStyle("-fx-background-color:transparent");
                btn_studentHome.setStyle("-fx-background-color:transparent");

            }
        });


        StudentShowInfo();
        addTeacherShowList();
        profList();
        subjectList();

    }
}
