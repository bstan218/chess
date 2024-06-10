package client.websocket;

import web;

public interface ServerMessageObserver {
    void notify(ServerMessage message);
}
