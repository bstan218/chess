package handler;

import service.GameService;
import spark.Request;
import spark.Response;

public class JoinGameHandler {
    private final GameService service;

    public JoinGameHandler(GameService gameService) {
        this.service = gameService;
    }

    public Object handleRequest(Request req, Response res) {
        return "";
    }
}
