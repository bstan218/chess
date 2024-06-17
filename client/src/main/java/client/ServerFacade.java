package client;

import chess.ChessGame;
import chess.response.ListGameResponse;
import client.websocket.ServerMessageObserver;
import handler.json.FromJson;
import handler.json.ToJson;
import handler.json.ToJsonG;
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

    public int playGame(String[] params, List<GameData> gameList, String authToken) throws ResponseException {
        int gameID = gameList.get(Integer.parseInt(params[0])-1).gameID();
        var reqBody = Map.of("playerColor", params[1].toUpperCase(), "gameID", gameID);
        httpCommunicator.makeRequest("PUT", "/game", reqBody, authToken, null);
        webSocketCommunicator.connectToGame(authToken, gameID);
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

    public String resign(String authToken, int currentGameID) {
        return null;
    }

    public void makeMove(String[] params, String authToken) {
    }
}
