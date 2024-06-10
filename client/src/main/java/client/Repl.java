package client;

import client.websocket.ServerMessageObserver;

public class Repl {
    private final ChessClient client;

    public Repl(String serverUrl) {
        client = new ChessClient(serverUrl);
    }

    public void run() {
        System.out.println("\uD83D\uDC36 Welcome to Britton's Chess!");
        //System.out.print(client.help());

    }


}
