package websocket.commands;

public class LeaveGameCommand extends UserGameCommand {

    public LeaveGameCommand(String authToken, Integer gameID) {
        super(authToken);
        this.gameID = gameID;
        this.commandType = CommandType.LEAVE;
    }

}
