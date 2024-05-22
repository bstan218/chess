package service;

import dataaccess.*;
import model.*;

public class UserService {
    private static final AuthDAO authDAO = new MemoryAuthDAO();
    private static final UserDAO userDAO = new MemoryUserDAO();
    private static final GameDAO gameDAO = new MemoryGameDAO();

    public AuthData register(UserData user) {

    }
    public AuthData login(UserData user) {

    }
    public void logout(AuthData auth) {}
}
