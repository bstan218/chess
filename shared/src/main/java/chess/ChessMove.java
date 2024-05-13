package chess;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {
    private ChessPosition startPosition;
    private ChessPosition endPosition;
    private ChessPiece.PieceType promotionPiece;

    public ChessMove(ChessPosition startPosition, ChessPosition endPosition,
                     ChessPiece.PieceType promotionPiece) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.promotionPiece = promotionPiece;
    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {
        return startPosition;
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {
        return endPosition;
    }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece() {
        return promotionPiece;
    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * (hash + startPosition.hashCode());
        hash = 31 * (hash + endPosition.hashCode());
        if (promotionPiece != null) hash = 31 * (hash + promotionPiece.hashCode());
        return hash;

    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) return true;
        if (!(obj instanceof ChessMove)) return false;
        ChessMove eobj = (ChessMove) obj;
        if (!(eobj.getStartPosition().equals(startPosition) && eobj.getEndPosition().equals(endPosition))) return false;
        if (!(eobj.getPromotionPiece() == null && promotionPiece == null)) {
            if (eobj.getPromotionPiece() == null | promotionPiece == null) return false;
            return eobj.getPromotionPiece().equals(promotionPiece);
        }
        return true;
    }

}
