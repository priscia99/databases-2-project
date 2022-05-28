package it.polimi.db2_project.TELCOWEB.controllers;

import it.polimi.db2_project.TELCOEJB.entities.*;
import it.polimi.db2_project.TELCOEJB.enums.OrderState;
import it.polimi.db2_project.TELCOEJB.exceptions.OptionalProductException;
import it.polimi.db2_project.TELCOEJB.exceptions.ServiceException;
import it.polimi.db2_project.TELCOEJB.exceptions.ServicePackageException;
import it.polimi.db2_project.TELCOEJB.services.OptionalProductService;
import it.polimi.db2_project.TELCOEJB.services.PeriodService;
import it.polimi.db2_project.TELCOEJB.services.ServicePackageService;
import it.polimi.db2_project.TELCOEJB.services.ServiceService;
import it.polimi.db2_project.TELCOEJB.utils.ConnectionHandler;

import java.io.*;
import java.sql.Array;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.UnavailableException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;


@WebServlet(name = "AdminCreatePackageServlet", value = "/admin/create-package")
public class AdminCreatePackageServlet extends HttpServlet {
    private Connection connection = null;
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db2_project.TELCOEJB.services/ServiceService")
    private ServiceService serviceService;
    @EJB(name = "it.polimi.db2_project.TELCOEJB.services/OptionalProductService")
    private OptionalProductService optionalProductService;
    @EJB(name = "it.polimi.db2_project.TELCOEJB.services/ServicePackageService")
    private ServicePackageService servicePackageService;
    @EJB(name = "it.polimi.db2_project.TELCOEJB.services/PeriodService")
    private PeriodService periodService;

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

        // Fetching request parameters
        String packageName = request.getParameter("servicePackageName");
        String[] validityPeriods = request.getParameterValues("servicePackageValidityPeriod");
        String[] monthlyFees = request.getParameterValues("servicePackageMonthlyFee");
        String[] chosenServices = request.getParameterValues("chosenServices");
        String[] optionalProducts = request.getParameterValues("chosenOptionalProducts");

        // check if some parameters are missing
        if(packageName == null || validityPeriods == null || monthlyFees == null || chosenServices == null || validityPeriods.length == 0 || monthlyFees.length == 0 || chosenServices.length == 0){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Some parameters are missing.");
            return;
        }

        // double check the user inputs
        if(validityPeriods.length != monthlyFees.length){
            // if the two lengths are not matching, then return an error with BAD REQUEST to the user
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "The array lenghts are not the same");
            return;
        }

        // Retrieving the list of optional products that are connected to the service package
        ArrayList<OptionalProductEntity> optionalProductEntities = null;
        if(optionalProducts != null) {
            try {
                optionalProductEntities = (ArrayList<OptionalProductEntity>) optionalProductService.getListOptionalProducts(Arrays.asList(optionalProducts));
            } catch (OptionalProductException e) {
                e.printStackTrace();
            }
        }

        // Retrieving the list of services that are connected to the service package
        ArrayList<ServiceEntity> serviceEntities = null;
        try {
            serviceEntities = (ArrayList<ServiceEntity>) serviceService.getListServices(Arrays.asList(chosenServices));
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        // Create the new service package entity
        ServicePackageEntity newServicePackage = new ServicePackageEntity(packageName, serviceEntities, optionalProductEntities);
        ArrayList<ServicePackageEntity> createdPackageWithPeriods = new ArrayList<>();

        // Create the new validity periods entities
        ArrayList<PeriodEntity> newPeriodEntities = new ArrayList<>();
        for(int i=0; i<validityPeriods.length; i++){
            newPeriodEntities.add(
                    new PeriodEntity(
                            Integer.parseInt(validityPeriods[i]),
                            Float.parseFloat(monthlyFees[i]),
                            newServicePackage
                    )
            );
        }

        // Single transaction that inserts the package service entity and the associated period entities
        try {
            // begin of the transaction
            userTransaction.begin();
            // persist all the new service packages
            servicePackageService.persistServicePackage(newServicePackage);
            // persist all the new periods
            periodService.persistPeriods(newPeriodEntities);
            // commit
            userTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try{
                // in case of error, rollback the transaction
                userTransaction.rollback();
            } catch (SystemException systemException) {
                e.printStackTrace();
            }
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
            e.printStackTrace();
        }

        // retrieve the list of all optional products
        try {
            allOptionalProducts = optionalProductService.getAllOptionalProducts();
        } catch (OptionalProductException e) {
            e.printStackTrace();
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