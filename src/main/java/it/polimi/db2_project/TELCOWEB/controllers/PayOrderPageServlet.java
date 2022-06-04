package it.polimi.db2_project.TELCOWEB.controllers;

import it.polimi.db2_project.TELCOEJB.entities.AlertEntity;
import it.polimi.db2_project.TELCOEJB.entities.OrderEntity;
import it.polimi.db2_project.TELCOEJB.entities.UserEntity;
import it.polimi.db2_project.TELCOEJB.enums.OrderState;
import it.polimi.db2_project.TELCOEJB.exceptions.OrderException;
import it.polimi.db2_project.TELCOEJB.services.AlertService;
import it.polimi.db2_project.TELCOEJB.services.OrderService;
import it.polimi.db2_project.TELCOEJB.services.UserService;
import it.polimi.db2_project.TELCOEJB.utils.ConnectionHandler;

import java.io.*;
import java.sql.Connection;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.UnavailableException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import it.polimi.db2_project.TELCOEJB.utils.Utils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;


@WebServlet(name = "PayOrderPageServlet", value = "/payorder")
public class PayOrderPageServlet extends HttpServlet {
    private Connection connection = null;
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db2_project.TELCOEJB.services/OrderService")
    private OrderService orderService;
    @EJB(name = "it.polimi.db2_project.TELCOEJB.services/UserService")
    private UserService userService;
    @EJB(name = "it.polimi.db2_project.TELCOEJB.services/AlertService")
    private AlertService alertService;

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
        String path = "/confirmation.html";
        ServletContext servletContext = getServletContext();
        final WebContext context = new WebContext(request, response, servletContext, request.getLocale());

        // get order information
        OrderEntity order = (OrderEntity) session.getAttribute("order");
        UserEntity user = (UserEntity) session.getAttribute("user");
        // understanding if the order is already present
        if(order.getOrderId() == 0) {
            //setting the creation date
            order.setCreationDateTime(Utils.getNowTime());
            //setting the order state
            if (Utils.pay()) {
                order.setOrderState(OrderState.PAID);
            } else {
                //setting the user as insolvent and the order state as rejected
                order.setOrderState(OrderState.REJECTED);
            }
            //add the order to db
            try {
                orderService.persistOrder(order);
            } catch (OrderException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
                e.printStackTrace();
            }
        }else{
            // call the external service and try to pay the order
            if(Utils.pay()){
                // the payment has been successful
                order.setOrderState(OrderState.PAID);
                try {
                    // update the order state to the db
                    orderService.updateOrderOnState(order);
                } catch (OrderException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
                    e.printStackTrace();
                }
            }
            else{
                try {
                    // begin of the transaction
                    userTransaction.begin();
                    // the payment has been rejected (failed)
                    order.setOrderState(OrderState.REJECTED);
                    // update the order state to the db
                    orderService.updateOrderOnState(order);
                    order = orderService.findOrderById(order.getOrderId());
                    userService.addFailedAttempt(order.getUser());
                    if(order.getUser().getFailedAttempts() == 3){
                        // float amount, Timestamp lastRejectionDateTime, String email, UserEntity relatedUser
                        AlertEntity alert = new AlertEntity(order.getTotalFee(), Utils.getNowTime(), order.getUser().getEmail(), order.getUser());
                        alertService.persistAlert(alert);
                    }
                    // commit
                    userTransaction.commit();
                } catch (Exception e) {
                    try{
                        // in case of error, rollback the transaction
                        userTransaction.rollback();
                    } catch (SystemException systemException) {
                        e.printStackTrace();
                    }
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while trying to create the service package");
                }
            }
        }

        //updating the session
        context.setVariable("orderInfo", order);
        session.removeAttribute("order");
        session.setAttribute("user",user);
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