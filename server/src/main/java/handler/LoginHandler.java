package handler;

import handler.json.FromJson;
import handler.json.ToJson;
import handler.response.UserResponse;
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
        UserData LoginRequest = fromJson.fromJsonToUser(req.body());
        UserResponse result = service.login(LoginRequest, res);
        return toJson.fromResponse(result);
    }
}