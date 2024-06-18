package client;

import client.websocket.ServerMessageObserver;
import com.google.gson.Gson;
import websocket.commands.ConnectCommand;
import websocket.commands.LeaveGameCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

public class WebSocketCommunicator extends Endpoint {
    private final ServerMessageObserver observer;
    private final Gson gson;
    private Session session;

    public WebSocketCommunicator(String url, ServerMessageObserver serverMessageObserver) throws Exception {
        url = url.replace("http", "ws");
        URI uri = new URI(url + "/ws");
        this.observer = serverMessageObserver;
        this.gson = new Gson();
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);
        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                try {
                    ServerMessage serverMessage =
                        gson.fromJson(message, ServerMessage.class);
                    switch(serverMessage.getServerMessageType()) {
                        case NOTIFICATION -> observer.notify(gson.fromJson(message, NotificationMessage.class));
                        case ERROR -> observer.notify(gson.fromJson(message, ErrorMessage.class));
                        case LOAD_GAME -> observer.notify(gson.fromJson(message, LoadGameMessage.class));
                    }
                } catch (Exception ex) {
                    observer.notify(new ErrorMessage(ex.getMessage()));
                }
            }
        });
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    public void connectToGame(String authToken, Integer gameID) {
        try {
            var command = new ConnectCommand(authToken, gameID);
            this.session.getBasicRemote().sendText(gson.toJson(command));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void leaveGame(String authToken, Integer gameID) {
        try {
            var command = new LeaveGameCommand(authToken, gameID);
            this.session.getBasicRemote().sendText(gson.toJson(command));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
