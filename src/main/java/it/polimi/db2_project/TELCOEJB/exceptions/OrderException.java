package it.polimi.db2_project.TELCOEJB.exceptions;

public class OrderException extends Exception{
    private static final long serialVersionUID = 4L;

    public OrderException(String message){
        super(message);
    }
}
