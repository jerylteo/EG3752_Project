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
@WebServlet(name = "CheckoutServlet", urlPatterns = {"/checkout"})
public class CheckoutServlet extends HttpServlet {

    @Resource(name = "jdbc/jed_project1")
    private DataSource dsOrder;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // TODO : retrieves cart from sessionStorage
        Map<Integer, Item> odMap = new HashMap<Integer, Item>();
        double totalPrice = (Double) request.getSession().getAttribute("totalPrice");
        int totalPts = (int) request.getSession().getAttribute("totalPts");
        
        // no need to check for null - check exists in CartServlet.
        odMap = (Map) request.getSession().getAttribute("odMap");
        
        // TODO : inserts cart into Orders & OrderDetails DB
        // odMap contains item.id & item.
        // orders will contain totals (taken from sessionStorage) & user 
        Connection connection = null;
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null;
        
        User user = (User) request.getSession().getAttribute("userInfo");
        System.out.println(user.id);
        
        try {
            connection = dsOrder.getConnection();
            connection.setAutoCommit(false);
            prepStatement = connection.prepareStatement("INSERT INTO orders (customerid, orderprice, orderpoints) VALUES (?, ?, ?)");
            prepStatement.setInt(1, user.id);
            prepStatement.setDouble(2, totalPrice);
            prepStatement.setInt(3, totalPts);
            
            prepStatement.executeUpdate();
            connection.commit();
            
            // retrieve orderid of last inserted row
            prepStatement = connection.prepareStatement("SELECT * FROM orders");
            resultSet = prepStatement.executeQuery();
            
            
            
            // can be better
            int orderid = 0;
            if (resultSet.last()) {
                orderid = resultSet.getInt("orderid");
                request.getSession().setAttribute("receiptPrice", resultSet.getDouble("orderprice"));
                request.getSession().setAttribute("receiptPts", resultSet.getInt("orderpoints"));
            }
            
            // orderDetails will contain orderid, itemid, quantity
            prepStatement = connection.prepareStatement("INSERT INTO orderdetails (orderid, itemid, quantity) VALUES (?,?,?)");
            
            // loop through odMap and insert em all
            for (int key : odMap.keySet()) {
                Item val = odMap.get(key);
                
                prepStatement.setInt(1, orderid);
                prepStatement.setInt(2, val.id);
                prepStatement.setInt(3, val.qty);
                
                prepStatement.executeUpdate();
                connection.commit();
            }
            
            // TODO : displays receipt
            
            
            // TODO : Close all DB connections
            resultSet.close();
            prepStatement.close();
            connection.close();
            request.getSession().removeAttribute("odMap");
            request.getSession().removeAttribute("totalPrice");
            request.getSession().removeAttribute("totalPts");
            request.getSession().removeAttribute("totalQty");
            
            response.sendRedirect(this.getServletContext().getContextPath() + "/checkout.jsp");
        } catch (SQLException ex) {
            Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE, null, ex);
    }
        
//        Connection connection = null;
//        PreparedStatement prepStatement = null;
//        ResultSet resultSet = null;
//        int orderID;
//        
//        User user = (User) request.getSession().getAttribute("userInfo");
//        try {
//            // update orders
//            connection = dsOrder.getConnection();
//            connection.setAutoCommit(false);
//            prepStatement = connection.prepareStatement("SELECT * FROM orders WHERE customerid = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//            prepStatement.setInt(1, user.id);
//            resultSet = prepStatement.executeQuery();
//            
//            if (resultSet.next()) {
//                double totalPrice = (double) request.getSession().getAttribute("totalPrice");
//                int totalPts = (int) request.getSession().getAttribute("totalPts");
//                resultSet.updateDouble(3, totalPrice);
//                resultSet.updateInt(4, totalPts);
//                resultSet.updateRow();
//                connection.commit();
//
//                
//                // delete orderdetails
//                orderID = resultSet.getInt(1);
//                prepStatement = connection.prepareStatement("SELECT * FROM orderdetails WHERE orderid = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//                prepStatement.setInt(1, orderID);
//                
//                resultSet = prepStatement.executeQuery();
//                
//                while (resultSet.next()) {
//                    resultSet.deleteRow();
//                }
//                
//            }
//            
//            resultSet.close();
//            prepStatement.close();
//            connection.setAutoCommit(true);
//            connection.close();
//            
//            response.sendRedirect(this.getServletContext().getContextPath() + "/checkout.jsp");
//            // return name of user, total points & total amount
//        } catch (SQLException ex) {
//            Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE, null, ex);
//        }



//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
}

            
            
            
            
            
            



    @Override
    public String getServletInfo() {
        return "Displays checkout status";
    }// </editor-fold>

}
