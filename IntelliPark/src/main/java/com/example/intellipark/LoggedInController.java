package com.example.intellipark;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

import java.sql.*;

public class LoggedInController implements Initializable {

    @FXML
    private TableView<AdminSearch> tv_data;
    @FXML
    private TableColumn<AdminSearch,Integer> tc_id;
    @FXML
    private TableColumn<AdminSearch,String> tc_name;
    @FXML
    private TableColumn<AdminSearch,String> tc_email;
    @FXML
    private TableColumn<AdminSearch,String> tc_license;
    @FXML
    private TableColumn<AdminSearch,String> tc_type;
    @FXML
    private TableColumn<AdminSearch, Date> tc_enter;
    @FXML
    private TextField tf_info;

    ObservableList<AdminSearch> adminSearchObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        DBConnection connection = new DBConnection();
        Connection connectionDB = connection.getDBConnection();
        String viewClientsQuery = "SELECT clientsID, name,e_mail,license_plate,vehicle_type,enter_time FROM clients";

        try{
            Statement stmt = connectionDB.createStatement();
            ResultSet QueryOutput = stmt.executeQuery(viewClientsQuery);

            while (QueryOutput.next())
            {
                Integer queryclientID = QueryOutput.getInt("clientsID");
                String queryname= QueryOutput.getString("name");
                String queryemail= QueryOutput.getString("e_mail");
                String querylicense= QueryOutput.getString("license_plate");
                String queryvehicle= QueryOutput.getString("vehicle_type");

                ///Here we populate the table that the admin sees pretty much.
                adminSearchObservableList.add(new AdminSearch(queryclientID,queryname,queryemail,querylicense,queryvehicle));
            }

            //PropertyValueFactory correspond with AdminSearch fields
            //Here we work with FXML tags
            tc_id.setCellValueFactory(new PropertyValueFactory<>("clientID"));
            tc_name.setCellValueFactory(new PropertyValueFactory<>("name"));
            tc_email.setCellValueFactory(new PropertyValueFactory<>("email"));
            tc_license.setCellValueFactory(new PropertyValueFactory<>("license"));
            tc_type.setCellValueFactory(new PropertyValueFactory<>("vehicle_type"));


            //We put the items from the ObservableList in the table from the fx controller
            tv_data.setItems(adminSearchObservableList);

            ///initializing the filtered list for the actual list in the admin page
            FilteredList<AdminSearch> filteredData = new FilteredList<>(adminSearchObservableList, init->true);

            tf_info.textProperty().addListener( (observable,oldValue,newValue) ->{
                filteredData.setPredicate(adminSearch -> {
                    ///Basically, when nothing is searched for we show everything, or keep whatever is already on the screen
                    if(newValue.isEmpty() || newValue.isBlank() || newValue==null)
                    {
                        return true;
                    }
                    //Making the search case-insensitive
                    String clientinfo = newValue.toLowerCase();
                    //It's a match! It is a match on the client's info.
                    if(adminSearch.getName().toLowerCase().indexOf(clientinfo)> -1)
                    {
                        return true;//we found the name of the client
                    } 
                    
                    else if (adminSearch.getEmail().toLowerCase().indexOf(clientinfo)> -1) 
                    {
                        return true;//we found the client by email
                    }
                    else if(adminSearch.getLicense().toLowerCase().indexOf(clientinfo)>-1)
                    {
                        return true;//we found the client by license
                    }
                    else if(adminSearch.getVehicle_type().toLowerCase().indexOf(clientinfo)>-1)
                    {
                        return true;//we found the client who have the same vehicle type
                    } else if (adminSearch.getClientID().toString().indexOf(clientinfo)>-1)
                    {
                        return true;//we found the client by the client id, but first we had a conversion
                    }
                    else
                    {
                        return false;//you found nothing.
                    }
                });

                SortedList<AdminSearch> sortedData = new SortedList<>(filteredData);

                //Here we bind the data visually to the tv_data aka the tableview aka the table from the fxml file
                sortedData.comparatorProperty().bind(tv_data.comparatorProperty());

                //actually putting the data into the tv_data
                tv_data.setItems(sortedData);
            } );

        }catch (SQLException esql) {
            esql.printStackTrace();
        }
    }
}
