package it.polimi.db2_project.TELCOWEB.controllers;

import it.polimi.db2_project.TELCOEJB.entities.OptionalProductEntity;
import it.polimi.db2_project.TELCOEJB.entities.ServicePackageEntity;
import it.polimi.db2_project.TELCOEJB.entities.UserEntity;
import it.polimi.db2_project.TELCOEJB.exceptions.OptionalProductException;
import it.polimi.db2_project.TELCOEJB.exceptions.ServicePackageException;
import it.polimi.db2_project.TELCOEJB.services.OptionalProductService;
import it.polimi.db2_project.TELCOEJB.services.ServicePackageService;
import it.polimi.db2_project.TELCOEJB.services.UserService;
import it.polimi.db2_project.TELCOEJB.utils.ConnectionHandler;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
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


@WebServlet(name = "ConfirmationPageServlet", value = "/confirmation")
public class ConfirmationPageServlet extends HttpServlet {
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
        String path = "/confirmation.html";
        ServletContext servletContext = getServletContext();
        final WebContext context = new WebContext(request, response, servletContext, request.getLocale());

        // get user information
        UserEntity user = (UserEntity) session.getAttribute("user");

        // get chosenPackageId, chosenValidityPeriod and chosenOptionalProducts from the request
        String chosenPackageId = request.getParameter("chosenPackageId");
        String chosenValidityPeriod = request.getParameter("chosenValidityPeriod");
        String chosenOptionalProductsId[] = request.getParameterValues("chosenOptionalProducts");

        ServicePackageEntity chosenServicePackage = null;
        List<OptionalProductEntity> chosenOptionalProducts = null;
        // check if all request parameters are actually present
        if(chosenPackageId == null || chosenValidityPeriod == null){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Some fields are not filled");
            return;
        }

        // check if the ID and the validity period of the chosen service package are valid
        try {
            servicePackageService.checkValidity(chosenPackageId, chosenValidityPeriod);
            chosenServicePackage = servicePackageService.getPackagesByIdAndValidityPeriod(chosenPackageId, chosenValidityPeriod);
        } catch (ServicePackageException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Some fields are not valid");
            return;
        }

        // get all chosen optional products
        if(chosenOptionalProductsId != null) {
            try {
                chosenOptionalProducts = optionalProductService.getListOptionalProducts(Arrays.asList(chosenOptionalProductsId));
            } catch (OptionalProductException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
                e.printStackTrace();
            }
        }

        // computing the total fee to pay

        float totalFee = chosenServicePackage.getMonthlyFee();
        if(chosenOptionalProducts != null) {
            context.setVariable("optionalProducts", new ArrayList<>(chosenOptionalProducts));
            for (OptionalProductEntity p : chosenOptionalProducts) {
                totalFee += p.getMonthlyFee();
            }
        }
        totalFee *= chosenServicePackage.getValidityPeriod();

        context.setVariable("servicePackage", chosenServicePackage);
        context.setVariable("totalFee", totalFee);
        context.setVariable("user", user);

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