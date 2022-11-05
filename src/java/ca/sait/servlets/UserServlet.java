/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ca.sait.servlets;

import ca.sait.models.Role;
import ca.sait.models.User;
import ca.sait.services.RoleService;
import ca.sait.services.UserService;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Rehan
 */
public class UserServlet extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserService userService = new UserService();

        try {
            List<User> userArray = userService.getAll();

            request.setAttribute("userArray", userArray);

            this.getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);

        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        UserService service = new UserService();

        if (action.equals("edit")) {

            /*
            Had the rough idea but couldn't figure out how to implement it.
            Not even sure if what i have below is right.
             */
            User user = new User();

            //Maybe should've been request.getParameter() instead?
            String email = user.getEmail();
            boolean active = user.isActive();
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String password = user.getPassword();
            Role role = user.getRole();

            //edits parameters?
            user.setEmail(email);
            user.setActive(active);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPassword(password);
            user.setRole(role);

            //update database?
            try {
                service.update(email, active, firstName, lastName, password, role);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (action.equals("add")) {
            /*
            Had the rough idea but couldn't figure out how to implement it.
            Not even sure if what i have below is right.
             */

            User user = new User();

            //Maybe should've been request.getParameter() instead?
            String email = user.getEmail();
            boolean active = user.isActive();
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String password = user.getPassword();
            Role role = user.getRole();
            
            //fill in inputs?
            user.setEmail(email);
            user.setActive(active);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPassword(password);
            user.setRole(role);
            
            //adds user inot database?
            try {
                service.insert(email, active, firstName, lastName, password, role);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (action.equals("delete")) {
            /*
            Had the rough idea but couldn't figure out how to implement it.
            Not even sure if what i have below is right.
             */

            User user = new User();

            //Maybe should've been request.getParameter() instead?
            String email = user.getEmail();
            
            //delete from database?
            try {
                service.delete(email);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);

    }

}
