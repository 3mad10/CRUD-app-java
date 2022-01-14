/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectdemo;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author hp
 */
public class DatabaseConnection {
    public Connection link;
    public Connection getConnected(){
        try{
            link = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?user=root&password=####");
            return link;
        }catch(SQLException e){
            System.out.println("error: "+e.getMessage());
            e.getCause();
            return null;
        }
    }
}
