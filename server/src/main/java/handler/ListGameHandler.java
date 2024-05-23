package handler;

import handler.utils.FromJson;
import handler.utils.ToJson;
import service.GameService;
import spark.Request;
import spark.Response;

public class ListGameHandler {
    private final GameService service;

    public ListGameHandler(GameService gameService, FromJson fromJson, ToJson toJson) {
        this.service = gameService;
    }


    public Object handleRequest(Request req, Response res) {
        return "";
    }
}
