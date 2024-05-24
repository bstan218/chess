package service;

import dataaccess.*;
import handler.response.EmptyResponse;
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
            if (user.password() == null || user.email() == null) {
                res.status(400);
            } else {
                res.status(403);
            }
                return new UserResponse(e.getMessage(), null, null);
        }

        AuthData authData;
        try {
            authData = authDAO.createAuth(user.username());
        } catch (DataAccessException e) {
            res.status(402);
            return new UserResponse(e.getMessage(), null, null);
        }
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

        if (user.password() == null) {
            res.status(401);
            return new UserResponse("Error: Please enter your password", null, null);
        }

        if (!user.password().equals(dbUser.password())) {
            res.status(401);
            return new UserResponse("Error: incorrect password", null, null);
        }

        AuthData authData;
        try {
            authData = authDAO.createAuth(user.username());
        } catch (DataAccessException e) {
            res.status(403);
            return new UserResponse(e.getMessage(), null, null);
        }
        return new UserResponse("", authData.username(), authData.authToken());
    }

    public EmptyResponse logout(AuthData auth, Response res) {
        try {
            authDAO.getAuth(auth.authToken());
        } catch (DataAccessException e) {
            res.status(401);
            return new EmptyResponse(e.getMessage());
        }
        authDAO.deleteAuth(auth.authToken());
        return new EmptyResponse(null);
    }
}
