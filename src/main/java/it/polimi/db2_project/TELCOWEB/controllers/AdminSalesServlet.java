package it.polimi.db2_project.TELCOWEB.controllers;

import it.polimi.db2_project.TELCOEJB.entities.*;
import it.polimi.db2_project.TELCOEJB.exceptions.AdminViewsException;
import it.polimi.db2_project.TELCOEJB.services.*;
import it.polimi.db2_project.TELCOEJB.utils.ConnectionHandler;

import java.io.*;
import java.sql.Connection;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.UnavailableException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;


@WebServlet(name = "AdminSalesServlet", value = "/admin/sales")
public class AdminSalesServlet extends HttpServlet {
    private Connection connection = null;
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db2_project.TELCOEJB.services/TotalPurchasesPerPackageService")
    private TotalPurchasesPerPackageService totalPurchasesPerPackageService;

    @EJB(name = "it.polimi.db2_project.TELCOEJB.services/TotalPurchasesPerPackageValidityPeriodService")
    private TotalPurchasesPerPackageValidityPeriodService totalPurchasesPerPackageValidityPeriodService;

    @EJB(name = "it.polimi.db2_project.TELCOEJB.services/TotalSalesPerPackageService")
    private TotalSalesPerPackageService totalSalesPerPackageService;

    @EJB(name = "it.polimi.db2_project.TELCOEJB.services/AverageSalesOptionalProductPerServicePackageService")
    private AverageSalesOptionalProductPerServicePackageService averageSalesOptionalProductPerServicePackageService;

    @EJB(name = "it.polimi.db2_project.TELCOEJB.services/InsolventUsersService")
    private InsolventUsersService insolventUsersService;

    @EJB(name = "it.polimi.db2_project.TELCOEJB.services/SuspendedOrdersService")
    private SuspendedOrdersService suspendedOrdersService;

    @EJB(name = "it.polimi.db2_project.TELCOEJB.services/AlertService")
    private AlertService alertService;

    @EJB(name = "it.polimi.db2_project.TELCOEJB.services/BestsellerOptionalProductEntity")
    private BestsellerOptionalProductService bestsellerOptionalProductService;

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

        // Get context
        String path = "/admin/sales.html";
        ServletContext servletContext = getServletContext();
        final WebContext context = new WebContext(request, response, servletContext, request.getLocale());

        // Get total purchases per packages
        ArrayList<TotalPurchasesPerPackageEntity> totalPurchasesPerPackage = null;
        try {
            totalPurchasesPerPackage = (ArrayList<TotalPurchasesPerPackageEntity>) totalPurchasesPerPackageService.getTotalPurchasesPerPackage();
        } catch (AdminViewsException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while loading the list of total purchases per service package");
        }

        // Get total purchases per packages and validity period
        ArrayList<TotalPurchasesPerPackageValidityPeriodEntity> totalPurchasesPerPackageValidityPeriod = null;
        try {
            totalPurchasesPerPackageValidityPeriod = (ArrayList<TotalPurchasesPerPackageValidityPeriodEntity>) totalPurchasesPerPackageValidityPeriodService.getTotalPurchasesPerPackageValidityPeriod();
        } catch (AdminViewsException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while loading the list of total purchases per service package and validity period");
        }

        // Get total sales per package with and without optional products
        ArrayList<TotalSalesPerPackageEntity> totalSalesPerPackage = null;
        try {
            totalSalesPerPackage = (ArrayList<TotalSalesPerPackageEntity>) totalSalesPerPackageService.getTotalSalesPerPackage();
        } catch (AdminViewsException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while loading the list of total sales per service package");
        }

        // Get average sales of optional product per service package
        ArrayList<AverageSalesOptionalProductPerServicePackageEntity> averageSalesOptionalProductPerServicePackage = null;
        try {
            averageSalesOptionalProductPerServicePackage = (ArrayList<AverageSalesOptionalProductPerServicePackageEntity>) averageSalesOptionalProductPerServicePackageService.getAverageSales();
        } catch (AdminViewsException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while loading the list of average sales of optional products per service package");
        }

        // Get list of insolvent users
        ArrayList<InsolventUsersEntity> insolventUsers = null;
        try {
            insolventUsers = (ArrayList<InsolventUsersEntity>) insolventUsersService.getInsolventUsers();
        } catch (AdminViewsException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while loading the list of insolvent users");
        }

        // Get list of suspended orders
        ArrayList<SuspendedOrdersEntity> suspendedOrders = null;
        try {
            suspendedOrders = (ArrayList<SuspendedOrdersEntity>) suspendedOrdersService.getSuspendedOrders();
        } catch (AdminViewsException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while loading the list of suspended orders");
        }

        // Get list of alerts
        ArrayList<AlertEntity> alerts = null;
        try {
            alerts = (ArrayList<AlertEntity>) alertService.getAlerts();
        } catch (AdminViewsException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while loading the list of alerts");
        }

        // Get bestseller optional product
        BestsellerOptionalProductEntity bestsellerOptionalProduct = null;
        try {
            bestsellerOptionalProduct = bestsellerOptionalProductService.getBestsellerProduct();
        } catch (AdminViewsException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while loading the best-seller optional product");
        }

        context.setVariable("totalPurchasesPerPackage",totalPurchasesPerPackage);
        context.setVariable("totalPurchasesPerPackageValidityPeriod",totalPurchasesPerPackageValidityPeriod);
        context.setVariable("totalSalesPerPackage",totalSalesPerPackage);
        context.setVariable("averageSalesOptionalProductPerServicePackage",averageSalesOptionalProductPerServicePackage);
        context.setVariable("insolventUsers",insolventUsers);
        context.setVariable("suspendedOrders",suspendedOrders);
        context.setVariable("alerts",alerts);
        context.setVariable("bestsellerOptionalProduct",bestsellerOptionalProduct);
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