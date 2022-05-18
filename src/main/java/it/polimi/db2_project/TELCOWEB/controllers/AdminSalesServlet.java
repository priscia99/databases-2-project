package it.polimi.db2_project.TELCOWEB.controllers;

import it.polimi.db2_project.TELCOEJB.entities.*;
import it.polimi.db2_project.TELCOEJB.enums.OrderState;
import it.polimi.db2_project.TELCOEJB.exceptions.AdminViewsException;
import it.polimi.db2_project.TELCOEJB.exceptions.OptionalProductException;
import it.polimi.db2_project.TELCOEJB.exceptions.ServiceException;
import it.polimi.db2_project.TELCOEJB.exceptions.ServicePackageException;
import it.polimi.db2_project.TELCOEJB.services.OptionalProductService;
import it.polimi.db2_project.TELCOEJB.services.ServicePackageService;
import it.polimi.db2_project.TELCOEJB.services.ServiceService;
import it.polimi.db2_project.TELCOEJB.services.TotalPurchasesPerPackageService;
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


@WebServlet(name = "AdminSalesServlet", value = "/admin/sales")
public class AdminSalesServlet extends HttpServlet {
    private Connection connection = null;
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db2_project.TELCOEJB.services/TotalPurchasesPerPackageService")
    private TotalPurchasesPerPackageService totalPurchasesPerPackageService;

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

        // Get context
        String path = "/admin/sales.html";
        ServletContext servletContext = getServletContext();
        final WebContext context = new WebContext(request, response, servletContext, request.getLocale());

        // Get total purchases per packages
        ArrayList<TotalPurchasesPerPackageEntity> totalPurchasesPerPackage = null;
        try {
            totalPurchasesPerPackage = (ArrayList<TotalPurchasesPerPackageEntity>) totalPurchasesPerPackageService.getTotalPurchasesPerPackage();
        } catch (AdminViewsException e) {
            e.printStackTrace();
        }

        context.setVariable("totalPurchasesPerPackage",totalPurchasesPerPackage);
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