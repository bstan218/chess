package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private ChessBoard gameboard;
    private TeamColor teamturn;

    public ChessGame() {
        setTeamTurn(TeamColor.WHITE);
        this.gameboard = new ChessBoard();
        this.gameboard.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamturn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamturn = team;
    }

    public void swapTeamTurn() {
        if (teamturn == TeamColor.BLACK)
            setTeamTurn(TeamColor.WHITE);
        else setTeamTurn(TeamColor.BLACK);
    }
    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ArrayList<ChessMove> validMoves = new ArrayList<>();
        ChessPiece piece = gameboard.getPiece(startPosition);
        if (piece == null) return null;
        for (ChessMove move : piece.pieceMoves(gameboard,startPosition)) {
            ChessBoard currentBoard = gameboard.makeDeepCopy();
            try {
                makeMove(move);
            } catch (InvalidMoveException e) {
                gameboard.addPiece(move.getStartPosition(),null);
                gameboard.addPiece(move.getEndPosition(),piece);
                if (isInCheck(piece.getTeamColor())) {
                    gameboard = currentBoard;
                    continue;
                }
            }
            if (isInCheckmate(piece.getTeamColor())) continue;
            gameboard = currentBoard;
            validMoves.add(move);
        }
        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition startposition = move.getStartPosition();
        ChessPosition endposition = move.getEndPosition();
        ChessPiece piece = gameboard.getPiece(startposition);

        if (piece == null) throw new InvalidMoveException();
        if (piece.getTeamColor() != teamturn) throw new InvalidMoveException();



        for (ChessMove collectionmove : piece.pieceMoves(gameboard,startposition)) {
            if (collectionmove.getEndPosition().equals(endposition)) {
                ChessBoard currentBoard = gameboard.makeDeepCopy();
                gameboard.addPiece(startposition,null);
                gameboard.addPiece(endposition,piece);
                if (move.getPromotionPiece() != null) {
                    gameboard.getPiece(endposition).setPieceType(move.getPromotionPiece());
                }
                if (isInCheck(piece.getTeamColor())) {
                    gameboard = currentBoard;
                    throw new InvalidMoveException();
                }
                swapTeamTurn();
                return;
            }
        }
        throw new InvalidMoveException();

    }




    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        TeamColor opposingTeamColor = TeamColor.BLACK;
        if (teamColor == TeamColor.BLACK) opposingTeamColor = TeamColor.WHITE;

        ChessPosition kingPosition = gameboard.getKingPosition(teamColor);

        for (int i = 1; i <= 8; i++) { //col
            for (int j = 1; j <= 8; j++) {
                ChessPosition currentPosition = new ChessPosition(j, i);
                ChessPiece currentPiece = gameboard.getPiece(currentPosition);
                if (currentPiece != null && currentPiece.getTeamColor() == opposingTeamColor) {
                    for (ChessMove move : currentPiece.pieceMoves(gameboard,currentPosition)) {
                        if (move.getEndPosition().equals(kingPosition))
                            return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if (!isInCheck(teamColor)) return false;
        ChessBoard currentGameBoard = gameboard.makeDeepCopy();
        //iterate through every possible move for teamColor
        for (int i = 1; i <= 8; i++) { //col
            for (int j = 1; j <= 8; j++) {
                ChessPosition currentPosition = new ChessPosition(j, i);
                ChessPiece currentPiece = currentGameBoard.getPiece(currentPosition);
                if (currentPiece != null && currentPiece.getTeamColor() == teamColor) {
                    for (ChessMove move : currentPiece.pieceMoves(currentGameBoard, currentPosition)) {
                        ChessBoard copiedBoard = currentGameBoard.makeDeepCopy();
                        gameboard = copiedBoard;
                        try {
                            makeMove(move);
                        } catch (InvalidMoveException ignored) {
                        }
                        if (!isInCheck(teamColor)) {
                            gameboard = currentGameBoard;
                            return false;
                        }


                    }
                }
            }
        }

                        //clone board for each move
        //check if move is in check
        //if a move isnt in check, return false
    gameboard = currentGameBoard;
    return true;
    }


    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        gameboard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return gameboard;
    }
}
