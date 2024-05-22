package service;

import dataaccess.*;
import model.*;

public class UserService {
    private UserDAO userDAO;
    private AuthDAO authDAO;
    private GameDAO gameDAO;

    public UserService(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public AuthData register(UserData user) {

    }
    public AuthData login(UserData user) {

    }
    public void logout(AuthData auth) {}
}
