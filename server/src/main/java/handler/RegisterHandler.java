package handler;

import handler.json.FromJson;
import handler.json.ToJson;
import handler.response.UserResponse;
import service.UserService;
import spark.Request;
import spark.Response;
import model.UserData;

public class RegisterHandler {
    private final UserService service;
    private final ToJson toJson;
    private final FromJson fromJson;

    public RegisterHandler(UserService userService, FromJson fromJson, ToJson toJson) {
        this.service = userService;
        this.toJson = toJson;
        this.fromJson = fromJson;
    }

    public Object handleRequest(Request req, Response res) {
        UserData registerRequest = fromJson.fromJsonToUser(req.body());
        UserResponse userResponse = service.register(registerRequest, res);
        return toJson.fromResponse(userResponse);
    }
}
