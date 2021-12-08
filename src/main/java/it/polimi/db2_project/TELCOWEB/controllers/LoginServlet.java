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

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;


@WebServlet(name = "helloServlet", value = "/perform-login")
public class LoginServlet extends HttpServlet {
    private Connection connection = null;
    private TemplateEngine templateEngine;

    public void init() throws UnavailableException {
        connection = ConnectionHandler.getConnection(getServletContext());
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        request.setCharacterEncoding("UTF-8");
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

        String path;
        // Check if user exists
        if(user == null) {
            // Username or password incorrect -> return to login page
            ServletContext servletContext = getServletContext();
            final WebContext webContext = new WebContext(request, response, servletContext, request.getLocale());
            webContext.setVariable("loginInfoMsg", "Incorrect username or password. Try again.");
            path = "/index.html";	//Re-direct to login page again
            templateEngine.process(path, webContext, response.getWriter());
        }
        else{
            // User is an actual object -> user authenticated successfully
            request.getSession().setAttribute("user", user);	// Create a new session giving the user object as an attribute
            path = getServletContext().getContextPath() + "/home";	// Re-direct to home page
            response.sendRedirect(path);
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