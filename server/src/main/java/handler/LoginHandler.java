package handler;

import service.UserService;
import spark.Request;
import spark.Response;

public class LoginHandler {
    private final UserService service;

    public LoginHandler(UserService userService) {
        this.service = userService;
    }

    public Object handleRequest(Request req, Response res) {
        return "";
    }
}