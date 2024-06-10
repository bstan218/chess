package client;

import client.websocket.ServerMessageObserver;

public class ChessClient {
    private ServerFacade serverFacade;

    public ChessClient(String serverUrl) {
        serverFacade = new ServerFacade(serverUrl);
    }
}
