package it.polimi.db2_project.TELCOWEB.controllers;

import it.polimi.db2_project.TELCOEJB.entities.OrderEntity;
import it.polimi.db2_project.TELCOEJB.entities.UserEntity;
import it.polimi.db2_project.TELCOEJB.enums.OrderState;
import it.polimi.db2_project.TELCOEJB.exceptions.OrderException;
import it.polimi.db2_project.TELCOEJB.services.OrderService;
import it.polimi.db2_project.TELCOEJB.services.UserService;
import it.polimi.db2_project.TELCOEJB.utils.ConnectionHandler;

import java.io.*;
import java.sql.Connection;
import java.sql.Timestamp;
import java.time.*;
import java.util.*;
import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.UnavailableException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

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
        String path = "/payorder.html";
        ServletContext servletContext = getServletContext();
        final WebContext context = new WebContext(request, response, servletContext, request.getLocale());

        // get order information
        OrderEntity order = (OrderEntity) session.getAttribute("order");
        UserEntity user = (UserEntity) session.getAttribute("user");

        // understanding if the order is already present
        if(order.getOrderId() == null) {
            //setting the creation date
            Date nowDate = new Date();
            LocalDateTime now = Instant.ofEpochMilli(nowDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
            Timestamp nowTime = Timestamp.valueOf(now);
            order.setCreationDateTime(nowTime);
            //setting the order state
            if (Utils.pay()) {
                order.setOrderState(OrderState.PAID);
            } else {
                //setting the user as insolvent and the order state as rejected
                order.setOrderState(OrderState.REJECTED);
                user = userService.setUserInsolvent(user.getUsername());
            }
            //add the order to db
            try {
                orderService.persistOrder(order);
            } catch (OrderException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
                e.printStackTrace();
            }
        }else{
            if (Utils.pay()) {
                order.setOrderState(OrderState.PAID);
                try {
                    orderService.updateOrderOnState(order);
                } catch (OrderException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        //updating the session
        context.setVariable("order", order);
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