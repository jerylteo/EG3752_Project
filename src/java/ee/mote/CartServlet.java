/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ee.mote;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author klmch
 */
@WebServlet(name = "CartServlet", urlPatterns = {"/cart"})
public class CartServlet extends HttpServlet {

    @Resource(name="jdbc/jed_project1")
    private DataSource dsCart;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        

        // well, this is what is required so
        User user = (User) request.getSession().getAttribute("userInfo");
        
        if (user != null) {
            System.out.println(user.id);
            
            // retrieve cart from sessionStorage
            Map<Integer, Item> odMap = new HashMap<Integer, Item>();
            if (request.getSession().getAttribute("odMap") != null) {
                odMap = (Map) request.getSession().getAttribute("odMap");
                
                double totalPrice = 0;
                int totalPts = 0, totalQty = 0;
                
                for (int key : odMap.keySet()) {
                    Item val = odMap.get(key);
                    System.out.println(val.id + ", " + val.qty);
                    
                    totalPrice += val.price * val.qty;
                    totalPts += val.points * val.qty;
                    totalQty += val.qty;
                }
                
                request.getSession().setAttribute("totalPrice", totalPrice);
                request.getSession().setAttribute("totalPts", totalPts);
                request.getSession().setAttribute("totalQty", totalQty);
                
            }
            else {
                // cart.jsp checks if null
                odMap = null;
            }
            
            response.sendRedirect(this.getServletContext().getContextPath() + "/cart.jsp");
            
            
            
//            Connection connection = null;
//            PreparedStatement prepStatement = null;
//            ResultSet resultSet = null;
//            int orderid;
////        int[] orderArr;
//        
//            try {
//                connection = dsCart.getConnection();
//                prepStatement = connection.prepareStatement("SELECT * FROM orders WHERE customerid = ?");
//                prepStatement.setInt(1, user.id);
//
//                resultSet = prepStatement.executeQuery();
//
//    //            while (resultSet.next()) {
//    //                orderArr.
//    //            }
//
//                if (resultSet.next()) {
//                    orderid = resultSet.getInt(1);
//
//                    prepStatement = connection.prepareStatement("SELECT * FROM orderdetails WHERE orderid = ?");
//                    prepStatement.setInt(1, orderid);
//
//                    resultSet = prepStatement.executeQuery();
//
//                    List<OrderDetails> orderList = new ArrayList<>();
//                    while (resultSet.next()) {
//                        OrderDetails od = new OrderDetails(
//                                resultSet.getInt(1),
//                                resultSet.getInt(2),
//                                resultSet.getInt(3)
//                        );
//                        orderList.add(od);
//                    }
//    //                orderList.forEach(order -> {
//    //                    
//    //                    prepStatement.setInt(1, order.itemid);
//    //                });
//
//                    prepStatement = connection.prepareStatement("SELECT * FROM item WHERE itemId = ?");
//
//                    List<Item> itemList = new ArrayList<>();
//                    for(int i=0; i<orderList.size(); i++) {
//                        prepStatement.setInt(1, orderList.get(i).itemid);
//
//                        resultSet = prepStatement.executeQuery();
//
//                        while (resultSet.next()) {
//                            Item item = new Item(
//                                    resultSet.getInt(1),
//                                    resultSet.getString(2),
//                                    resultSet.getString(3),
//                                    resultSet.getDouble(4),
//                                    resultSet.getInt(5)
//                            );
//
//                            itemList.add(item);
//                        }
//                    } // add quantity to item from od. checked - keys are the same.
//                    double price = 0;
//                    int totalPts = 0, qty = 0;
//                    for (int i=0; i<itemList.size(); i++) {
//                        itemList.get(i).qty = orderList.get(i).qty;
//                        price += itemList.get(i).price;
//                        qty += itemList.get(i).qty;
//                        totalPts += itemList.get(i).points;
//                    }
//
//                    double totalPrice = price * qty;
//                    request.getSession().setAttribute("totalPrice", totalPrice);
//                    request.getSession().setAttribute("totalQty", qty);
//                    request.getSession().setAttribute("totalPts", totalPts);
//                    
//                    request.getSession().removeAttribute("cartList");
//                    request.getSession().setAttribute("cartList", itemList);
//
//                }
//                resultSet.close();
//                prepStatement.close();
//                connection.close();
//
//                response.sendRedirect(this.getServletContext().getContextPath() + "/cart.jsp");
//
//            } catch (SQLException ex) {
//                Logger.getLogger(CartServlet.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
        else {
            response.sendRedirect(this.getServletContext().getContextPath() + "/cart.jsp");
        }   
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // add cart. 
        
        
        Item item = getItem(Integer.parseInt(request.getParameter("itemId")));
        int itemQty = Integer.parseInt(request.getParameter("itemQty"));
        
        // session storage to store cart details
        Map<Integer, Item> odMap = new HashMap<Integer, Item>();
        
        if (request.getSession().getAttribute("odMap") != null) {
            odMap = (Map) request.getSession().getAttribute("odMap");
        }
        
        if (odMap.containsKey(item.id)) {
            Item temp = odMap.get(item.id);
            if (temp.qty + itemQty > 50) {
                request.getSession().setAttribute("cartMsg", "You have attempted to add more than 50 of the same items. Please check your cart.");
            }
            else {
                temp.qty += itemQty;
                odMap.put(item.id, temp);
                request.getSession().setAttribute("cartMsg", "Successfully added item to cart");
            }
        }
        else {
            item.qty = itemQty;
            odMap.put(item.id, item);
            request.getSession().setAttribute("cartMsg", "Successfully added item to cart");
        }
        
        System.out.println(odMap.get(item.id).qty);
        request.getSession().setAttribute("odMap", odMap);
        response.sendRedirect(this.getServletContext().getContextPath() + "/shop.jsp");
        
        
        
        
//        User user = (User)request.getSession().getAttribute("userInfo");
//        
//        Connection connection = null;
//        PreparedStatement prepStatement = null;
//        ResultSet resultSet = null;
//        Boolean unique = true;
//        
//        // create orders first
//        // followed by orderitems referencing orders's id
//        try {
//            connection = dsCart.getConnection();
//            connection.setAutoCommit(false);
//            
//            // check for existing order row, do not want to create one every time user clicks btn
//            prepStatement = connection.prepareStatement("SELECT count(*) FROM orders WHERE customerid = ?");
//            prepStatement.setInt(1, user.id);
//            
//            resultSet = prepStatement.executeQuery();
//            connection.commit();
//            
//            resultSet.next();
//            
//            if (resultSet.getInt(1) >= 1) unique = false;
//            
//            // if no existing order row, create one
//            // note that order row will be NOT updated until checkout.
//            if (unique) {
//                String sqlInsertOrder = "INSERT INTO orders (customerid, orderprice, orderpoints) VALUES (?, ?, ?)";
//            
//                prepStatement = connection.prepareStatement(sqlInsertOrder);
//                prepStatement.setInt(1, user.id);
//                prepStatement.setDouble(2, item.price);
//                prepStatement.setInt(3, item.points);
//
//                prepStatement.executeUpdate();
//                connection.commit();
//                
//            }
//            
//            // retrieve orderid of last inserted row
//            prepStatement = connection.prepareStatement("SELECT * FROM orders");
//            resultSet = prepStatement.executeQuery();
//
//            int orderid = 0;
//            if (resultSet.last()) orderid = resultSet.getInt("orderid");
//
//            // check if orderdetails with corresponding itemid exists
//            String sqlSelectOrder = "SELECT * FROM orderdetails WHERE orderid = ? AND itemid = ?";
//            prepStatement = connection.prepareStatement(sqlSelectOrder, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//            prepStatement.setInt(1, orderid);
//            prepStatement.setInt(2, item.id);
//            resultSet = prepStatement.executeQuery();
//            
//            // UPDATE existing record
//            if (resultSet.next()) {
//                int oldQty = resultSet.getInt(3);
//                if (oldQty + itemQty > 20) {
//                    request.getSession().setAttribute("cartMsg", "You have attempted to add more than 20 of the same items. Please check your cart.");
//                }
//                else {
//                    resultSet.updateInt(3, oldQty + itemQty);
//                    resultSet.updateRow();
//                    request.getSession().setAttribute("cartMsg", "Successfully added item to cart");
//                }
//            }
////            if (resultSet.getInt(1) >= 1) {
////                
////            }
//            else {
//                // else create new orderdetails row using orderid of last inserted row
//                prepStatement = connection.prepareStatement("INSERT INTO orderdetails (orderid, itemid, quantity) VALUES (?, ?, ?)");
//                prepStatement.setInt(1, orderid);
//                prepStatement.setInt(2, item.id);
//                prepStatement.setInt(3, itemQty);
//
//                prepStatement.executeUpdate();
//                connection.commit();  
//                
//                request.getSession().setAttribute("cartMsg", "Successfully added item to cart");             
//            }
//      
//            request.getSession().setAttribute("orderID", orderid);
//            prepStatement.close();
//            connection.setAutoCommit(true);
//            connection.close();
//            response.sendRedirect(this.getServletContext().getContextPath() + "/shop.jsp");
//            
//        } catch (SQLException ex) {
//            Logger.getLogger(CartServlet.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
    public Item getItem(int itemID) {
        Connection connection = null;
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null;
        Item temp = null;
        
        try {
            connection = dsCart.getConnection();
            prepStatement = connection.prepareStatement("SELECT * FROM item WHERE itemId = ?");
            prepStatement.setInt(1, itemID);
            
            resultSet = prepStatement.executeQuery();
            if (resultSet.next()) {
                temp = new Item(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getInt(5)
                );
                
                return temp;
            }
            
            resultSet.close();
            prepStatement.close();
            connection.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(CartServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return temp;
    }
    
    @Override
    public String getServletInfo() {
        return "doGet - retrieve and display cart for user. doPost - add to cart";
    }// </editor-fold>

}
