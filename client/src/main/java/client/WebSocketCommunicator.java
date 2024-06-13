package client;

import client.websocket.ServerMessageObserver;
import com.google.gson.Gson;
import websocket.messages.ErrorMessage;
import websocket.messages.ServerMessage;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketCommunicator extends Endpoint {
    private final ServerMessageObserver observer;
    private final Gson gson;
    private WebSocketContainer container;
    private Session session;

    public WebSocketCommunicator(String url, ServerMessageObserver serverMessageObserver) throws Exception {
        URI uri = new URI(url + "/wb");
        this.observer = serverMessageObserver;
        this.gson = new Gson();
        this.container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);
        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                try {
                    ServerMessage serverMessage =
                        gson.fromJson(message, ServerMessage.class);
                    observer.notify(serverMessage);
                } catch (Exception ex) {
                    observer.notify(new ErrorMessage(ServerMessage.ServerMessageType.ERROR, ex.getMessage()));
                }
            }
        });
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
}
