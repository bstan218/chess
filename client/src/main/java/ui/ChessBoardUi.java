package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;

public class ChessBoardUi {
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_CHARS = 3;
    private static final String EMPTY = "   ";

    public ChessBoardUi() {}



    public void drawBothGames() {
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.resetBoard();
        drawGame(chessBoard, ChessGame.TeamColor.WHITE);
        drawGame(chessBoard, ChessGame.TeamColor.BLACK);
    }

    public void drawGame(ChessBoard chessBoard, ChessGame.TeamColor teamColor) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        drawHeaders(out, teamColor);

        drawChessBoard(chessBoard, teamColor, out);

        out.println();
    }

    private static void drawHeaders(PrintStream out, ChessGame.TeamColor teamColor) {

        setBlack(out);
        
        int headerIndex = 0;
        String[] headers = { " a ", " b ", " c ", " d ", " e ", " f ", " g ", " h " };
        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {

            switch (teamColor) {
                case WHITE -> headerIndex = boardCol;
                case BLACK -> headerIndex = BOARD_SIZE_IN_SQUARES-boardCol-1;
            }

            drawHeader(out, headers[headerIndex]);


        }

        out.println();
    }

    private static void drawHeader(PrintStream out, String headerText) {
        int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
        int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;

        out.print(EMPTY.repeat(prefixLength));
        printHeaderText(out, headerText);
        out.print(EMPTY.repeat(suffixLength));
    }

    private static void printHeaderText(PrintStream out, String player) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);

        out.print(player);

        setBlack(out);
    }

    private static void drawChessBoard(ChessBoard chessBoard, ChessGame.TeamColor teamColor, PrintStream out) {
        String[] sideLabels = { " 8 ", " 7 ", " 6 ", " 5 ", " 4 ", " 3 ", " 2 ", " 1 " };
        SquareColor currentSquareColor = SquareColor.DARK;

        int rowIndex = 0;
        int colIndex = 0;
        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {
            currentSquareColor = switchSquareColor(out, currentSquareColor);
            for (int squareRow = 0; squareRow < SQUARE_SIZE_IN_CHARS; ++squareRow) {
                for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
                    currentSquareColor = switchSquareColor(out, currentSquareColor);

                    if (squareRow == SQUARE_SIZE_IN_CHARS / 2) {
                        int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
                        int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;

                        switch (teamColor) {
                            case WHITE -> {
                                rowIndex = boardRow+1;
                                colIndex = boardCol+1;
                            }
                            case BLACK -> {
                                rowIndex = BOARD_SIZE_IN_SQUARES-boardRow;
                                colIndex = BOARD_SIZE_IN_SQUARES-boardCol;
                            }
                        }


                        ChessPiece chessPiece = chessBoard.getPiece(new ChessPosition(rowIndex,colIndex));
                        String pieceChar = pieceTypeToChar(chessPiece);
                        out.print(EMPTY.repeat(prefixLength));
                        if (chessPiece != null) {
                            switch (chessPiece.getTeamColor()) {
                                case WHITE -> out.print(SET_TEXT_COLOR_RED);
                                case BLACK -> out.print(SET_TEXT_COLOR_BLUE);
                            }
                        }
                        out.print(pieceChar);
                        out.print(EMPTY.repeat(suffixLength));
                        if (boardCol == BOARD_SIZE_IN_SQUARES-1) {
                            printHeaderText(out, sideLabels[rowIndex-1]);
                        }
                    }
                    else {
                        out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
                    }


                    setBlack(out);
                }
                out.println();
            }

        }
    }

    private static SquareColor switchSquareColor(PrintStream out, SquareColor currentSquareColor) {
        switch (currentSquareColor) {
            case LIGHT -> {
                currentSquareColor = SquareColor.DARK;
                setWhite(out);
            }
            case DARK -> {
                currentSquareColor = SquareColor.LIGHT;
                setGrey(out);
            }

        }
        return currentSquareColor;
    }

    private enum SquareColor {
        LIGHT,
        DARK
    }

    private static String pieceTypeToChar(ChessPiece piece) {
        if (piece == null) {return "   ";};
        return switch (piece.getPieceType()) {
            case QUEEN -> " Q ";
            case KING -> " K ";
            case BISHOP -> " B ";
            case PAWN -> " P ";
            case ROOK -> " R ";
            case KNIGHT -> " N ";
            case null -> "   ";
        };
    }

    private static void setWhite(PrintStream out) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void setGrey(PrintStream out) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
    }



}
