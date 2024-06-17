package handler.websocket;

import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public ConcurrentHashMap<Integer, HashSet<Session>> connections = new ConcurrentHashMap<>();

    public void saveSession(Integer gameID, Session session) {
        if (connections.containsKey(gameID)) {
            Set<Session> set = connections.get(gameID);
            set.add(session);
        } else {
            HashSet<Session> newSet = new HashSet<Session>();
            newSet.add(session);
            connections.put(gameID, newSet);
        }
    }
    public void broadcast(Integer gameID, Session excludeRootSession, String serverMessage) throws IOException {
        var removeList = new ArrayList<Session>();
        for (var s : connections.get(gameID)) {
            if (s.isOpen()) {
                if (!s.equals(excludeRootSession)) {
                    s.getRemote().sendString(serverMessage);
                }
            } else {
                removeList.add(s);
            }
        }

        // Clean up any connections that were left open.
        for (var c : removeList) {
            connections.get(gameID).remove(c);
        }
    }
}
