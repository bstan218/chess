package chess;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    private ChessPiece[][] board; //[col][row]

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
        int col = position.getColumn();
        int row = position.getRow();
        board[col-1][row-1] = piece;


    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        int col = position.getColumn();
        int row = position.getRow();
        return board[col-1][row-1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        board = new ChessPiece[8][8];
        for (int i = 0; i < board.length; i++) { //col
            for (int j = 0; j < board[0].length; j++) { //row
                ChessGame.TeamColor teamcolor = ChessGame.TeamColor.BLACK;
                if (j > 4) {
                    teamcolor = ChessGame.TeamColor.WHITE;
                }
                if (j == 1 | j == 6) {
                    board[i][j] = new ChessPiece(teamcolor, ChessPiece.PieceType.PAWN);
                }
                else if (j == 0 | j == 7) {
                    switch (i) {
                        case 0:
                        case 7:
                            board[i][j] = new ChessPiece(teamcolor, ChessPiece.PieceType.ROOK);
                            break;
                        case 1:
                        case 6:
                            board[i][j] = new ChessPiece(teamcolor, ChessPiece.PieceType.KNIGHT);
                            break;
                        case 2:
                        case 5:
                            board[i][j] = new ChessPiece(teamcolor, ChessPiece.PieceType.BISHOP);
                            break;
                        case 3:
                            board[i][j] = new ChessPiece(teamcolor, ChessPiece.PieceType.QUEEN);
                            break;
                        case 4:
                            board[i][j] = new ChessPiece(teamcolor, ChessPiece.PieceType.KING);
                            break;
                    }
                }
            }
        }
    }


}
