package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private PieceType type;
    private ChessGame.TeamColor pieceColor;

    public ChessPiece(ChessGame.TeamColor pieceColor, PieceType type) {
        this.type = type;
        this.pieceColor = pieceColor;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition pos) {
        Collection<ChessMove> collection = new ArrayList<>();
        int col = pos.getColumn()-1; //index
        int row = pos.getRow()-1; //index
        if (board.getBoard()[col][row] == null) {
            return collection;
        }
        PieceType piecetype = board.getBoard()[col][row].getPieceType();
        switch (piecetype) {
            case KING:
                for (int i=col-1; i <= col+1; i++) {
                    for (int j=row-1; j <= row+1; j++) {
                        ChessPosition newposition = new ChessPosition(j+1,i+1);
                        if (validateMove(board, pos, newposition)) {
                            collection.add(new ChessMove(pos, newposition, null));
                        }
                    }
                }
                break;
            case ROOK:
                //pathway 1 - up:
                for (int i = row+2; i <= board.getBoard().length+1; i++) {
                    ChessPosition newposition = new ChessPosition(i, col+1);
                    if (validateMove(board, pos, newposition)) {
                        collection.add(new ChessMove(pos, newposition, null));
                        if (validateCapture(board, pos, newposition)) break;
                    }
                    else break;
                }
                for (int i = row; i >= 0; i--) {
                    ChessPosition newposition = new ChessPosition(i, col+1);
                    if (validateMove(board, pos, newposition)) {
                        collection.add(new ChessMove(pos, newposition, null));
                        if (validateCapture(board, pos, newposition)) break;
                    }
                    else break;
                }
                for (int i = col+2; i <= board.getBoard().length+1; i++) {
                    ChessPosition newposition = new ChessPosition(row+1, i);
                    if (validateMove(board, pos, newposition)) {
                        collection.add(new ChessMove(pos, newposition, null));
                        if (validateCapture(board, pos, newposition)) break;
                    }
                    else break;
                }
                for (int i = col; i >= 0; i--) {
                    ChessPosition newposition = new ChessPosition(row+1, i);
                    if (validateMove(board, pos, newposition)) {
                        collection.add(new ChessMove(pos, newposition, null));
                        if (validateCapture(board, pos, newposition)) break;
                    }
                    else break;
                }
                break;
            case BISHOP:
                for (int i = 1; i < board.getBoard().length+1; i++) {
                    ChessPosition newposition = new ChessPosition(row+1+i, col+1+i);
                    if (validateMove(board, pos, newposition)) {
                        collection.add(new ChessMove(pos, newposition, null));
                        if (validateCapture(board, pos, newposition)) break;
                    }
                    else break;
                }
                for (int i = 1; i < board.getBoard().length+1; i++) {
                    ChessPosition newposition = new ChessPosition(row+1+i, col+1-i);
                    if (validateMove(board, pos, newposition)) {
                        collection.add(new ChessMove(pos, newposition, null));
                        if (validateCapture(board, pos, newposition)) break;
                    }
                    else break;
                }
                for (int i = 1; i < board.getBoard().length+1; i++) {
                    ChessPosition newposition = new ChessPosition(row+1-i, col+1-i);
                    if (validateMove(board, pos, newposition)) {
                        collection.add(new ChessMove(pos, newposition, null));
                        if (validateCapture(board, pos, newposition)) break;
                    }
                    else break;
                }
                for (int i = 1; i < board.getBoard().length+1; i++) {
                    ChessPosition newposition = new ChessPosition(row+1-i, col+1+i);
                    if (validateMove(board, pos, newposition)) {
                        collection.add(new ChessMove(pos, newposition, null));
                        if (validateCapture(board, pos, newposition)) break;
                    }
                    else break;
                }
                break;
            case QUEEN:
                for (int i = 1; i < board.getBoard().length+1; i++) {
                    ChessPosition newposition = new ChessPosition(row+1+i, col+1+i);
                    if (validateMove(board, pos, newposition)) {
                        collection.add(new ChessMove(pos, newposition, null));
                        if (validateCapture(board, pos, newposition)) break;
                    }
                    else break;
                }
                for (int i = 1; i < board.getBoard().length+1; i++) {
                    ChessPosition newposition = new ChessPosition(row+1+i, col+1-i);
                    if (validateMove(board, pos, newposition)) {
                        collection.add(new ChessMove(pos, newposition, null));
                        if (validateCapture(board, pos, newposition)) break;
                    }
                    else break;
                }
                for (int i = 1; i < board.getBoard().length+1; i++) {
                    ChessPosition newposition = new ChessPosition(row+1-i, col+1-i);
                    if (validateMove(board, pos, newposition)) {
                        collection.add(new ChessMove(pos, newposition, null));
                        if (validateCapture(board, pos, newposition)) break;
                    }
                    else break;
                }
                for (int i = 1; i < board.getBoard().length+1; i++) {
                    ChessPosition newposition = new ChessPosition(row+1-i, col+1+i);
                    if (validateMove(board, pos, newposition)) {
                        collection.add(new ChessMove(pos, newposition, null));
                        if (validateCapture(board, pos, newposition)) break;
                    }
                    else break;
                }
                for (int i = row+2; i <= board.getBoard().length+1; i++) {
                    ChessPosition newposition = new ChessPosition(i, col+1);
                    if (validateMove(board, pos, newposition)) {
                        collection.add(new ChessMove(pos, newposition, null));
                        if (validateCapture(board, pos, newposition)) break;
                    }
                    else break;
                }
                for (int i = row; i >= 0; i--) {
                    ChessPosition newposition = new ChessPosition(i, col+1);
                    if (validateMove(board, pos, newposition)) {
                        collection.add(new ChessMove(pos, newposition, null));
                        if (validateCapture(board, pos, newposition)) break;
                    }
                    else break;
                }
                for (int i = col+2; i <= board.getBoard().length+1; i++) {
                    ChessPosition newposition = new ChessPosition(row+1, i);
                    if (validateMove(board, pos, newposition)) {
                        collection.add(new ChessMove(pos, newposition, null));
                        if (validateCapture(board, pos, newposition)) break;
                    }
                    else break;
                }
                for (int i = col; i >= 0; i--) {
                    ChessPosition newposition = new ChessPosition(row+1, i);
                    if (validateMove(board, pos, newposition)) {
                        collection.add(new ChessMove(pos, newposition, null));
                        if (validateCapture(board, pos, newposition)) break;
                    }
                    else break;
                }
                break;
            case PAWN:
                ChessGame.TeamColor teamColor = board.getPiece(pos).getTeamColor();
                int i = -1;
                if (teamColor == ChessGame.TeamColor.WHITE) i = 1; //white path
                ChessPosition firstposition = new ChessPosition(row+1+i, col+1);
                if (validateMove(board, pos, firstposition) && !(validateCapture(board, pos, firstposition))) {
                    if (firstposition.getRow() == (i == 1 ? 8 : 1)) {
                        for (ChessPiece.PieceType type : ChessPiece.PieceType.values()) {
                            if (type != PieceType.PAWN && type != PieceType.KING) collection.add(new ChessMove(pos, firstposition, type));
                        }
                    }
                    else collection.add(new ChessMove(pos, firstposition, null));
                    ChessPosition secondposition = new ChessPosition(row+1+2*i, col+1);
                    if (validateMove(board, pos, secondposition) &&
                            row == (i == 1 ? 1 : 6) &&
                            !(validateCapture(board, pos, secondposition))) {
                        collection.add(new ChessMove(pos, secondposition, null));
                    }
                }
                ChessPosition leftpos = new ChessPosition(row+1+i, col);
                if (validateCapture(board, pos, leftpos)) {
                    if (firstposition.getRow() == (i == 1 ? 8 : 1)) {
                        for (ChessPiece.PieceType type : ChessPiece.PieceType.values()) {
                            if (type != PieceType.PAWN && type != PieceType.KING) collection.add(new ChessMove(pos, leftpos, type));
                        }
                    }
                    else collection.add(new ChessMove(pos, leftpos, null));
                }
                ChessPosition rightpos = new ChessPosition(row+1+i, col+2);
                if (validateCapture(board, pos, rightpos)) {
                    if (firstposition.getRow() == (i == 1 ? 8 : 1)) {
                        for (ChessPiece.PieceType type : ChessPiece.PieceType.values()) {
                            if (type != PieceType.PAWN && type != PieceType.KING) collection.add(new ChessMove(pos, rightpos, type));
                        }
                    }
                    else collection.add(new ChessMove(pos, rightpos, null));
                }
                break;
            case KNIGHT:
                ChessPosition[] possiblemoves =
                        {new ChessPosition(row+3,col+2),
                        new ChessPosition(row+3,col),
                        new ChessPosition(row+2,col+3),
                        new ChessPosition(row+2,col-1),
                        new ChessPosition(row,col-1),
                        new ChessPosition(row,col+3),
                        new ChessPosition(row-1,col+2),
                        new ChessPosition(row-1,col)};
                for (ChessPosition newposition : possiblemoves) {
                    if (validateMove(board,pos,newposition)) {
                        collection.add(new ChessMove(pos, newposition, null));
                    }
                }
                break;
        }
        return collection;
    }

    /**
     * Validates whether ending position is out of bounds, the current position,
     * or blocked by piece of same team.
     *
     * @param board
     * @param startingpos
     * @param endingpos
     * @return bool for if position is blocked or not
     */
    private boolean validateMove(ChessBoard board, ChessPosition startingpos, ChessPosition endingpos) {
        int i = endingpos.getColumn();
        int j = endingpos.getRow();
        int col = startingpos.getColumn();
        int row = startingpos.getRow();

        if (!(i > 0 && i <= board.getBoard().length &&
                j > 0 && j <= board.getBoard()[0].length &&
                !(i == col && j == row))) return false;

        boolean samePieceColor = false;
        if (board.getPiece(startingpos) != null && board.getPiece(endingpos) != null) {
            samePieceColor = (board.getPiece(startingpos).getTeamColor() == board.getPiece(endingpos).getTeamColor());
        }
        return !samePieceColor;

    }
    private boolean validateCapture(ChessBoard board, ChessPosition startingpos, ChessPosition endingpos) { //returns true if it's a capture
        if (board.getPiece(startingpos) != null && board.getPiece(endingpos) != null) {
            return (board.getPiece(startingpos).getTeamColor() != board.getPiece(endingpos).getTeamColor());
        }
        return false;
    }
}
