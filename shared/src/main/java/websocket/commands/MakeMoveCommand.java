package websocket.commands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand {
    private Integer gameID;
    private ChessMove move;

    public MakeMoveCommand(String authToken, Integer gameID, ChessMove move) {
        super(authToken);
        this.commandType = CommandType.MAKE_MOVE;
        this.gameID = gameID;
        this.move = move;
    }

    public Integer getGameID() {
        return this.gameID;
    }

    public ChessMove getMove() {
        return this.move;
    }


}
