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
public class OrderDetails implements Serializable {
    public static final long serialVersionUID = -1L;
    int orderid;
    int itemid;
    int qty;

    public OrderDetails(int orderid, int itemid, int qty) {
        this.orderid = orderid;
        this.itemid = itemid;
        this.qty = qty;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
    
}
