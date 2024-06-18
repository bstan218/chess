package client;

import chess.ChessGame;
import chess.response.ListGameResponse;
import client.state.PlayState;
import client.websocket.ServerMessageObserver;
import json.ToJson;
import json.ToJsonG;
import model.AuthData;
import model.GameData;

import java.util.List;
import java.util.Map;

public class ServerFacade {
    private final HttpCommunicator httpCommunicator;
    private final WebSocketCommunicator webSocketCommunicator;
    private final ToJson toJson = new ToJsonG();

    public ServerFacade(String url, ServerMessageObserver serverMessageObserver) throws Exception {
        httpCommunicator = new HttpCommunicator(url);
        webSocketCommunicator = new WebSocketCommunicator(url, serverMessageObserver);


    }


    public String login(String[] params) throws ResponseException {
        var reqBody = Map.of("username", params[0],
                            "password", params[1]);

            AuthData authData = httpCommunicator.makeRequest("POST", "/session", reqBody,
                                                            null, AuthData.class);
            return authData.authToken();
    }

    public String register(String[] params) throws ResponseException {
        var reqBody = Map.of("username", params[0],
                            "password", params[1],
                            "email", params[2]);

        AuthData authData = httpCommunicator.makeRequest("POST", "/user", reqBody,
                                            null, AuthData.class);
        return authData.authToken();
    }

    public void createGame(String[] params, String authToken) throws ResponseException {
        var reqBody = Map.of("gameName", String.join(" ", params));

        httpCommunicator.makeRequest("POST", "/game", reqBody, authToken, null);
    }

    public int playGame(String[] params, List<GameData> gameList, String authToken, ChessClient chessClient) throws ResponseException {
        int gameID = gameList.get(Integer.parseInt(params[0])-1).gameID();
        var reqBody = Map.of("playerColor", params[1].toUpperCase(), "gameID", gameID);
        httpCommunicator.makeRequest("PUT", "/game", reqBody, authToken, null);
        webSocketCommunicator.connectToGame(authToken, gameID);

        if (params[1].toUpperCase().equals("WHITE")) {
            chessClient.setPlayState(PlayState.WHITE);
        } else if (params[1].toUpperCase().equals("BLACK"))
            chessClient.setPlayState(PlayState.BLACK);
        return gameID;
    }

    public int observeGame(String[] params, List<GameData> gameList, String authToken) {
        int gameID = gameList.get(Integer.parseInt(params[0])-1).gameID();
        webSocketCommunicator.connectToGame(authToken, gameID);
        return gameID;
    }

    public List<GameData> listGames(String authToken) throws ResponseException {
        ListGameResponse listGameResponse = httpCommunicator.makeRequest("GET", "/game", null, authToken, ListGameResponse.class);
        return  listGameResponse.games();
    }

    public void logout(String authToken) throws ResponseException {
        httpCommunicator.makeRequest("DELETE", "/session", null,
                                    authToken, null);
    }

    public void leaveGame(String authToken, int currentGameID) {
        webSocketCommunicator.leaveGame(authToken, currentGameID);
    }

    public void resign(String authToken, int currentGameID) {
        webSocketCommunicator.resign(authToken, currentGameID);
    }

    public void makeMove(String[] params, String authToken) {
    }
}
