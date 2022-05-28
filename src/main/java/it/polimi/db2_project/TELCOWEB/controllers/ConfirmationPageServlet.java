package it.polimi.db2_project.TELCOWEB.controllers;

import it.polimi.db2_project.TELCOEJB.entities.*;
import it.polimi.db2_project.TELCOEJB.enums.OrderState;
import it.polimi.db2_project.TELCOEJB.exceptions.OptionalProductException;
import it.polimi.db2_project.TELCOEJB.exceptions.PeriodException;
import it.polimi.db2_project.TELCOEJB.exceptions.ServicePackageException;
import it.polimi.db2_project.TELCOEJB.services.*;
import it.polimi.db2_project.TELCOEJB.utils.ConnectionHandler;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import javax.ejb.EJB;
import javax.persistence.criteria.Order;
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
    @EJB(name = "it.polimi.db2_project.TELCOEJB.services/OrderService")
    private OrderService orderService;
    @EJB(name = "it.polimi.db2_project.TELCOEJB.services/PeriodService")
    private PeriodService periodService;

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
        String path = "/confirmation.html";
        ServletContext servletContext = getServletContext();
        final WebContext context = new WebContext(request, response, servletContext, request.getLocale());

        // get user information
        UserEntity user = (UserEntity) session.getAttribute("user");

        // get purchase information in case if the order that has to be created is new
        String chosenPackageId = (String) request.getParameter("chosenPackageId");
        String chosenPeriodId = (String) request.getParameter("chosenPeriodId");
        String[] chosenOptionalProducts = request.getParameterValues("chosenOptionalProducts");
        String startDate = (String) request.getParameter("startDate");
        String rejectedOrderId = (String) request.getParameter("rejectedOrderId");

        // get order entity in case if the order has been already stored in the session
        OrderEntity storedOrder;
        if(rejectedOrderId == null) {
            storedOrder = (OrderEntity) session.getAttribute("order");
        }else {
            storedOrder = orderService.findOrderById(Integer.parseInt(rejectedOrderId));
            session.setAttribute("order", storedOrder);
        }

        if(storedOrder == null){
            // Handle a purchase in case the order is new
            if(chosenPackageId == null || chosenPeriodId == null || startDate == null){
                if(chosenPackageId == null){
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing package ID in order");
                }
                else if(chosenPeriodId == null){
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing period id");
                }
                else if(startDate == null){
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing start date");
                }
                // Some parameters are missing -> return a bad request error

                return;
            }

            // Retrieve the chosen period entity
            PeriodEntity chosenPeriodEntity = null;
            try {
                chosenPeriodEntity = periodService.getPeriodById(Integer.parseInt(chosenPeriodId));
            } catch (PeriodException e) {
                e.printStackTrace();
            }

            float totalFee = chosenPeriodEntity.getMonthlyFee();

            // Retrieve the list of optional products in case the user has selected at least one of them
            ArrayList<OptionalProductEntity> chosenOptionalProductsEntities = null;
            if(chosenOptionalProducts != null) {
                try {
                    chosenOptionalProductsEntities = (ArrayList<OptionalProductEntity>) optionalProductService.getListOptionalProducts(Arrays.asList(chosenOptionalProducts));
                    for(OptionalProductEntity opt : chosenOptionalProductsEntities){
                        totalFee += opt.getMonthlyFee() * chosenPeriodEntity.getValidityPeriod();
                    }
                } catch (OptionalProductException e) {
                    e.printStackTrace();
                }
            }

            // define the format used to convert the given starting date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = null;

            // parsing the given starting date
            try {
                parsedDate = dateFormat.parse(startDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // creating the starting time stamp
            LocalDateTime start = Instant.ofEpochMilli(parsedDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
            Timestamp startTime = Timestamp.valueOf(start);

            // add the validity period time to the start time in order to compute the end time of the service package
            LocalDateTime end = start.plusMonths(chosenPeriodEntity.getValidityPeriod());
            Timestamp endTime = Timestamp.valueOf(end);

            // Creates the new order entity
            OrderEntity newOrder = new OrderEntity(
                    totalFee,
                    startTime,
                    endTime,
                    OrderState.CREATED,
                    user,
                    chosenOptionalProductsEntities,
                    chosenPeriodEntity
            );

            // Insert the tentative order into the session
            request.getSession().setAttribute("order", newOrder);

            // Insert the objects into the context of the response
            context.setVariable("orderInfo", newOrder);
        }
        else{
            // Handle a purchase in case the order is retrieved from the session
            if(user!=null){
                storedOrder.setUser(user);
            }
            context.setVariable("orderInfo", storedOrder);
        }

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