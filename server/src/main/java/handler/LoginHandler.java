package handler;

import json.FromJson;
import json.ToJson;
import chess.response.UserResponse;
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
        UserData loginRequest = fromJson.fromJsonToUser(req.body());
        UserResponse userResponse = service.login(loginRequest, res);
        return toJson.fromResponse(userResponse);
    }
}