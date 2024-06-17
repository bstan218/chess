package handler;

import json.FromJson;
import json.ToJson;
import chess.response.EmptyResponse;
import model.AuthData;
import service.UserService;
import spark.Request;
import spark.Response;

public class LogoutHandler {
    private final UserService service;
    private final FromJson fromJson;
    private final ToJson toJson;

    public LogoutHandler(UserService userService, FromJson fromJson, ToJson toJson) {
        this.service = userService;
        this.fromJson = fromJson;
        this.toJson = toJson;
    }

    public Object handleRequest(Request req, Response res) {
        AuthData authData = fromJson.fromHeaderToAuth(req.headers("Authorization"));
        EmptyResponse emptyResponse = service.logout(authData, res);
        return toJson.fromResponse(emptyResponse);

    }
}
