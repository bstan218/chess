package dataaccess;

import java.util.HashMap;
import model.UserData;

import javax.xml.crypto.Data;

public class MemoryUserDAO implements UserDAO{
    private HashMap<String, UserData> users = new HashMap<>();


    @Override
    public void deleteAllUsers() {
        users.clear();
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        UserData user =  users.get(username);
        if (user == null) throw new DataAccessException("Error: No user with that username");
        return user;
    }

    @Override
    public void createUser(UserData userData) throws DataAccessException {
        if (users.containsKey(userData.username()))  throw new DataAccessException("Error: User already in database");
        users.put(userData.username(), userData);
    }
}
