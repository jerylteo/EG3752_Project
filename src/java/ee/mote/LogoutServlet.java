/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ee.mote;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author klmch
 */
@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("isLoggedIn") != null) {
            if (((Boolean)request.getSession().getAttribute("isLoggedIn"))) {
                request.getSession().removeAttribute("isLoggedIn");
            }
        }
        
        response.sendRedirect(this.getServletContext().getContextPath() + "/index.jsp");
    }

    
    @Override
    public String getServletInfo() {
        return "Log user outs";
    }// </editor-fold>

}
