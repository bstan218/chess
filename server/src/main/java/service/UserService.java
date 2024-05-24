package service;

import dataaccess.*;
import handler.response.UserResponse;
import model.*;
import spark.Response;
import java.util.Objects;

public class UserService {
    private final UserDAO userDAO;
    private final AuthDAO authDAO;

    public UserService(UserDAO userDAO, AuthDAO authDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }

    public UserResponse register(UserData user, Response res) {
        try {
            userDAO.createUser(user);
        } catch (DataAccessException e) {
            res.status(401);
            return new UserResponse(e.getMessage(), null, null);
        }
        AuthData authData = authDAO.createAuth(user.username());
        return new UserResponse(null, authData.username(), authData.authToken());
    }
    public UserResponse login(UserData user, Response res) {
        UserData dbUser;
        try {
            dbUser = userDAO.getUser(user.username());
        } catch (DataAccessException e) {
            res.status(401);
            return new UserResponse(e.getMessage(), null, null);
        }
        if (!user.password().equals(dbUser.password())) {
            res.status(402);
            return new UserResponse("Error: incorrect password", null, null);
        }

        AuthData authData;
        try {
            authDAO.createAuth(user.username());
        } catch (DataAccessException e) {

        }
        return new UserResponse("", authData.username(), authData.authToken());
    }
    public void logout(AuthData auth) {}
}
