package handler;

import handler.json.FromJson;
import handler.json.ToJson;
import service.UserService;
import spark.Request;
import spark.Response;

public class LogoutHandler {
    private final UserService service;

    public LogoutHandler(UserService userService, FromJson fromJson, ToJson toJson) {
        this.service = userService;
    }


    public Object handleRequest(Request req, Response res) {
        return "";
    }
}
