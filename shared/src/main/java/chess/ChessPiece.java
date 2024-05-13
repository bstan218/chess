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
    private ChessGame.TeamColor pieceColor;
    private ChessPiece.PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
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
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() { return type; }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition pos) {
        ArrayList<ChessMove> collection = new ArrayList<>();
        int col = pos.getColumn();
        int row = pos.getRow();

        boolean isrook = false;
        switch (type) {
            case KING:
                for (int i = col - 1; i <= col + 1; i++) {
                    for (int j = row - 1; j <= row + 1; j++) {
                        ChessPosition newpos = new ChessPosition(j, i);
                        if (validateMove(board, pos, newpos)) collection.add(new ChessMove(pos, newpos, null));
                    }
                }
                break;

            case ROOK:
                isrook = true;
            case QUEEN:
                boolean upopen = true;
                boolean downopen = true;
                boolean leftopen = true;
                boolean rightopen = true;
                for (int i = 1; i <= 8; i++) {
                    if (upopen) {
                        ChessPosition uppos = new ChessPosition(row + i, col);
                        if (validateMove(board, pos, uppos)) {
                            collection.add(new ChessMove(pos, uppos, null));
                            if (validateCapture(board, pos, uppos)) upopen = false;
                        } else upopen = false;
                    }
                    if (downopen) {
                        ChessPosition downpos = new ChessPosition(row - i, col);
                        if (validateMove(board, pos, downpos)) {
                            collection.add(new ChessMove(pos, downpos, null));
                            if (validateCapture(board, pos, downpos)) downopen = false;
                        } else downopen = false;
                    }
                    if (leftopen) {
                        ChessPosition leftpos = new ChessPosition(row, col - i);
                        if (validateMove(board, pos, leftpos)) {
                            collection.add(new ChessMove(pos, leftpos, null));
                            if (validateCapture(board, pos, leftpos)) leftopen = false;
                        } else leftopen = false;
                    }
                    if (rightopen) {
                        ChessPosition rightpos = new ChessPosition(row, col + i);
                        if (validateMove(board, pos, rightpos)) {
                            collection.add(new ChessMove(pos, rightpos, null));
                            if (validateCapture(board, pos, rightpos)) rightopen = false;
                        } else rightopen = false;
                    }
                }
                if (isrook) break;

            case BISHOP:
                boolean uropen = true;
                boolean dropen = true;
                boolean ulopen = true;
                boolean dlopen = true;
                for (int i = 1; i <= 8; i++) {
                    if (uropen) {
                        ChessPosition urpos = new ChessPosition(row + i, col + i);
                        if (validateMove(board, pos, urpos)) {
                            collection.add(new ChessMove(pos, urpos, null));
                            if (validateCapture(board, pos, urpos)) uropen = false;
                        } else uropen = false;
                    }
                    if (dropen) {
                        ChessPosition drpos = new ChessPosition(row - i, col + i);
                        if (validateMove(board, pos, drpos)) {
                            collection.add(new ChessMove(pos, drpos, null));
                            if (validateCapture(board, pos, drpos)) dropen = false;
                        } else dropen = false;
                    }
                    if (ulopen) {
                        ChessPosition ulpos = new ChessPosition(row + i, col - i);
                        if (validateMove(board, pos, ulpos)) {
                            collection.add(new ChessMove(pos, ulpos, null));
                            if (validateCapture(board, pos, ulpos)) ulopen = false;
                        } else ulopen = false;
                    }
                    if (dlopen) {
                        ChessPosition dlpos = new ChessPosition(row - i, col - i);
                        if (validateMove(board, pos, dlpos)) {
                            collection.add(new ChessMove(pos, dlpos, null));
                            if (validateCapture(board, pos, dlpos)) dlopen = false;
                        } else dlopen = false;
                    }
                }
                break;
            case PAWN:
                ChessPosition forward1 = new ChessPosition(row + (pieceColor == ChessGame.TeamColor.WHITE ? 1 : -1), col);
                ChessPosition forward2 = new ChessPosition(row + (pieceColor == ChessGame.TeamColor.WHITE ? 2 : -2), col);
                ChessPosition left1 = new ChessPosition(row + (pieceColor == ChessGame.TeamColor.WHITE ? 1 : -1), col - 1);
                ChessPosition right1 = new ChessPosition(row + (pieceColor == ChessGame.TeamColor.WHITE ? 1 : -1), col + 1);

                for (ChessPosition newposition : new ChessPosition[]{forward1, forward2}) {
                    if (validateMove(board, pos, newposition) && !validateCapture(board, pos, newposition)) {
                        if (newposition.getRow() != (pieceColor == ChessGame.TeamColor.WHITE ? 8 : 1))
                            collection.add(new ChessMove(pos, newposition, null));
                        else {
                            for (PieceType type : PieceType.values()) {
                                if (type != PieceType.PAWN && type != PieceType.KING)
                                    collection.add(new ChessMove(pos, newposition, type));
                            }
                        }
                    } else break;if (pos.getRow() != (pieceColor == ChessGame.TeamColor.WHITE ? 2 : 7)) break;

                }
                for (ChessPosition newposition : new ChessPosition[]{left1, right1}) {
                    if (validateCapture(board, pos, newposition)) {
                        if (newposition.getRow() != (pieceColor == ChessGame.TeamColor.WHITE ? 8 : 1))
                            collection.add(new ChessMove(pos, newposition, null));
                        else {
                            for (PieceType type : PieceType.values()) {
                                if (type != PieceType.PAWN && type != PieceType.KING)
                                    collection.add(new ChessMove(pos, newposition, type));
                            }
                        }
                    }
                }
                break;
            case KNIGHT:
                ChessPosition[] knightpositions =
                {new ChessPosition(row+2,col+1),
                new ChessPosition(row+2,col-1),
                new ChessPosition(row+1,col+2),
                new ChessPosition(row+1,col-2),
                new ChessPosition(row-1,col+2),
                new ChessPosition(row-1,col-2),
                new ChessPosition(row-2,col+1),
                new ChessPosition(row-2,col-1)};
                for (ChessPosition newposition : knightpositions) {
                    if (validateMove(board, pos, newposition))
                        collection.add(new ChessMove(pos, newposition, null));
                }


        } return collection;
    }



    private boolean validateMove(ChessBoard board, ChessPosition currentposition, ChessPosition newposition) {
        if (newposition.equals(currentposition)) return false;
        if (newposition.getRow() > 8 | newposition.getColumn() > 8 |
            newposition.getColumn() < 1 | newposition.getRow() < 1) {
            return false;
        }
        if (board.getPiece(newposition) != null) {
            return board.getPiece(currentposition).getTeamColor() != board.getPiece(newposition).getTeamColor();
        }
        return true;
    }

    private boolean validateCapture(ChessBoard board, ChessPosition currentposition, ChessPosition newposition) {
        if (newposition.getRow() > 8 | newposition.getColumn() > 8 |
            newposition.getColumn() < 1 | newposition.getRow() < 1) {
            return false;
        }
        if (board.getPiece(newposition) != null) {
            return board.getPiece(currentposition).getTeamColor() != board.getPiece(newposition).getTeamColor();
        }
        return false;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

}
