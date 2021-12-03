package it.polimi.db2_project.TELCOEJB.services;

import it.polimi.db2_project.TELCOEJB.entities.UserEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    private Connection connection;

    public UserService(Connection connection){
        this.connection = connection;
    }

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
}
