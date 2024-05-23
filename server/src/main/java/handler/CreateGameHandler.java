package handler;

import handler.utils.FromJson;
import handler.utils.ToJson;
import service.GameService;
import spark.Request;
import spark.Response;

public class CreateGameHandler {
    private final GameService service;

    public CreateGameHandler(GameService gameService, FromJson fromJson, ToJson toJson) {
        this.service = gameService;
    }

    public Object handleRequest(Request req, Response res) {
        return "";
    }
}
