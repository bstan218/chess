package dataaccess;

import java.util.HashMap;
import model.UserData;

public class MemoryUserDAO implements UserDAO{
    private HashMap<String, UserData> users = new HashMap<>();


    @Override
    public void deleteAllUsers() {
        users.clear();
    }

    @Override
    public UserData getUser(String username) {
        return users.get(username);
    }

    @Override
    public void createUser(UserData userData) {
        users.put(userData.username(), userData);
    }
}
