package handler;

import json.FromJson;
import json.ToJson;
import handler.request.JoinGameRequest;
import chess.response.EmptyResponse;
import model.AuthData;
import service.GameService;
import spark.Request;
import spark.Response;

public class JoinGameHandler {
    private final GameService service;
    private final FromJson fromJson;
    private final ToJson toJson;

    public JoinGameHandler(GameService gameService, FromJson fromJson, ToJson toJson) {
        this.service = gameService;
        this.fromJson = fromJson;
        this.toJson = toJson;
    }

    public Object handleRequest(Request req, Response res) {
        AuthData authData = fromJson.fromHeaderToAuth(req.headers("Authorization"));
        JoinGameRequest joinGameRequest = fromJson.fromJson(req.body(), JoinGameRequest.class);
        EmptyResponse emptyResponse = service.joinGame(authData, joinGameRequest, res);
        return toJson.fromResponse(emptyResponse);
    }
}
