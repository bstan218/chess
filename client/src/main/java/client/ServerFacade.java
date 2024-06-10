package client;

public class ServerFacade {
    private final String serverUrl;
    private final HttpCommunicator httpCommunicator;

    public ServerFacade(String url) {
    serverUrl = url;
    httpCommunicator = new HttpCommunicator(url);
    }


}
