package websocket.messages;

import chess.ChessGame;

public class LoadGameMessage extends ServerMessage {
    private ChessGame game;

    public LoadGameMessage(ChessGame chessGame) {
        this.serverMessageType = ServerMessageType.LOAD_GAME;
        this.game = chessGame;
    }

    public ChessGame getGame() {
        return game;
    }


}
