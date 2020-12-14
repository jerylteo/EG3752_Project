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
public class User implements Serializable {
    public static final long serialVersionUID = -1L;
    int id;
    String name;
    String email;
    String add1;
    String add2;
    String postal;
    String mobile;
    String pw;
    int points;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public User(int id, String name, String email, String add1, String add2, String postal, String mobile, String pw) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.add1 = add1;
        this.add2 = add2;
        this.postal = postal;
        this.mobile = mobile;
        this.pw = pw;
    }

    public User(String name, String email, String add1, String postal, String mobile, String pw) {
        this.name = name;
        this.email = email;
        this.add1 = add1;
        this.postal = postal;
        this.mobile = mobile;
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdd1() {
        return add1;
    }

    public void setAdd1(String add1) {
        this.add1 = add1;
    }

    public String getAdd2() {
        return add2;
    }

    public void setAdd2(String add2) {
        this.add2 = add2;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
    
    
}
