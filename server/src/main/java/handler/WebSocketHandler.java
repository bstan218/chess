package handler;

import dataaccess.DataAccessException;
import json.FromJson;
import json.ToJson;
import handler.websocket.ConnectionManager;
import model.GameData;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.GameService;
import service.UserService;
import websocket.commands.*;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {
    private GameService gameService;
    private UserService userService;
    private FromJson fromJson;
    private ToJson toJson;

    private ConnectionManager connectionManager;


    public WebSocketHandler(UserService userService, GameService gameService, FromJson fromJson, ToJson toJson) {
        this.userService = userService;
        this.gameService = gameService;
        this.fromJson = fromJson;
        this.toJson = toJson;

        this.connectionManager = new ConnectionManager();
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        try {
            UserGameCommand userGameCommand = fromJson.fromJson(message, UserGameCommand.class);

            // Throws a custom UnauthorizedException. Yours may work differently.
            String username = userService.getUsernameFromAuthToken(userGameCommand.getAuthString());

            connectionManager.saveSession(userGameCommand.getGameID(), session);

            switch (userGameCommand.getCommandType()) {
                case CONNECT -> connect(session, username, new ConnectCommand(userGameCommand.getAuthString(), userGameCommand.getGameID()));
                case MAKE_MOVE -> makeMove(session, username, (MakeMoveCommand) userGameCommand);
                case LEAVE -> leaveGame(session, username, fromJson.fromJson(message,LeaveGameCommand.class));
                case RESIGN -> resign(session, username, (ResignCommand) userGameCommand);
            }
        } catch (DataAccessException ex) {
            // Serializes and sends the error message
            sendMessage(session.getRemote(), new ErrorMessage("Error: unauthorized"));
        } catch (Exception ex) {
            ex.printStackTrace();
            sendMessage(session.getRemote(), new ErrorMessage("Error: " + ex.getMessage()));
        }
    }


    private void sendMessage(RemoteEndpoint remote, ErrorMessage errorMessage) throws IOException {
        // Serializes and sends the error message
        remote.sendString(toJson.fromResponse(errorMessage));
    }

    private void resign(Session session, String username, ResignCommand command) {
    }

    private void leaveGame(Session session, String username, LeaveGameCommand command) throws DataAccessException, IOException {
        String authToken = command.getAuthString();
        GameData currentGameData = gameService.getGame(command.getGameID());

        GameData updatedGameData = null;

        if (username.equals(currentGameData.blackUsername())) {
            updatedGameData = new GameData(0, currentGameData.whiteUsername(), null, null, currentGameData.game());
        }
        if (username.equals(currentGameData.whiteUsername())) {
            updatedGameData = new GameData(0, null, currentGameData.blackUsername(), null, currentGameData.game());
        }

        if (updatedGameData != null) {
            gameService.updateGame(command.getGameID(), updatedGameData);
        }

        ServerMessage serverMessage = new NotificationMessage(String.format("%s has left the game \n", username));
        connectionManager.broadcast(command.getGameID(), session, toJson.fromResponse(serverMessage));
    }

    private void makeMove(Session session, String username, MakeMoveCommand command) {
    }

    private void connect(Session session, String username, ConnectCommand command) throws IOException, DataAccessException {
        String role = "an observer";
        GameData gameData = gameService.getGame(command.getGameID());
        if (username.equals(gameData.blackUsername())) {
            role = "the black player";
        }
        if (username.equals(gameData.whiteUsername())) {
            role = "the white player";
        }
        ServerMessage serverMessage = new NotificationMessage(String.format("%s has joined the game as %s \n", username, role));
        connectionManager.broadcast(command.getGameID(), session, toJson.fromResponse(serverMessage));


        LoadGameMessage gameMessage = new LoadGameMessage(gameData.game());
        session.getRemote().sendString(toJson.fromResponse(gameMessage));

    }
}
