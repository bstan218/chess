package client;

import model.AuthData;

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

    public String createGame(String[] params) {
        return "not implemented";
    }

    public String playGame(String[] params) {
        return "not implemented";
    }

    public String observeGame(String[] params) {
        return "not implemented";
    }

    public String listGames() {
        return "not implemented";
    }

    public String logout() {
        return "not implemented";
    }

}
