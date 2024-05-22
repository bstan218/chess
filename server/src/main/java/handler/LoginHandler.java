package handler;

import com.google.gson.Gson;
import model.AuthData;
import model.UserData;
import service.UserService;
import spark.Request;
import spark.Response;

public class LoginHandler {
    private final UserService service;

    public LoginHandler(UserService userService) {
        this.service = userService;
    }

    public Object handleRequest(Request req, Response res) {
        UserData loginRequest = new Gson().fromJson(req.body(), UserData.class);
        AuthData result = service.login(loginRequest);
        return new Gson().toJson(result);
    }
}