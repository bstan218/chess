package dataaccess;

import java.util.HashMap;
import model.UserData;

public class MemoryUserDAO implements UserDAO{
    private HashMap<String, UserData> users;

    public MemoryUserDAO () {
           this.users = new HashMap<>();
    }


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
        if (userData.password() == null || userData.email() == null) throw new DataAccessException("Error: email or password missing");
        users.put(userData.username(), userData);
    }

    public boolean usersIsEmpty() {
        return users.isEmpty();
    }
}
