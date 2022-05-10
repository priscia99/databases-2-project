package it.polimi.db2_project.TELCOEJB.services;

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
public class UserService {
    @PersistenceContext(unitName = "TELCOEJB")
    private EntityManager em;

    public UserService(){
    }

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

    public UserEntity findUserByUsername(String username) {
//        return em.createNamedQuery("UserEntity.findByUsername", UserEntity.class)
//                .setParameter("username", username)
//                .setMaxResults(1)
//                .getResultStream()
//                .findFirst()
//                .orElse(null);
        return em.find(UserEntity.class,username);
    }

    public UserEntity findUserByEmail(String email) {
        return em.createNamedQuery("UserEntity.findByEmail", UserEntity.class)
                .setParameter("email", email)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public UserEntity addNewUser(String username, String password, String email) throws CredentialsException {
        if (findUserByUsername(username) != null) {
            throw new CredentialsException("Username already in use!");
        }

        if (findUserByEmail(email) != null) {
            throw new CredentialsException("Email already in use!");
        }

        UserEntity newUser = new UserEntity(username, password, email);
        em.persist(newUser);
        em.flush();
        return newUser;
    }

    public UserEntity setUserInsolvent(String username){
        UserEntity user = em.find(UserEntity.class,username);
        user.setInsolvent(true);
        user.setFailedAttempts(user.getFailedAttempts() +1);
        em.flush();
        return user;
    }

}
