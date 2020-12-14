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
import java.util.ArrayList;
import java.util.List;
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
@WebServlet(name = "ShopServlet", urlPatterns = {"/shop"})
public class ShopServlet extends HttpServlet {

    @Resource(name = "jdbc/jed_project1")
    private DataSource dsItemCatalogue;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String sqlSelectItems = "SELECT * FROM item";
        
        Connection connection = null;
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = dsItemCatalogue.getConnection();
            prepStatement = connection.prepareStatement(sqlSelectItems);
            resultSet = prepStatement.executeQuery();
            
            List<Item> itemList = new ArrayList<>();
            
            while(resultSet.next()) {
                Item item = new Item(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString (3),
                        resultSet.getDouble(4),
                        resultSet.getInt(5)
                );
                
                itemList.add(item);
            }
            
            System.out.println(itemList.size());
            resultSet.close();
            prepStatement.close();
            connection.close();
           
            if (request.getSession().getAttribute("searchTerm") != null) request.getSession().removeAttribute("searchTerm");
            request.getSession().setAttribute("itemList", itemList);
            response.sendRedirect(this.getServletContext().getContextPath() + "/shop.jsp");
            
        } catch (SQLException ex) {
            Logger.getLogger(ShopServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Connection connection = null;
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null;
        
        try {
            List<Item> itemList = new ArrayList<>();
            connection = dsItemCatalogue.getConnection();
            
            String sqlSelectItems = "SELECT * FROM item WHERE itemDescription LIKE ?";
            prepStatement = connection.prepareStatement(sqlSelectItems);
            prepStatement.setString(1, "%" + request.getParameter("searchTerm") + "%");
            
            resultSet = prepStatement.executeQuery();
            
            while(resultSet.next()) {
                Item item = new Item(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString (3),
                        resultSet.getDouble(4),
                        resultSet.getInt(5)
                );

                itemList.add(item);
            }
            
            resultSet.close();
            prepStatement.close();
            connection.close();
           
            request.getSession().setAttribute("searchTerm", request.getParameter("searchTerm"));
            request.getSession().setAttribute("itemList", itemList);
            response.sendRedirect(this.getServletContext().getContextPath() + "/shop.jsp");
            
        } catch (SQLException ex) {
            Logger.getLogger(ShopServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public String getServletInfo() {
        return "doGet = retrieve all items from table item. doPost = search for item";
    }// </editor-fold>

}
