package handler;

import service.UserService;
import spark.Request;
import spark.Response;

public class LogoutHandler {
    private final UserService service;

    public LogoutHandler(UserService userService) {
        this.service = userService;
    }


    public Object handleRequest(Request req, Response res) {
        return "";
    }
}
