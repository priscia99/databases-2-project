package it.polimi.db2_project.TELCOWEB.controllers;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import it.polimi.db2_project.TELCOEJB.entities.OptionalProductEntity;
import it.polimi.db2_project.TELCOEJB.entities.OrderEntity;
import it.polimi.db2_project.TELCOEJB.entities.ServicePackageEntity;
import it.polimi.db2_project.TELCOEJB.entities.UserEntity;
import it.polimi.db2_project.TELCOEJB.enums.OrderState;
import it.polimi.db2_project.TELCOEJB.exceptions.OptionalProductException;
import it.polimi.db2_project.TELCOEJB.exceptions.ServicePackageException;
import it.polimi.db2_project.TELCOEJB.services.OptionalProductService;
import it.polimi.db2_project.TELCOEJB.services.ServicePackageService;
import it.polimi.db2_project.TELCOEJB.services.UserService;
import it.polimi.db2_project.TELCOEJB.utils.ConnectionHandler;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        // get order information
        OrderEntity storedOrder = (OrderEntity) session.getAttribute("order");

        // get chosenPackageId, chosenValidityPeriod and chosenOptionalProducts from the request

        String chosenPackageId = null;
        String chosenValidityPeriod = null;
        String[] chosenOptionalProductsId = null;
        final List<String> optionalProducts = new ArrayList<>();

        if(storedOrder == null) {
            chosenPackageId = request.getParameter("chosenPackageId");
            chosenValidityPeriod = request.getParameter("chosenValidityPeriod");
            chosenOptionalProductsId = request.getParameterValues("chosenOptionalProducts");
        }else{
            chosenPackageId = String.valueOf(storedOrder.getServicePackage().getPackageId());
            chosenValidityPeriod = String.valueOf(storedOrder.getServicePackage().getValidityPeriod());
            storedOrder.getOptionalProducts().forEach(opt -> optionalProducts.add(String.valueOf(opt.getProductId())));
        }


        ServicePackageEntity chosenServicePackage = null;
        List<OptionalProductEntity> chosenOptionalProducts = null;

        // check if all request parameters are actually present
        if((chosenPackageId == null || chosenValidityPeriod == null)){
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
        }else if(optionalProducts.size() != 0){
            try {
                chosenOptionalProducts = optionalProductService.getListOptionalProducts(optionalProducts);
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

        // Generating the order that has to be attached to the session

        // Retrieving the local current date time
        // TODO: Change it with starting time given in input by the user
        Clock cl = Clock.systemUTC();
        LocalDateTime start = LocalDateTime.now();

        // add the validity period time to the start time in order to compute the end time of the service package
        LocalDateTime end = start.plusMonths(Integer.parseInt(chosenValidityPeriod));
        Timestamp startTime = Timestamp.valueOf(start);
        Timestamp endTime = Timestamp.valueOf(end);

        // creating the order entity
        OrderEntity order = new OrderEntity(totalFee, startTime, endTime, OrderState.CREATED, user, chosenOptionalProducts, chosenServicePackage);

        // Insert the tentative order into the session
        request.getSession().setAttribute("order", order);

        // Insert the objects into the context of the response
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