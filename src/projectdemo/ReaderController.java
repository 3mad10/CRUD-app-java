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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class ReaderController implements Initializable {

    @FXML
    private TableColumn<Books, String> colTitle;
    @FXML
    private TableColumn<Books, String> colAuthor;
    @FXML
    private TableColumn<Books, String> colCatagory;
    @FXML
    private TableColumn<Books, Integer> colPrice;
    @FXML
    private TableColumn<Books, Integer> colAvailable;
    @FXML
    private Button searchBtn;
    @FXML
    private Button buyBtn;
    @FXML
    private TableView<Books> tableViewBooks;
    
    
    DatabaseConnection connection = new DatabaseConnection();
    Connection conn = connection.getConnected();
    @FXML
    private TextField txtSearch;
    @FXML
    private Label noSelectionLabel;
    @FXML
    private AnchorPane root;
    @FXML
    private Button logOutBtn;
    @FXML
    private Button editInfoBtn;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        User user = LoginController.getUser();
        getAllBooks();
    }    

    @FXML
    private void handleEditAcrion(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("EditInformation.fxml"));
        Scene scene = new Scene(root);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(scene);
        app_stage.setTitle("Edit Information");
        app_stage.show();
    }

    @FXML
    private void handleLogoutAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(scene);
        app_stage.setTitle("login");
        app_stage.show();
    }
    
    public ObservableList<Books> getBooks(String option){
        ObservableList<Books> bookList = FXCollections.observableArrayList();
        String query = null;
        
        if (option.equalsIgnoreCase("all")){
            query = "SELECT * FROM books WHERE available > 0";
        }
        else if (option.equalsIgnoreCase("search")){
            query = "SELECT * FROM books WHERE title LIKE '"+txtSearch.getText()+"%' AND available > 0";
        }
        Statement st;
        ResultSet rs;
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Books books;
            while(rs.next()){
                books = new Books(rs.getInt("id"),rs.getString("title"),rs.getString("author"),rs.getInt("price"),rs.getString("catagory"),rs.getInt("available"));
                bookList.add(books);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return bookList;
    }
    
    public void getAllBooks(){
        ObservableList<Books> List = getBooks("all");
        showBooks(List);
    }
    
    @FXML
    private void handleSearchAction(ActionEvent event) {
        ObservableList<Books> List = getBooks("search");
        showBooks(List);
    }

    
    public void showBooks(ObservableList<Books> List){
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCatagory.setCellValueFactory(new PropertyValueFactory<>("catagory"));
        colAvailable.setCellValueFactory(new PropertyValueFactory<>("available"));
        tableViewBooks.setItems(List);
    }
    
    

    
    @FXML
    private void handleBuyAction(ActionEvent event) {
        try{
        Books book = tableViewBooks.getSelectionModel().getSelectedItem();
        Statement st;
        ResultSet rs;
        String query = "UPDATE books SET available = available - 1 WHERE id="+book.getID();
        
            st = conn.createStatement();
            st.executeUpdate(query);
            getAllBooks();
            noSelectionLabel.setText(" ");
        }catch(Exception ex){
            noSelectionLabel.setText("Select a book");
        }
        
    }
    
}
