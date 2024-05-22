package service;

import dataaccess.*;
import model.*;

import java.util.Objects;

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
        UserData dbUser = userDAO.getUser(user.username());
        if (!Objects.equals(dbUser.password(), user.password())) {
            //incorrect username or password
        }
        return authDAO.createAuth(user.username());
    }
    public void logout(AuthData auth) {}
}
