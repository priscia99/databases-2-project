package it.polimi.db2_project.TELCOEJB.services;

import it.polimi.db2_project.TELCOEJB.entities.AlertEntity;
import it.polimi.db2_project.TELCOEJB.entities.OrderEntity;
import it.polimi.db2_project.TELCOEJB.entities.UserEntity;
import it.polimi.db2_project.TELCOEJB.enums.OrderState;
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
public class UserService {
    @PersistenceContext(unitName = "TELCOEJB")
    private EntityManager em;

    public UserService(){
    }

    /**
     * Check the user credentials
     * @param username user's username
     * @param password user's password
     * @return the user entity associated to the given user if the login succeeded
     * @throws NonUniqueResultException if the user entity retrieved is not unique
     * @throws CredentialsException if an error occoured while trying to retrieve the user
     * @throws InvalidCredentialsException if the credentials are not correct
     */
    public UserEntity checkCredentials(String username, String password) throws NonUniqueResultException, CredentialsException, InvalidCredentialsException {
        List<UserEntity> usersList;

        try{
            // retrieving the list of users that match with a given username and password
            usersList = em.createNamedQuery("UserEntity.checkCredentials", UserEntity.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getResultList();
        }
        catch (PersistenceException e){
            throw new CredentialsException("An error occoured while trying to check your credentials.");
        }

        // Check the retrieved list of users
        if(usersList.isEmpty()){
            // No one matches with the given username and password
            throw new InvalidCredentialsException("Invalid Username or Password");
        }
        else if(usersList.size() == 1){
            // An user matches
            // Return the user entity object
            return usersList.get(0);
        }
        else{
            // More than one user match with the given username and password
            throw new NonUniqueResultException("Error: more users match with the same credentials");
        }
    }

    /**
     * Retrieves the user that matches with the username given in input
     * @param username user's username
     * @return the user entity associated with the given username
     */
    public UserEntity findUserByUsername(String username) {
        return em.find(UserEntity.class,username);
    }

    /**
     * Retrieves the user that matches with the email given in input
     * @param email user's email
     * @return the user entity associated with the given email
     */
    public UserEntity findUserByEmail(String email) {
        return em.createNamedQuery("UserEntity.findByEmail", UserEntity.class)
                .setParameter("email", email)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    /**
     * Creates a new user and persists it to the database
     * @param username user's username
     * @param password user's password
     * @param email user's email
     * @return the user entity associated to the new user created
     * @throws CredentialsException if some parameters are already present
     */
    public UserEntity addNewUser(String username, String password, String email) throws CredentialsException {
        // return an error if the username is already in use
        if (findUserByUsername(username) != null) {
            throw new CredentialsException("Username already in use!");
        }

        // return an error if the email is already in use
        if (findUserByEmail(email) != null) {
            throw new CredentialsException("Email already in use!");
        }

        // if everything is fine, then create the user entity associated to the new user
        UserEntity newUser = new UserEntity(username, password, email);
        em.persist(newUser);    // persist the new user to the database
        em.flush();
        return newUser;
    }
}
