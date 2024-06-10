package client;

public class ServerFacade {
    private final String serverUrl;
    private final HttpCommunicator httpCommunicator;

    public ServerFacade(String url) {
        serverUrl = url;
        httpCommunicator = new HttpCommunicator(url);
    }


    public String login(String[] params) {
    }

    public String register(String[] params) {
    }

    public String createGame(String[] params) {
    }

    public String playGame(String[] params) {
    }

    public String observeGame(String[] params) {
    }

    public String listGames() {
    }

    public String logout() {
    }

}
