/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectdemo;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
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
import static projectdemo.LoginController.user;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class EditInformationController implements Initializable {

    @FXML
    private Button editBtn;
    @FXML
    private Label invalidLoginLabel;
    @FXML
    private TextField firstnameField;
    @FXML
    private TextField lastnameField;
    @FXML
    private TextField UsernameField;
    @FXML
    private TextField mailField;
    @FXML
    private TextField phoneField;
    @FXML
    private PasswordField PasswordField;
    @FXML
    private TextField addressField;
    @FXML
    private Button cancelBtm;
    @FXML
    private Label successLabel;
    @FXML
    private Label invalidLabel;

    /**
     * Initializes the controller class.
     */
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initEdit();
    }

    @FXML
    private void handleEditAction(ActionEvent event) {
        String query = "UPDATE users SET firstname = '" + firstnameField.getText() + "',lastname = '"+lastnameField.getText()+ "',"
                + "username = '"+UsernameField.getText()+ "', mail = '"+mailField.getText() +"', password = '"
                + PasswordField.getText()+"', address = '"+addressField.getText()+"', phone = "+phoneField.getText()+" WHERE account_id = "+ user.getID();
        
        if(PasswordField.getText().isEmpty() == true){
            invalidLabel.setText("Enter a password to continue");
        }
        
        else{
            try{
                executeQuery(query);
                invalidLabel.setText("");
                successLabel.setText("Edit successful");
                user.setFirstname(firstnameField.getText());
                user.setLastname(lastnameField.getText());
                user.setMail(mailField.getText());
                user.setUsername(UsernameField.getText());
                user.setAddress(addressField.getText());
                user.setPhone(Integer.parseInt(phoneField.getText()));
                cancelBtm.setText("Go back");
            }catch(SQLSyntaxErrorException e){
                invalidLabel.setText("Enter a valid phone number");
            }catch(NumberFormatException ex){
                System.out.println("sql exception");
            }
            catch(Exception e){
                System.out.println("exception");
            }
        }
    }

    @FXML
    private void handleCancelAction(ActionEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("Reader.fxml"));
        Scene scene = new Scene(root);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(scene);
        app_stage.setTitle("Book store");
        app_stage.show();
        
    }
    
    
    private void executeQuery(String query) throws SQLSyntaxErrorException,SQLException{
        DatabaseConnection connection = new DatabaseConnection();
        Connection conn = connection.getConnected();

        Statement st = conn.createStatement();
        st.executeUpdate(query);
    }
    
    private void initEdit(){
        firstnameField.setText(user.getFirstname());
        lastnameField.setText(user.getLastname());
        UsernameField.setText(user.getUsername());
        mailField.setText(user.getMail());
        addressField.setText(user.getAddress());
        phoneField.setText(""+user.getPhone());
    }
}
