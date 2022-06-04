package it.polimi.db2_project.TELCOWEB.controllers;

import it.polimi.db2_project.TELCOEJB.entities.ServicePackageEntity;
import it.polimi.db2_project.TELCOEJB.entities.UserEntity;
import it.polimi.db2_project.TELCOEJB.exceptions.ServicePackageException;
import it.polimi.db2_project.TELCOEJB.services.OptionalProductService;
import it.polimi.db2_project.TELCOEJB.services.ServicePackageService;
import it.polimi.db2_project.TELCOEJB.utils.ConnectionHandler;

import java.io.*;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
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

        // get servlet context and prepare the redirect path
        String path = "/buyservice.html";
        ServletContext servletContext = getServletContext();
        final WebContext context = new WebContext(request, response, servletContext, request.getLocale());

        String chosenPackageId = (String) request.getParameter("chosenPackageId");
        UserEntity user = (UserEntity) session.getAttribute("user");
        session.removeAttribute("order");
        ArrayList<ServicePackageEntity> servicePackages = null;

        // get list of all service packages
        try {
            servicePackages = servicePackageService.getAllPackages();
        } catch (ServicePackageException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while loading the list of service packages");
        }

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        Date tomorrow = new Date();
        tomorrow.setYear(tomorrow.getYear()+1);

        // retrieve the chosen service package entity in case the user has already selected a package ID
        ServicePackageEntity sp = null;
        if(chosenPackageId != null){
            try {
                // retrieve the chosen service package entity
                sp = servicePackageService.getPackageById(Integer.parseInt(chosenPackageId));
            } catch (ServicePackageException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while loading the chosen service package");
            }
        }

        // add objects to context
        context.setVariable("servicePackages", servicePackages);
        context.setVariable("user", user);
        context.setVariable("chosenPackage",sp);
        context.setVariable("today",df.format(today));
        context.setVariable("tomorrow",df.format(tomorrow));
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