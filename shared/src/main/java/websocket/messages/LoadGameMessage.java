package websocket.messages;

import chess.ChessGame;

public class LoadGameMessage extends ServerMessage {
    private ChessGame game;

    public LoadGameMessage(ServerMessageType type, ChessGame chessGame) {
        super(type);

    }

    public ChessGame getGame() {
        return game;
    }


}
