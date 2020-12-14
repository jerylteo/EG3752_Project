package ee.mote;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import ee.mote.User;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
@WebServlet(urlPatterns = {"/profile"})
public class ProfileServlet extends HttpServlet {

    @Resource(name = "jdbc/jed_project1")
    private DataSource dsUser;
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // should've done this in login tbh. inefficient.
        
        if ((Boolean)request.getSession().getAttribute("isLoggedIn")) {
            if (request.getSession().getAttribute("logEmail") != null) {
                String email = request.getSession().getAttribute("logEmail").toString();
                
                String sqlSelectUser = "SELECT * FROM customer WHERE email = ?";
                
                Connection connection = null;
                PreparedStatement prepStatement = null;
                ResultSet resultSet = null;
                
                try {
                    connection = dsUser.getConnection();
                    prepStatement = connection.prepareStatement(sqlSelectUser);
                    
                    prepStatement.setString(1, email);
                    
                    resultSet = prepStatement.executeQuery();
                    
                    resultSet.next();
                    
                    User user = new User(
                            resultSet.getInt("customerId"),
                            resultSet.getString("fullname"),
                            resultSet.getString("email"),
                            resultSet.getString("addressline1"),
                            resultSet.getString("addressline2"),
                            resultSet.getString("postalcode"),
                            resultSet.getString("mobile"),
                            resultSet.getString("password")
                    );
                    
                    // retrieve total points
                    prepStatement = connection.prepareStatement("SELECT * FROM orders WHERE customerid = ?");
                    prepStatement.setInt(1, user.id);
                    resultSet = prepStatement.executeQuery();
                    int points = 0;
                    while (resultSet.next()) {
                        points += resultSet.getInt("orderpoints");
                    }
                    user.setPoints(points);
                    System.out.println(user.getPoints());

                    resultSet.close();
                    prepStatement.close();
                    connection.close();
                    
                    request.getSession().setAttribute("userInfo", user);
                    System.out.println(user.add2);
                    response.sendRedirect(this.getServletContext().getContextPath() + "/profile.jsp");
                    
                } catch (SQLException ex) {
                    Logger.getLogger(ProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
                } 
                
                
            }   
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, UnsupportedEncodingException {

        User user = (User) request.getSession().getAttribute("userInfo");
        
        String hashed;
        try {
            hashed = getSHA(request.getParameter("pw"));
            
            Connection connection = null;
            PreparedStatement prepStatement = null;
            
            try {
                connection = dsUser.getConnection();
                
                // retreieve current pw check
                if (isCurrentPw(getSHA(request.getParameter("currentPw")), user)) {
                    // update new pw
                    prepStatement = connection.prepareStatement("UPDATE customer SET password = ? WHERE customerId = ?");

                    prepStatement.setString(1, hashed);
                    prepStatement.setInt(2, user.id);

                    prepStatement.executeUpdate();

                    prepStatement.close();
                    connection.close();

                    request.getSession().setAttribute("profileMsg", "Successfully changed password");
                    response.sendRedirect(this.getServletContext().getContextPath() + "/profile");
                    
                } // end-if
                else {
                    request.getSession().setAttribute("profileMsg", "Incorrect current password.");
                    response.sendRedirect(this.getServletContext().getContextPath() + "/profile");
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
            } //end-try
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
        } //end-try
    }

    public static String getSHA(String text) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        // change request - SHA-256
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashed = md.digest(text.getBytes(StandardCharsets.UTF_8));

        BigInteger number = new BigInteger(1, hashed);
        StringBuilder hex = new StringBuilder(number.toString(16));
        
        // pad with 0
        while (hex.length() < 32) {
            hex.insert(0, "0");
        }
        
        return hex.toString();
        
        // old
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            byte[] messageDigest = md.digest(text.getBytes());
//            BigInteger no = new BigInteger(1, messageDigest);
//            
//            String hashed = no.toString(16);
//            while (hashed.length() < 32) {
//                hashed = "0" + hashed;
//            }
            
//        return hashed;
        
    }
    
    public boolean isCurrentPw(String pw, User user) {
    
        Connection connection = null;
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null;
        Boolean proceed = false;

        try {
            connection = dsUser.getConnection();
            prepStatement = connection.prepareStatement("SELECT count(*) FROM customer WHERE customerId = ? AND password = ?");
            
            prepStatement.setInt(1, user.id);
            prepStatement.setString(2, pw);
            
            System.out.println(user.id + ", " + pw);
            
            resultSet = prepStatement.executeQuery();
            resultSet.next();
            
            // checks if result >= 1. if 0 = invalid pw
            if (resultSet.getInt(1) >= 1) proceed = true;
            
            resultSet.close();
            prepStatement.close();
            connection.close();
            
            return proceed;
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    @Override
    public String getServletInfo() {
        return "Retrieve and display user's information";
    }// </editor-fold>

}
