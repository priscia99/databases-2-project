package it.polimi.db2_project.TELCOEJB.exceptions;

public class NonUniqueResultException extends Exception{
    private static final long serialVersionUID = 3L;

    public NonUniqueResultException(String message){
        super(message);
    }
}
