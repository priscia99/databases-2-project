package it.polimi.db2_project.TELCOWEB.controllers;

import it.polimi.db2_project.TELCOEJB.entities.OrderEntity;
import it.polimi.db2_project.TELCOEJB.entities.ServicePackageEntity;
import it.polimi.db2_project.TELCOEJB.entities.UserEntity;
import it.polimi.db2_project.TELCOEJB.enums.OrderState;
import it.polimi.db2_project.TELCOEJB.exceptions.ServicePackageException;
import it.polimi.db2_project.TELCOEJB.services.ServicePackageService;
import it.polimi.db2_project.TELCOEJB.services.UserService;
import it.polimi.db2_project.TELCOEJB.utils.ConnectionHandler;

import java.io.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
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


@WebServlet(name = "HomePageServlet", value = "/home")
public class HomePageServlet extends HttpServlet {
    private Connection connection = null;
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db2_project.TELCOEJB.services/ServicePackageService")
    private ServicePackageService servicePackageService;
    @EJB(name = "it.polimi.db2_project.TELCOEJB.services/UserService")
    private UserService userService;

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
        // set request encoding to match the project character encoding (utf-8)
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        // get user information from session
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user != null) {
            // if user exists, get the entire object
            user = userService.findUserByUsername(user.getUsername());
        }

        // Retrieve all packages
        ArrayList<ServicePackageEntity> servicePackages = null;
        try {
            servicePackages = servicePackageService.getAllPackages();
        } catch (ServicePackageException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while retrieving the list of service packages");
        }

        // get servlet context and prepare the redirect path
        String path = "/home.html";
        ServletContext servletContext = getServletContext();
        final WebContext context = new WebContext(request, response, servletContext, request.getLocale());

        // add user information to the context
        context.setVariable("user", user);

        // add the service packages retrieved to the context
        context.setVariable("servicePackages", servicePackages);

        //check if the user is insolvent
        List<OrderEntity> rejectedOrders = new ArrayList<>();
        if(user != null && user.isInsolvent()){
            List<OrderEntity> allOrders = user.getOrderEntities();
            for(int i = 0; i < allOrders.size(); i++){
                if(allOrders.get(i).getOrderState() == OrderState.REJECTED){
                    rejectedOrders.add(allOrders.get(i));
                }
            }
            context.setVariable("rejectedOrders",rejectedOrders);
        }
        templateEngine.process(path, context, response.getWriter());
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        doGet(request, response);
    }

    public void destroy() {
        try{
            ConnectionHandler.closeConnection(connection);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}