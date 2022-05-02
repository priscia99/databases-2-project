package it.polimi.db2_project.TELCOEJB.exceptions;

public class ServiceException extends Exception{
    private static final long serialVersionUID = 4L;

    public ServiceException(String message){
        super(message);
    }
}
