package it.polimi.db2_project.TELCOWEB.controllers;

import it.polimi.db2_project.TELCOEJB.entities.*;
import it.polimi.db2_project.TELCOEJB.services.OptionalProductService;
import it.polimi.db2_project.TELCOEJB.services.ServiceService;
import it.polimi.db2_project.TELCOEJB.utils.ConnectionHandler;

import java.io.*;
import java.sql.Connection;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.UnavailableException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.transaction.UserTransaction;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;


@WebServlet(name = "AdminCreateOptionalServlet", value = "/admin/create-optional")
public class AdminCreateOptionalServlet extends HttpServlet {
    private Connection connection = null;
    private TemplateEngine templateEngine;


    @EJB(name = "it.polimi.db2_project.TELCOEJB.services/OptionalProductService")
    private OptionalProductService optionalProductService;
    @EJB(name = "it.polimi.db2_project.TELCOEJB.services/ServiceService")
    private ServiceService serviceService;

    @Resource
    private UserTransaction userTransaction;

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
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Cannot perform a GET request");
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

        // Fetching request parameters
        String optionalProductName = request.getParameter("optionalProductName");
        Integer optionalProductMonthlyFee = null;
        try {
            optionalProductMonthlyFee = Integer.valueOf(request.getParameter("optionalProductMonthlyFee"));
        }catch (Exception e){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Monthly fee is not an integer value.");
        }
        if(optionalProductName == null || optionalProductMonthlyFee == null || optionalProductName.length() == 0){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Some parameters are missing.");
            return;
        }

        // persist the new optional product created
        optionalProductService.persistOptionalProduct(new OptionalProductEntity(optionalProductName,optionalProductMonthlyFee));

        response.sendRedirect("home");
    }

    public void destroy() {
        try{
            ConnectionHandler.closeConnection(connection);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}