package handler;

import handler.json.FromJson;
import handler.json.ToJson;
import handler.response.ListGameResponse;
import model.AuthData;
import service.GameService;
import spark.Request;
import spark.Response;

public class ListGameHandler {
    private final GameService service;
    private final FromJson fromJson;
    private final ToJson toJson;

    public ListGameHandler(GameService gameService, FromJson fromJson, ToJson toJson) {
        this.service = gameService;
        this.fromJson = fromJson;
        this.toJson = toJson;
    }


    public Object handleRequest(Request req, Response res) {
        AuthData authData = fromJson.fromHeaderToAuth(req.headers("Authorization"));
        ListGameResponse listGameResponse = service.listGames(authData, res);
        return toJson.fromResponse(listGameResponse);
    }
}
