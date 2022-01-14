/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectdemo;

/**
 *
 * @author hp
 */
public abstract class User {
    private int ID;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String mail;

    public User(int ID, String firstname, String lastname, String username, String mail, String password) {
        this.ID = ID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.mail = mail;
    }
    
    
    public User(String username, String mail, String password) {
        this.username = username;
        this.password = password;
    }
    
    public int getID() {
        return ID;
    }
    
    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }
    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
    
    public String getMail() {
        return mail;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    
    
}
