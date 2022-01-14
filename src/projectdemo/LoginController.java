/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectdemo;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class LoginController implements Initializable {

    @FXML
    private Button sign;
    @FXML
    private Button register;
    @FXML
    private Label invalidLoginLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField PasswordField;

    /**
     * Initializes the controller class.
     */
    
    protected static reader user;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("Register.fxml"));
        Scene scene = new Scene(root);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(scene);
        app_stage.setTitle("Register");
        app_stage.show();
    }
    
            
            
    @FXML
    private void handleLoginAction(ActionEvent event) throws IOException{
        
        if (usernameField.getText().isEmpty() == false && PasswordField.getText().isEmpty() == false){
            validateLogin();
        } else {
            invalidLoginLabel.setText("Please enter a Username and a Password");
        }
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    

    private void validateLogin() {
        DatabaseConnection connection = new DatabaseConnection();
        Connection conn = connection.getConnected();
        //String verifyLogin = "SELECT count(1) FROM users WHERE username = '"+usernameField.getText()+"' AND password = '"+PasswordField.getText()+"'";
        String adminquery = "SELECT * FROM users WHERE username = '"+usernameField.getText()+"' AND password = '"+PasswordField.getText()+"'";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(adminquery);
            if(rs.next()){
                int admin = rs.getInt("admin");
                if(admin == 1){
                    turnPageAdmin();
                }
                else if (admin == 0){
                    user = new reader(rs.getInt("phone"),rs.getString("address"),rs.getInt("account_id"),rs.getString("firstname"),rs.getString("lastname"),rs.getString("username"),rs.getString("mail"),rs.getString("password"));
                    turnPageReader();
                }
                
            }
            else{
                    invalidLoginLabel.setText("invalid login");
                }
            }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void turnPageAdmin() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root);
        Stage app_stage = (Stage) ((Node) sign).getScene().getWindow();
        app_stage.setScene(scene);
        app_stage.setTitle("Book store");
        app_stage.show();
    }

    private void turnPageReader() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Reader.fxml"));
        Scene scene = new Scene(root);
        Stage app_stage = (Stage) ((Node) sign).getScene().getWindow();
        app_stage.setScene(scene);
        app_stage.setTitle("Book store");
        app_stage.show();
    }
    
    public static User getUser(){
        return user;
    }
    
}
