package handler;

import service.GameService;
import spark.Request;
import spark.Response;

public class ListGameHandler {
    private final GameService service;

    public ListGameHandler(GameService gameService) {
        this.service = gameService;
    }


    public Object handleRequest(Request req, Response res) {
        return "";
    }
}
