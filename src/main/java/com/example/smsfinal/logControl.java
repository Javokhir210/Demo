package com.example.smsfinal;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class logControl implements Initializable {

    @FXML
    private Button btLogin;
    @FXML
    private ComboBox login_SignAs;
    @FXML
    private TextField tfLogName;
    @FXML
    private PasswordField tfLogPass;
    @FXML
    private Button btSignGo;

    private String[] signAs = {"Student", "Teacher", "Admin"};

    public void addSignAsList(){
        List<String> listS = new ArrayList<>();

        for (String data: signAs){
            listS.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listS);
        login_SignAs.setItems(listData);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btSignGo.setOnAction(new EventHandler<ActionEvent>() {
                                 @Override
                                 public void handle(ActionEvent event) {
                                     Utils.changePage(event, "reg.fxml", "Registration!");
                                 }
                             });
        btLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utils.LogInUser(event, tfLogName.getText(), tfLogPass.getText(), (String) login_SignAs.getSelectionModel().getSelectedItem());
            }
        });
        addSignAsList();

    }
}
