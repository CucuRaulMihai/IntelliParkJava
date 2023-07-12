package com.example.intellipark;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class HelloApplication extends Application
{
    private static Stage stg;
    public static String parameters;

    public static void main(String[] args) throws Exception
    {
        //we check if the user has entered at least 1 CLA
        if(args.length>0)
        {
            // we pass it to parameter for the start function
            parameters = args[0];
        }
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        stg = primaryStage;
        primaryStage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("user.fxml"));
        if(Objects.equals(parameters, "admin"))
        {
            root = FXMLLoader.load(getClass().getResource("sign-up.fxml"));
        }
        if(Objects.equals(parameters,"user"))
        {
            //basically this is useless, since we only have 2 scenes
            //and the user is the default one is case we don't specify
            //any command line arguments, but hey, it is another option, I guess.
            root = FXMLLoader.load(getClass().getResource("user.fxml"));
        }
        primaryStage.setTitle("IntelliPark");
        primaryStage.setScene(new Scene(root,800,600));
        primaryStage.show();
    }
    public void changeScene(String fxml) throws IOException
    {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
    }


}