package com.example.intellipark;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController
{
    public SignUpController()
    {

    }
    @FXML
    private TextField tf_username;
    @FXML
    private PasswordField tf_password;
    @FXML
    private Button button_login;
    @FXML
    private Label wrongLogin;

    public void admin_login(ActionEvent event) throws IOException
    {
        //when we press the button we check the user
        checkLogIn();
    }
    public void checkLogIn() throws IOException
    {
        HelloApplication m = new HelloApplication();
        if(tf_username.getText().toString().equals("admin") && tf_password.getText().toString().equals("root"))
        {
            wrongLogin.setText("Connection successful!");
            m.changeScene("logged-in.fxml");
        }
        else if(tf_password.getText().isEmpty() || tf_username.getText().isEmpty())
        {
            wrongLogin.setText("Please enter the admin log-in data.");
        }
        else
        {
            wrongLogin.setText("Wrong admin information.");
            //Surely, it will be reported...
        }

    }



}
