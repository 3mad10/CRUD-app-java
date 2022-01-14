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
public class Books {
    private int ID;
    private String title;
    private String author;
    private int price;
    private String catagory;
    private int available;

    public Books(int ID, String title, String author, int price, String catagory, int available) {
        this.ID = ID;
        this.title = title;
        this.author = author;
        this.price = price;
        this.catagory = catagory;
        this.available = available;
    }

    public int getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPrice() {
        return price;
    }

    public String getCatagory() {
        return catagory;
    }

    public int getAvailable() {
        return available;
    }
    
}
