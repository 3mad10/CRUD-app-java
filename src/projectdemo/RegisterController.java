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
import java.sql.SQLIntegrityConstraintViolationException;
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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class RegisterController implements Initializable {

    @FXML
    private Button registerBtn;
    @FXML
    private TextField UsernameField;
    @FXML
    private PasswordField PasswordField;
    @FXML
    private TextField firstnameField;
    @FXML
    private TextField lastnameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneField;
    @FXML
    private Button cancelBtm;
    @FXML
    private TextField mailField;
    @FXML
    private Label successLabel;
    @FXML
    private Label invalidUsername;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleRegisterAction(ActionEvent event) {
        String query = "INSERT into users (firstname,lastname,username,mail,password,address,phone) VALUES ('"
                +firstnameField.getText() + "','"+lastnameField.getText()+"','"+UsernameField.getText() +"','"
                +mailField.getText()+"','"+PasswordField.getText()+"','"+addressField.getText()+"',"+phoneField.getText()+")";
                
        try{ 
            executeQuery(query);
            invalidUsername.setText("");
            successLabel.setText("Registration successful");
            cancelBtm.setText("Go back");
        }catch(SQLSyntaxErrorException e){
            System.out.println("sql exception");
        }
        catch(SQLIntegrityConstraintViolationException e){
            invalidUsername.setText("username already in use");
        }
        catch(Exception e){
            System.out.println("exception");
        }
    }
    
    private void executeQuery(String query) throws SQLException,SQLIntegrityConstraintViolationException{
        DatabaseConnection connection = new DatabaseConnection();
        Connection conn = connection.getConnected();
        Statement st = conn.createStatement();
        st.executeUpdate(query);
        
    }

    @FXML
    private void handleCancelAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(scene);
        app_stage.setTitle("login");
        app_stage.show();
    }
    
}
