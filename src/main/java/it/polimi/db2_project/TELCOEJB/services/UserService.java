package it.polimi.db2_project.TELCOEJB.services;

import it.polimi.db2_project.TELCOEJB.entities.UserEntity;
import it.polimi.db2_project.TELCOEJB.exceptions.CredentialsException;
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

    public UserService(Connection connection){
    }

    public UserEntity checkCredentials(String username, String password) throws NonUniqueResultException, CredentialsException{
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
            return null;
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

    /*
    public UserEntity performLogin(String username, String password) throws SQLException {
        String query = "SELECT id, username FROM users WHERE username = ? AND password = ?";

        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, username);
            statement.setString(2, password);

            try(ResultSet resultSet = statement.executeQuery()){

                if(!resultSet.isBeforeFirst()){
                    return null;
                }else{
                    resultSet.next();
                    int logged_id = resultSet.getInt("id");
                    String logged_username = resultSet.getString("username");

                    UserEntity loggedUser = new UserEntity(logged_id, logged_username);
                    return loggedUser;
                }

            }
        }
    }
    aaa
    */
}
