package it.polimi.db2_project.TELCOWEB.controllers;

import it.polimi.db2_project.TELCOEJB.entities.UserEntity;
import it.polimi.db2_project.TELCOEJB.services.UserService;
import it.polimi.db2_project.TELCOEJB.utils.ConnectionHandler;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.UnavailableException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/perform-login")
public class LoginServlet extends HttpServlet {
    private Connection connection = null;

    public void init() throws UnavailableException {
        connection = ConnectionHandler.getConnection(getServletContext());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        // Get servlet context
        ServletContext context = getServletContext();

        // Initialize variables
        String username = null;
        String password = null;

        try{
            // Retrieve username and password from request
            username = request.getParameter("username");
            password = request.getParameter("password");
            if(username == null || username.isEmpty() || password == null || password.isEmpty()){
                throw new Exception("Missing or empty parameters");
            }
        }catch (Exception e){
            out.println(e.getMessage());
            out.close();
            return;
        }

        // Perform user authentication using DAO
        UserService userDAO = new UserService(connection);
        UserEntity user = null;
        try{
            user = userDAO.performLogin(username, password);
        }catch (SQLException e){
            out.println("Error while checking credentials, try again.");
            return;
        }

        // Check if user exists
        if(user == null){
            // Username or password incorrect
            out.println("Incorrect username or password!");
        }else{
            // User successfully logged
            out.println("Welcome, " + user.getUsername() + "!");
        }
        out.close();
    }

    public void destroy() {
        try{
            ConnectionHandler.closeConnection(connection);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}