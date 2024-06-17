package handler;

import json.FromJson;
import json.ToJson;
import chess.response.CreateGameResponse;
import model.AuthData;
import model.GameData;
import service.GameService;
import spark.Request;
import spark.Response;

public class CreateGameHandler {
    private final GameService service;
    private final FromJson fromJson;
    private final ToJson toJson;

    public CreateGameHandler(GameService gameService, FromJson fromJson, ToJson toJson) {
        this.service = gameService;
        this.fromJson = fromJson;
        this.toJson = toJson;
    }

    public Object handleRequest(Request req, Response res) {
        AuthData authData = fromJson.fromHeaderToAuth(req.headers("Authorization"));
        GameData gameData = fromJson.fromJsonToGame(req.body());
        CreateGameResponse createGameResponse = service.createGame(authData, gameData, res);
        return toJson.fromResponse(createGameResponse);
    }
}
