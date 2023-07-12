package com.example.intellipark;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.*;

public class UserController implements Initializable {
    public UserController()
    {

    }
    @FXML
    private TextField tf_name;

    @FXML
    private TextField tf_license;

    @FXML
    private TextField tf_email;

    @FXML
    private ChoiceBox<String> cb_vehicle;

    @FXML
    private ChoiceBox<String> cb_duration;

    @FXML
    private Button button_reserve ;

    @FXML
    private Button button_admin;

    @FXML
    private Label label_WrongInfo;

    public boolean checkUser()
    {
        Pattern toFind = Pattern.compile("\\b\\w*@yahoo.com$|\\b\\w*@gmail.com$");
        //Practically, b means start and $ means end, and that means an exact matching of that type of string, not a substring.
        Matcher toMatch = toFind.matcher(tf_email.getText().toString());
        boolean CorrectEmail = toMatch.find();
        if(!CorrectEmail)
        {
            label_WrongInfo.setText("Invalid E-mail address!");
            return false;
        }

        toFind = Pattern.compile("\\b[A-Z]{2}\\d{2}[A-Z]{3}$");
        // any license plate from Romania, at least the format, I will not write 41 counties, by hand for validation at the first 2.
        toMatch = toFind.matcher(tf_license.getText().toString());
        boolean CorrectLicense = toMatch.find();
        if(!CorrectLicense)
        {
            label_WrongInfo.setText("Invalid license plate!");
            return false;
        }
        if(tf_name.getText().isEmpty())
        {
            label_WrongInfo.setText("Please enter your name!");
            return false;
        }
        if(cb_vehicle.getValue()==null)
        {
            label_WrongInfo.setText("Please enter your vehicle type!");
            return false;
        }
        if(cb_duration.getValue()==null)
        {
            label_WrongInfo.setText("Please select your duration");
            return false;
        }
        else
        {
            label_WrongInfo.setText("Reserved successfully!");
            return true;
        }
    }

    public void reserveUser(ActionEvent event) throws IOException
    {
        //when we press the button we check the user
        // and also add the data to the database
        if(checkUser())
        {
            DBConnection connection = new DBConnection();
            Connection connectionDB = connection.getDBConnection();
            System.out.println("Connection successful!");
            String add_client = "INSERT INTO clients "+"VALUES (default,'"+tf_name.getText()+"','"
                    +tf_email.getText()+"','"+tf_license.getText()+"','"+cb_vehicle.getValue()+"',"+"NOW())";
            System.out.println(add_client);
            Statement add_query = null;
            String query1 = "DELETE FROM clients " +" WHERE enter_time < now() - interval 1 HOUR ;";
            String query2 = "DELETE FROM clients " +" WHERE enter_time < now() - interval 2 HOUR ;";
            String query3 = "DELETE FROM clients " +" WHERE enter_time < now() - interval 3 HOUR ;";
            String query4 = "DELETE FROM clients " +" WHERE enter_time < now() - interval 4 HOUR ;";
            String query6 = "DELETE FROM clients " +" WHERE enter_time < now() - interval 6 HOUR ;";
            //String delete = "DELETE FROM clients " +" WHERE enter_time < now() - interval "+cb_duration.getValue().toUpperCase()+" ;";

            try {
                add_query =connectionDB.createStatement();
                add_query.executeUpdate(add_client);
                System.out.println("Query executed successfully");
                Statement delete_query = null;
                delete_query = connectionDB.createStatement();
                delete_query.executeUpdate(query1);
                delete_query.executeUpdate(query2);
                delete_query.executeUpdate(query3);
                delete_query.executeUpdate(query4);
                delete_query.executeUpdate(query6);
                System.out.println("Deletion successful!");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBConnection connection = new DBConnection();
        Connection connectionDB = connection.getDBConnection();
        ArrayList<String> VehicleTypes = new ArrayList<>();
        VehicleTypes.add("Car");
        VehicleTypes.add("Motorcycle");
        VehicleTypes.add("Van");
        cb_vehicle.getItems().addAll(VehicleTypes);
        cb_vehicle.setOnAction(this::getVehicleType);
        ArrayList<String> DurationTypes = new ArrayList<>();
        DurationTypes.add("1 hour");
        DurationTypes.add("2 hour");
        DurationTypes.add("3 hour");
        DurationTypes.add("4 hour");
        DurationTypes.add("6 hour");
        cb_duration.getItems().addAll(DurationTypes);
        cb_duration.setOnAction(this::getDurationType);
    }

    public void getVehicleType(ActionEvent actionEvent)
    {
        String VehicleType = cb_vehicle.getValue();
    }
    public void getDurationType(ActionEvent actionEvent)
    {
        String DurationType = cb_duration.getValue();
    }

    public void AdminPage(ActionEvent event) throws IOException
    {
        HelloApplication m = new HelloApplication();
        m.changeScene("sign-up.fxml");
    }

}
