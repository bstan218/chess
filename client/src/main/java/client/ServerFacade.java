package client;

import chess.response.ListGameResponse;
import model.AuthData;
import model.GameData;

import java.util.List;
import java.util.Map;

public class ServerFacade {
    private final HttpCommunicator httpCommunicator;

    public ServerFacade(String url) {
        httpCommunicator = new HttpCommunicator(url);
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

    public void playGame(String[] params, List<GameData> gameList, String authToken) throws ResponseException {
        int gameID = gameList.get(Integer.parseInt(params[0])-1).gameID();
        var reqBody = Map.of("playerColor", params[1].toUpperCase(), "gameID", gameID);
        httpCommunicator.makeRequest("PUT", "/game", reqBody, authToken, null);
    }

    //public String observeGame(String[] params) {return "not implemented";}

    public List<GameData> listGames(String authToken) throws ResponseException {
        ListGameResponse listGameResponse = httpCommunicator.makeRequest("GET", "/game", null, authToken, ListGameResponse.class);
        return  listGameResponse.games();
    }

    public void logout(String authToken) throws ResponseException {
        httpCommunicator.makeRequest("DELETE", "/session", null,
                                    authToken, null);
    }

}
