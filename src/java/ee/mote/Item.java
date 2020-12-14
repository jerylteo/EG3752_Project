/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ee.mote;

import java.io.Serializable;

/**
 *
 * @author klmch
 */
public class Item implements Serializable {
    public static final long serialVersionUID = -1L;
    int id;
    String description;
    String brand;
    double price;

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
    int points;
    int qty;

    public Item(int id, String description, String brand, double price, int points, int qty) {
        this.id = id;
        this.description = description;
        this.brand = brand;
        this.price = price;
        this.points = points;
        this.qty = qty;
    }

    public Item(int id, String description, String brand, double price, int points) {
        this.id = id;
        this.description = description;
        this.brand = brand;
        this.price = price;
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
    
    
    
}
