package handler;

import com.google.gson.Gson;
import model.AuthData;
import service.UserService;
import spark.Request;
import spark.Response;
import model.UserData;

public class RegisterHandler {
    private final UserService service;

    public RegisterHandler(UserService userService) {
        this.service = userService;
    }

    public Object handleRequest(Request req, Response res) {
        UserData registerRequest = new Gson().fromJson(req.body(), UserData.class);
        AuthData result = service.register(registerRequest);
        return new Gson().toJson(result);
    }
}
