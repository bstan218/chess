package handler;

import com.google.gson.Gson;
import handler.utils.FromJson;
import handler.utils.ToJson;
import model.AuthData;
import model.UserData;
import service.UserService;
import spark.Request;
import spark.Response;

public class LoginHandler {
    private final UserService service;
    private final ToJson toJson;
    private final FromJson fromJson;

    public LoginHandler(UserService userService, FromJson fromJson, ToJson toJson) {
        this.service = userService;
        this.toJson = toJson;
        this.fromJson = fromJson;
    }

    public Object handleRequest(Request req, Response res) {
        UserData loginRequest = new Gson().fromJson(req.body(), UserData.class);
        AuthData result = service.login(loginRequest);
        return new Gson().toJson(result);
    }
}