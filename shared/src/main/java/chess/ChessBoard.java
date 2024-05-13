package chess;

import java.util.Arrays;
import java.util.Objects;
import java.util.ArrayList;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] board;
    public ChessBoard() {
        this.board = new ChessPiece[8][8];
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getColumn()-1][position.getRow()-1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getColumn()-1][position.getRow()-1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */

    public ArrayList<ChessPiece> getAllPieces() {
        ArrayList<ChessPiece> allPieces = new ArrayList<>();
        for (int i = 1; i <= board.length; i++) { //col
            for (int j = 1; j <= board.length; j++) {
                ChessPiece currentPiece = getPiece(new ChessPosition(j,i));
                if (currentPiece != null) allPieces.add(currentPiece);
            }
        }
        return allPieces;
    }

    public ChessPosition getKingPosition(ChessGame.TeamColor teamColor) {

        for (int i = 1; i <= board.length; i++) { //col
            for (int j = 1; j <= board.length; j++) {
                ChessPosition currentPosition = new ChessPosition(j,i);
                ChessPiece currentPiece = getPiece(currentPosition);
                if (currentPiece != null) {
                    if (currentPiece.getPieceType() == ChessPiece.PieceType.KING &&
                            currentPiece.getTeamColor() == teamColor)
                        return currentPosition;
                }
            }
        }
        return null;
    }

    public void resetBoard() {
        ChessGame.TeamColor color;
        for (int i = 0; i < board.length; i++) { //col
            for (int j = 0; j < board.length; j++) { //row
                ChessPosition position = new ChessPosition(j+1,i+1);
                if (j > 4) color = ChessGame.TeamColor.BLACK;
                else color = ChessGame.TeamColor.WHITE;
                if (j == 1 | j == 6) {
                    addPiece(position, new ChessPiece(color, ChessPiece.PieceType.PAWN));
                }
                if (j == 0 | j == 7) {
                    switch (i) {
                        case 0:
                        case 7:
                            addPiece(position, new ChessPiece(color, ChessPiece.PieceType.ROOK));
                            break;
                        case 1:
                        case 6:
                            addPiece(position, new ChessPiece(color, ChessPiece.PieceType.KNIGHT));
                            break;
                        case 2:
                        case 5:
                            addPiece(position, new ChessPiece(color, ChessPiece.PieceType.BISHOP));
                            break;
                        case 4:
                            addPiece(position, new ChessPiece(color, ChessPiece.PieceType.KING));
                            break;
                        case 3:
                            addPiece(position, new ChessPiece(color, ChessPiece.PieceType.QUEEN));
                            break;
                    }
                }

            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        for (int i = 0; i < board.length; i++) { //col
            for (int j = 0; j < board.length; j++) { //row
                ChessPosition position = new ChessPosition(j + 1, i + 1);
                if (getPiece(position) == null | that.getPiece(position) == null) {
                    return getPiece(position) == null && that.getPiece(position) == null;
                }
                if (!(getPiece(position).equals(that.getPiece(position)))) return false;
            }
        }
        return true;
    }

    public ChessBoard makeDeepCopy() {
        ChessBoard copyBoard = new ChessBoard();
        for (int i = 0; i < board.length; i++) { //col
            for (int j = 0; j < board.length; j++) { //row
                ChessPosition currentPosition = new ChessPosition(j+1,i+1);
                if (getPiece(currentPosition) != null) {
                    ChessPiece currentPiece = getPiece(currentPosition);
                    copyBoard.addPiece(currentPosition, new ChessPiece(currentPiece.getTeamColor(),currentPiece.getPieceType()));
                }
            }
        }
                return copyBoard;
    }
}
