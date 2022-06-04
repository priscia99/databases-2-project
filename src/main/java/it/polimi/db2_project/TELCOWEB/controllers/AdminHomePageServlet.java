package it.polimi.db2_project.TELCOWEB.controllers;

import it.polimi.db2_project.TELCOEJB.entities.*;
import it.polimi.db2_project.TELCOEJB.exceptions.OptionalProductException;
import it.polimi.db2_project.TELCOEJB.exceptions.ServiceException;
import it.polimi.db2_project.TELCOEJB.services.OptionalProductService;
import it.polimi.db2_project.TELCOEJB.services.ServiceService;
import it.polimi.db2_project.TELCOEJB.utils.ConnectionHandler;

import java.io.*;
import java.sql.Connection;
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


@WebServlet(name = "AdminHomePageServlet", value = "/admin/home")
public class AdminHomePageServlet extends HttpServlet {
    private Connection connection = null;
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db2_project.TELCOEJB.services/ServiceService")
    private ServiceService serviceService;
    @EJB(name = "it.polimi.db2_project.TELCOEJB.services/OptionalProductService")
    private OptionalProductService optionalProductService;

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

        // get employee information from the session
        EmployeeEntity employee = (EmployeeEntity) session.getAttribute("employee");
        String loginPath = "../index.html";

        // if the employee is not already logged, redirect to the login page
        if(employee == null){
            response.sendRedirect(loginPath);
        }

        // get servlet context and prepare the redirect path
        ServletContext servletContext = getServletContext();
        String path = "/admin/home.html";

        final WebContext context = new WebContext(request, response, servletContext, request.getLocale());
        context.setVariable("employee", employee);
        List <ServiceEntity> allServices = null;
        List <OptionalProductEntity> allOptionalProducts = null;

        // retrieve the list of all services
        try {
            allServices = serviceService.getAllServices();
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while trying to retrieve the list of services");
        }

        // retrieve the list of all optional products
        try {
            allOptionalProducts = optionalProductService.getAllOptionalProducts();
        } catch (OptionalProductException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while trying to retrieve the list of optional products");
        }

        // prepare the variables of the context
        context.setVariable("allServices",allServices);
        context.setVariable("allOptionalProducts",allOptionalProducts);

        // process the page
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