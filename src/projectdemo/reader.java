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
public class reader extends User{
    private int phone;
    private String address;

    public reader(int phone, String address, int ID, String firstname, String lastname, String username, String mail, String password) {
        super(ID, firstname, lastname, username, mail, password);
        this.phone = phone;
        this.address = address;
    }
    

    public reader(String username,String mail,String password,int phone, String address, String payment) {
        super(username,mail,password);
        this.phone = phone;
        this.address = address;
    }
    
    
    
    public int getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    
}
