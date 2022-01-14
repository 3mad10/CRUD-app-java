/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectdemo;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author hp
 */
public class MainController implements Initializable {
    

    @FXML
    private TableColumn<Books, Integer> colID;
    @FXML
    private TableView<Books> tableViewBooks;
    @FXML
    private TableColumn<Books, String> colTitle;
    @FXML
    private TableColumn<Books, String> colAuthor;
    @FXML
    private TableColumn<Books, Integer> colPrice;
    @FXML
    private TableColumn<Books, String> colCatagory;
    @FXML
    private TableColumn<Books, Integer> colAvailable;
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtAuthor;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtCatagory;
    @FXML
    private Button addBtn;
    @FXML
    private Button editBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private TextField txtAvailable;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button goBackBtn;
    @FXML
    private Button searchBtn;
    
    DatabaseConnection connection = new DatabaseConnection();
    Connection conn = connection.getConnected();
    @FXML
    private Label invalidLabel;
    
    @FXML
    private void handleKeyAction(KeyEvent event) throws SQLException {
        
        if ("ENTER".equals(event.getCode().toString())) {
            showSearchedBooks();
        }
    }
    
    
    @FXML
    private void handleMouseAction(MouseEvent event) {
        Books book = tableViewBooks.getSelectionModel().getSelectedItem();
        txtID.setText(book.getID()+"");
        txtTitle.setText(book.getTitle());
        txtAuthor.setText(book.getAuthor());
        txtPrice.setText(book.getPrice()+"");
        txtCatagory.setText(book.getCatagory());
        txtAvailable.setText(book.getAvailable()+"");  
    }
    
    
    
    @FXML
    private void handleButtonAction(ActionEvent e) throws SQLException, IOException{
        if (e.getSource()== addBtn){
            addBook();
        }else if(e.getSource()== editBtn){
            editBook();
        }
        else if(e.getSource()== deleteBtn){
            deleteBook();
        }
        else if(e.getSource()== searchBtn){
            showSearchedBooks();
        }
        else if(e.getSource()== goBackBtn){
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            Scene scene = new Scene(root);
            Stage app_stage = (Stage) ((Node) addBtn).getScene().getWindow();
            app_stage.setScene(scene);
            app_stage.setTitle("Book store");
            app_stage.show();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showAllBooks();
    }
    
    
    
    public ObservableList<Books> getBooks(String option){
        ObservableList<Books> bookList = FXCollections.observableArrayList();
        String query = null;
        
        if (option.equalsIgnoreCase("all")){
            query = "SELECT * FROM books";
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
    
    public void showAllBooks(){
        ObservableList<Books> List = getBooks("all");
        colID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCatagory.setCellValueFactory(new PropertyValueFactory<>("catagory"));
        colAvailable.setCellValueFactory(new PropertyValueFactory<>("available"));
        tableViewBooks.setItems(List);
    }
    
    public void showSearchedBooks(){
        ObservableList<Books> List = getBooks("search");
        colID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCatagory.setCellValueFactory(new PropertyValueFactory<>("catagory"));
        colAvailable.setCellValueFactory(new PropertyValueFactory<>("available"));
        tableViewBooks.setItems(List);
    }
    
    private void addBook() throws SQLException{
        String query = null;
        if(txtID.getText().isEmpty() == true){
            query = "INSERT INTO books (title,author,price,catagory,available) VALUES ('"+txtTitle.getText() + "','"
                +txtAuthor.getText()+"',"+txtPrice.getText()+",'"+txtCatagory.getText() +"',"+txtAvailable.getText()+")";
        }
        if(txtID.getText().isEmpty() == false){
            query = "INSERT INTO books (id,title,author,price,catagory,available) VALUES (" + txtID.getText()+",'"+txtTitle.getText() + "','"
                +txtAuthor.getText()+"',"+txtPrice.getText()+",'"+txtCatagory.getText() +"',"+txtAvailable.getText()+")";
        }
        try{ 
            executeQuery(query);
            showAllBooks();
            invalidLabel.setText("");
            
        }
        catch(SQLException e){
            invalidLabel.setText("Please enter valid values");
        }
    }
    
    private void editBook() throws SQLException{
        String query = "UPDATE books SET title = '" + txtTitle.getText() + "',author = '"+txtAuthor.getText()+ "',"
                + "price = "+txtPrice.getText()+ ", catagory = '"+txtCatagory.getText() +"', available = "+txtAvailable.getText()+" "
                + "WHERE id = "+txtID.getText()+"";
        executeQuery(query);
        showAllBooks();
    }
    private void deleteBook() throws SQLException{
        String query = "DELETE FROM books WHERE id = " + txtID.getText()+ "";
        executeQuery(query);
        showAllBooks();
    }
    
    

    private void executeQuery(String query) throws SQLException{
        DatabaseConnection connection = new DatabaseConnection();
        Connection conn = connection.getConnected();

        Statement st = conn.createStatement();
        st.executeUpdate(query);
    }
    
}
