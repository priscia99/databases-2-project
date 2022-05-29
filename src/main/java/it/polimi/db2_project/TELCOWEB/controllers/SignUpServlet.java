package it.polimi.db2_project.TELCOWEB.controllers;

import it.polimi.db2_project.TELCOEJB.entities.UserEntity;
import it.polimi.db2_project.TELCOEJB.exceptions.CredentialsException;
import it.polimi.db2_project.TELCOEJB.exceptions.InvalidCredentialsException;
import it.polimi.db2_project.TELCOEJB.exceptions.NonUniqueResultException;
import it.polimi.db2_project.TELCOEJB.services.UserService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "signUpServlet", value = "/perform-signup")
public class SignUpServlet extends HttpServlet {
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db2_project.TELCOEJB.services/UserService")
    private UserService userService;

    public void init() throws UnavailableException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Cannot perform a GET request");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        // Get servlet context
        ServletContext context = getServletContext();

        // Retrieve username and password from request
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        if(username == null || username.isEmpty() || password == null || password.isEmpty()
        || email == null || email.isEmpty()){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or empty parameters");
            return;
        }

        // Create user entity object
        UserEntity user;
        String path = null;
        String answerMessage = "User successfully created";

        try{
            user = userService.addNewUser(username, password,email);
        }
        catch(CredentialsException e){
            answerMessage = e.getMessage();
        }

        ServletContext servletContext = getServletContext();
        WebContext webContext = new WebContext(request, response, servletContext, request.getLocale());
        webContext.setVariable("loginInfoMsg", answerMessage);
        path = "/index.html";    //Re-direct to login page again
        templateEngine.process(path, webContext, response.getWriter());
        out.close();

    }

    public void destroy() {
    }
}