package handler;

import handler.utils.FromJson;
import handler.utils.ToJson;
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
