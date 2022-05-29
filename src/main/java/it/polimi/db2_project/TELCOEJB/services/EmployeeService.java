package it.polimi.db2_project.TELCOEJB.services;

import it.polimi.db2_project.TELCOEJB.entities.EmployeeEntity;
import it.polimi.db2_project.TELCOEJB.entities.UserEntity;
import it.polimi.db2_project.TELCOEJB.exceptions.CredentialsException;
import it.polimi.db2_project.TELCOEJB.exceptions.InvalidCredentialsException;
import it.polimi.db2_project.TELCOEJB.exceptions.NonUniqueResultException;

import java.sql.Connection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Stateless
public class EmployeeService {
    @PersistenceContext(unitName = "TELCOEJB")
    private EntityManager em;

    public EmployeeService(){
    }

    /**
     * Check the credentials of an employee
     * @param id employee's ID
     * @param password employee's password
     * @return the employee entity associated with the credentials given in input
     * @throws NonUniqueResultException if more than one employee exist with the given credentials
     * @throws CredentialsException if there is an error while trying to execute the query
     * @throws InvalidCredentialsException if the credentials are not valid
     */
    public EmployeeEntity checkCredentials(String id, String password) throws NonUniqueResultException, CredentialsException, InvalidCredentialsException {
        List<EmployeeEntity> employeeList;

        try{
            // retrieving the list of users that match with a given username and password
            employeeList = em.createNamedQuery("EmployeeEntity.checkCredentials", EmployeeEntity.class)
                    .setParameter("id", id)
                    .setParameter("password", password)
                    .getResultList();
        }
        catch (PersistenceException e){
            throw new CredentialsException("An error occoured while trying to check your credentials.");
        }

        // Check the retrieved list of users
        if(employeeList.isEmpty()){
            // No one matches with the given username and password
            throw new InvalidCredentialsException("Invalid Id or Password");
        }
        else if(employeeList.size() == 1){
            // An user matches
            // Return the user entity object
            return employeeList.get(0);
        }
        else{
            // More than one user match with the given username and password
            throw new NonUniqueResultException("Error: more employees match with the same credentials");
        }
    }


}
