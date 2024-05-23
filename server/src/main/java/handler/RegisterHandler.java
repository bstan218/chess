package handler;

import com.google.gson.Gson;
import handler.utils.FromJson;
import handler.utils.ToJson;
import handler.utils.ToJsonG;
import model.AuthData;
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
        UserData registerRequest = fromJson.(req.body(), UserData.class);
        AuthData result = service.register(registerRequest);
        return ToJsonG.toJson(result);
    }
}
