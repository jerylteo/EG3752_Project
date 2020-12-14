/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ee.mote;

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
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Resource(name = "jdbc/jed_project1")
    private DataSource dsUser;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, UnsupportedEncodingException {
        
        String email = request.getParameter("lemail");
        // old
//        String pw = getMD5(request.getParameter("lpw"));
        
        // new:
        String pw;
        try {
            pw = getSHA(request.getParameter("lpw"));
            System.out.println(pw);
        
            String sqlSelectUser = "SELECT count(*) FROM customer where email = ? AND password = ?";

            Connection connection = null;
            PreparedStatement prepStatement = null;
            ResultSet resultSet = null;
            Boolean valid = false;

            try {
                connection = dsUser.getConnection();
                connection.setAutoCommit(false);
                prepStatement = connection.prepareStatement(sqlSelectUser);

                prepStatement.setString(1, email);
                prepStatement.setString(2, pw);

                resultSet = prepStatement.executeQuery();
                resultSet.next();

                if (resultSet.getInt(1) == 1) valid = true;

                resultSet.close();
                prepStatement.close();
                connection.setAutoCommit(true);
                connection.close();

            } catch (SQLException ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {

                if (!valid) {
                    request.getSession().setAttribute("logMsg", "Incorrect Email or Password.");
                    response.sendRedirect(this.getServletContext().getContextPath() + "/login.jsp");
                }
                else  {
                    request.getSession().removeAttribute("logMsg");
                    request.getSession().setAttribute("isLoggedIn", valid);
                    request.getSession().setAttribute("logEmail", email);
                    response.sendRedirect(this.getServletContext().getContextPath() + "/shop");
                }
            }
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
    
    
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

    @Override
    public String getServletInfo() {
        return "Login servlet";
    }// </editor-fold>

}
