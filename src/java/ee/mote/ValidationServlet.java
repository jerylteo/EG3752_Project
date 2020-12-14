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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.apache.commons.validator.EmailValidator;

/**
 *
 * @author klmch
 */
@WebServlet(name = "ValidationServlet", urlPatterns = {"/validate"})
public class ValidationServlet extends HttpServlet {

    @Resource(name="jdbc/jed_project1")
    private DataSource dsUser;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        EmailValidator emailValidator = EmailValidator.getInstance();
        
        if (emailValidator.isValid(email)) {
            if (emailExist(email)) {
//                request.getSession().removeAttribute("errMsg");
                request.getSession().setAttribute("errMsg", "");
                RequestDispatcher rd = request.getRequestDispatcher("/register");
                rd.forward(request, response);
            } 
            else {
                request.getSession().setAttribute("errMsg", "Email has already been registered.");
                response.sendRedirect(this.getServletContext().getContextPath() + "/login.jsp");
            }
        }
        else {
            request.getSession().setAttribute("errMsg", "Invalid Email.");
            response.sendRedirect(this.getServletContext().getContextPath() + "/login.jsp");
        }
    }
    
    public boolean emailExist(String email) {
        Connection connection = null;
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null;
        Boolean unique = true;
        
        try {
            connection = dsUser.getConnection();
            prepStatement = connection.prepareStatement("SELECT count(*) FROM customer WHERE email = ?");
            
            prepStatement.setString(1, email);
            
            resultSet = prepStatement.executeQuery();
            resultSet.next();
            
            if (resultSet.getInt(1) >= 1) unique = false;
            
            resultSet.close();
            prepStatement.close();
            connection.close();
            
            return unique;
            
        } catch (SQLException ex) {
            Logger.getLogger(ValidationServlet.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public String getServletInfo() {
        return "Validate user registration info";
    }// </editor-fold>

}
