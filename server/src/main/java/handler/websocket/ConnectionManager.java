package handler.websocket;

import org.eclipse.jetty.websocket.api.Session;

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
}
