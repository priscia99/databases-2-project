package it.polimi.db2_project.TELCOEJB.exceptions;

public class InvalidCredentialsException extends Exception{
    private static final long serialVersionUID = 1L;

    public InvalidCredentialsException(String message){
        super(message);
    }
}