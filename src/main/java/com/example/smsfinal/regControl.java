package com.example.smsfinal;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BubbleChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class regControl implements Initializable {
    @FXML
    private Button btLogGo;
    @FXML
    private Button bt_Register;
    @FXML
    private TextField tf_regname;
    @FXML
    private TextField tf_regsurname;
    @FXML
    private TextField tf_regnumber;
    @FXML
    private TextField tf_regemail;
    @FXML
    private TextField tf_regPassword;
    @FXML
    private TextField tf_regConfirmPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btLogGo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utils.changePage(event, "log.fxml", "Login!");
            }
        });
        bt_Register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!tf_regemail.getText().trim().isEmpty() && !tf_regname.getText().trim().isEmpty() &&
                        !tf_regsurname.getText().trim().isEmpty() && !tf_regPassword.getText().trim().isEmpty()){
                    if (tf_regPassword.getText().equals(tf_regConfirmPassword.getText())){
                        Utils.SignUpUser(event, tf_regname.getText(), tf_regsurname.getText(), tf_regemail.getText(),
                                tf_regnumber.getText(), tf_regPassword.getText());
                    }else {
                        System.out.println("Passwords didn't match!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Passwords didn't match!");
                        alert.show();
                    }
                }else {
                    System.out.println("Fill in the required fields");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Required fields are not filled in!");
                    alert.show();
                }
            }
        });
    }
}
