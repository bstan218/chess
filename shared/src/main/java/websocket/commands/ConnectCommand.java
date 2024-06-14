package websocket.commands;

public class ConnectCommand extends UserGameCommand {
    private Integer gameID;

    public ConnectCommand(String authToken, Integer gameID) {
        super(authToken);
        this.commandType = CommandType.CONNECT;
        this.gameID = gameID;
    }

    public Integer getGameID() {
        return gameID;
    }
}
