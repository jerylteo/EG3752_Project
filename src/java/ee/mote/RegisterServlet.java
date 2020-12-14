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
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    @Resource(name="jdbc/jed_project1")
    private DataSource dsUser;
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, UnsupportedEncodingException {
        
        // old:
//        String hashed = getMD5(request.getParameter("pw"));
        
        // new: change request - getSHA
        String hashed;
        try {
            hashed = getSHA(request.getParameter("pw"));
            
            User user = new User(
                request.getParameter("name"),
                request.getParameter("email"),
                request.getParameter("add1"),
                request.getParameter("postal"),
                request.getParameter("mobile"),
                hashed
            );

            if (!request.getParameter("add2").isEmpty()) user.add2 = request.getParameter("add2");
            else user.add2 = "";

            System.out.println(user);



            String sqlInsertUser = "INSERT INTO customer (fullname, email, addressline1, addressline2, postalcode, mobile, password) VALUES (?, ?, ?, ?, ?, ?, ?)";

            Connection connection = null;
            PreparedStatement prepStatement = null;

            try {
                System.out.println(user);

                connection = dsUser.getConnection();
                connection.setAutoCommit(false);
                prepStatement = connection.prepareStatement(sqlInsertUser);

                prepStatement.setString(1, user.name);
                prepStatement.setString(2, user.email);
                prepStatement.setString(3, user.add1);
                prepStatement.setString(4, user.add2);
                prepStatement.setString(5, user.postal);
                prepStatement.setString(6, user.mobile);
                prepStatement.setString(7, user.pw);

                prepStatement.executeUpdate();
                connection.commit();

    //            request.getSession().setAttribute("infoMsg", "Successfully registered. Please log in with your Email and Password.");

                prepStatement.close();
                connection.setAutoCommit(true);
                connection.close();

                // change this to route to shop page
                request.getSession().removeAttribute("infoMsg");
                request.getSession().setAttribute("isLoggedIn", true);
                request.getSession().setAttribute("logEmail", user.email);
                response.sendRedirect(this.getServletContext().getContextPath() + "/shop");

            } catch (SQLException ex) {
                Logger.getLogger(ValidationServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        return "Registers user after validation";
    }// </editor-fold>

}
