package service;

import dataaccess.*;
import model.*;

public class UserService {
    private UserDAO userDAO;
    private AuthDAO authDAO;

    public UserService(UserDAO userDAO, AuthDAO authDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }

    public AuthData register(UserData user) {
        if (userDAO.getUser(user.username()) != null) {
            //error case
        }
        userDAO.createUser(user);
        return authDAO.createAuth(user.username());
    }
    public AuthData login(UserData user) {
        return null;
    }
    public void logout(AuthData auth) {}
}
