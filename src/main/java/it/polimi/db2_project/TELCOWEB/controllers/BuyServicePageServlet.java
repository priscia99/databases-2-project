package it.polimi.db2_project.TELCOWEB.controllers;

import it.polimi.db2_project.TELCOEJB.entities.ServicePackageEntity;
import it.polimi.db2_project.TELCOEJB.entities.UserEntity;
import it.polimi.db2_project.TELCOEJB.exceptions.ServicePackageException;
import it.polimi.db2_project.TELCOEJB.services.ServicePackageService;
import it.polimi.db2_project.TELCOEJB.services.UserService;
import it.polimi.db2_project.TELCOEJB.utils.ConnectionHandler;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.UnavailableException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;


@WebServlet(name = "BuyServicePageServlet", value = "/buyservice")
public class BuyServicePageServlet extends HttpServlet {
    private Connection connection = null;
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db2_project.TELCOEJB.services/ServicePackageService")
    private ServicePackageService servicePackageService;


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
// set request encoding to match the project character encoding (utf-8)
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String path = "/buyservice.html";
        ServletContext servletContext = getServletContext();
        final WebContext context = new WebContext(request, response, servletContext, request.getLocale());


        UserEntity user = (UserEntity) session.getAttribute("user");
        List<ServicePackageEntity> packages = null;
            try {
                packages = servicePackageService.getAllPackages();
            } catch (ServicePackageException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            }
        String chosenPackageId = request.getParameter("chosenPackageId");
        List<ServicePackageEntity> chosenPackages = null;
        if(!(chosenPackageId == null ||chosenPackageId.isEmpty() || chosenPackageId.isBlank())) {
            try {
                chosenPackages = servicePackageService.getPackagesById(chosenPackageId);
                context.setVariable("chosenPackages", chosenPackages);
            } catch (ServicePackageException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            }
        }

//        // Redirect to the Home page and add missions to the parameters
//        String path = "/buyservice.html";
//        ServletContext servletContext = getServletContext();
//        final WebContext context = new WebContext(request, response, servletContext, request.getLocale());
        context.setVariable("user", user);
        context.setVariable("packages", packages);
        templateEngine.process(path, context, response.getWriter());
    }

    public void destroy() {
        try{
            ConnectionHandler.closeConnection(connection);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}