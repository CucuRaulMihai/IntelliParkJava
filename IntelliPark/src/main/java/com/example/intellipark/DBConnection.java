package com.example.intellipark;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public Connection DBLink;
    public Connection getDBConnection()
    {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            DBLink = DriverManager.getConnection("jdbc:mysql://localhost:3306/java-project", "root", "VSCode2022");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DBLink;
    }

}
