package handler;

import dataaccess.DataAccessException;
import handler.json.FromJson;
import handler.json.ToJson;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.GameService;
import service.UserService;
import websocket.commands.UserGameCommand;

@WebSocket
public class WebSocketHandler {
    private GameService gameService;
    private UserService userService;
    private FromJson fromJson;
    private ToJson toJson;


    public WebSocketHandler(UserService userService, GameService gameService, FromJson fromJson, ToJson toJson) {
        this.userService = userService;
        this.gameService = gameService;
        this.fromJson = fromJson;
        this.toJson = toJson;
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        try {
            UserGameCommand command = fromJson.fromJson(message, UserGameCommand.class);

            // Throws a custom UnauthorizedException. Yours may work differently.
            String username = userService.getUsernameFromAuthToken(command.getAuthString());

            saveSession(command.getGameID(), session);

            switch (command.getCommandType()) {
                case CONNECT -> connect(session, username, (ConnectCommand) command);
                case MAKE_MOVE -> makeMove(session, username, (MakeMoveCommand) command);
                case LEAVE -> leaveGame(session, username, (LeaveGameCommand) command);
                case RESIGN -> resign(session, username, (ResignCommand) command);
            }
        } catch (DataAccessException ex) {
            // Serializes and sends the error message
            sendMessage(session.getRemote(), new ErrorMessage("Error: unauthorized"));
        } catch (Exception ex) {
            ex.printStackTrace();
            sendMessage(session.getRemote(), new ErrorMessage("Error: " + ex.getMessage()));
        }
    }

    private void sendMessage(RemoteEndpoint remote, ErrorMessage errorMessage) {
        // Serializes and sends the error message
    }

    private void resign(Session session, String username, ResignCommand command) {
    }

    private void leaveGame(Session session, String username, LeaveGameCommand command) {
    }

    private void makeMove(Session session, String username, MakeMoveCommand command) {
    }

    private void connect(Session session, String username, ConnectCommand command) {
    }
}
