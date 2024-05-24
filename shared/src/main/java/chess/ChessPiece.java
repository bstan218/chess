package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private ChessGame.TeamColor teamColor;
    private ChessPiece.PieceType type;

    public ChessPiece(ChessGame.TeamColor teamColor, ChessPiece.PieceType type) {
        this.teamColor = teamColor;
        this.type = type;
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
        return teamColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() { return type; }

    public void setPieceType(PieceType pieceType) {
        type = pieceType;
    }
    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard chessBoard, ChessPosition currentPosition) {
        ArrayList<ChessMove> chessMoves = new ArrayList<>();
        PieceMovesCalculator pieceMovesCalculator = new PieceMovesCalculator(chessBoard, currentPosition);

        boolean isrook = false;
        switch (type) {
            case KING:
                pieceMovesCalculator.addKingMoves(chessMoves);
                break;
            case ROOK:
                isrook = true;
            case QUEEN:
                pieceMovesCalculator.addRookMoves(chessMoves);
                if (isrook) break;
            case BISHOP:
                pieceMovesCalculator.addBishopMoves(chessMoves);
                break;
            case PAWN:
                pieceMovesCalculator.addPawnMoves(chessMoves, teamColor);
                break;
            case KNIGHT:
                pieceMovesCalculator.addKnightMoves(chessMoves);
                break;
        } return chessMoves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return teamColor == that.teamColor && type == that.type;
    }

}
