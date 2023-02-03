package com.example.smsfinal;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class logControl implements Initializable {

    @FXML
    private Button btLogin;
    @FXML
    private TextField tfLogName;
    @FXML
    private PasswordField tfLogPass;
    @FXML
    private Button btSignGo;
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
                Utils.LogInUser(event, tfLogName.getText(), tfLogPass.getText());
            }
        });

    }
}
