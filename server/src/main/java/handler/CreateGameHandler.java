package handler;

import service.GameService;
import spark.Request;
import spark.Response;

public class CreateGameHandler {
    private final GameService service;

    public CreateGameHandler(GameService gameService) {
        this.service = gameService;
    }

    public Object handleRequest(Request req, Response res) {
        return "";
    }
}
