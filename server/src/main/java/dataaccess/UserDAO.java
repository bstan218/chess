package dataaccess;

import model.UserData;

import javax.xml.crypto.Data;

public interface UserDAO {
    void deleteAllUsers() throws DataAccessException;

    UserData getUser(String username) throws DataAccessException;

    void createUser(UserData userData) throws DataAccessException;
}
