package chess;

import java.util.ArrayList;

public class PieceMovesCalculator {
    private ChessBoard chessBoard;
    private ChessPosition currentPosition;
    private int col;
    private int row;

    public PieceMovesCalculator(ChessBoard chessBoard, ChessPosition currentPosition) {
        this.chessBoard = chessBoard;
        this.currentPosition = currentPosition;
        this.col = currentPosition.getColumn();
        this.row = currentPosition.getRow();
    }

    public void addKingMoves(ArrayList<ChessMove> chessMoves) {
        for (int i = col - 1; i <= col + 1; i++) {
            for (int j = row - 1; j <= row + 1; j++) {
                ChessPosition newPosition = new ChessPosition(j, i);
                if (validateMove(newPosition)) chessMoves.add(new ChessMove(currentPosition, newPosition, null));
            }
        }
    }

    public void addRookMoves(ArrayList<ChessMove> chessMoves) {
        boolean upopen = true;
        boolean downopen = true;
        boolean leftopen = true;
        boolean rightopen = true;
        for (int i = 1; i <= 8; i++) {
            if (upopen) {
                ChessPosition uppos = new ChessPosition(row + i, col);
                if (validateMove(uppos)) {
                    chessMoves.add(new ChessMove(currentPosition, uppos, null));
                    if (validateCapture(uppos)) upopen = false;
                } else upopen = false;
            }
            if (downopen) {
                ChessPosition downpos = new ChessPosition(row - i, col);
                if (validateMove(downpos)) {
                    chessMoves.add(new ChessMove(currentPosition, downpos, null));
                    if (validateCapture(downpos)) downopen = false;
                } else downopen = false;
            }
            if (leftopen) {
                ChessPosition leftpos = new ChessPosition(row, col - i);
                if (validateMove(leftpos)) {
                    chessMoves.add(new ChessMove(currentPosition, leftpos, null));
                    if (validateCapture(leftpos)) leftopen = false;
                } else leftopen = false;
            }
            if (rightopen) {
                ChessPosition rightpos = new ChessPosition(row, col + i);
                if (validateMove(rightpos)) {
                    chessMoves.add(new ChessMove(currentPosition, rightpos, null));
                    if (validateCapture(rightpos)) rightopen = false;
                } else rightopen = false;
            }
        }

    }

    public void addBishopMoves(ArrayList<ChessMove> chessMoves) {
        boolean uropen = true;
        boolean dropen = true;
        boolean ulopen = true;
        boolean dlopen = true;
        for (int i = 1; i <= 8; i++) {
            if (uropen) {
                ChessPosition urpos = new ChessPosition(row + i, col + i);
                if (validateMove(urpos)) {
                    chessMoves.add(new ChessMove(currentPosition, urpos, null));
                    if (validateCapture(urpos)) uropen = false;
                } else uropen = false;
            }
            if (dropen) {
                ChessPosition drpos = new ChessPosition(row - i, col + i);
                if (validateMove(drpos)) {
                    chessMoves.add(new ChessMove(currentPosition, drpos, null));
                    if (validateCapture(drpos)) dropen = false;
                } else dropen = false;
            }
            if (ulopen) {
                ChessPosition ulpos = new ChessPosition(row + i, col - i);
                if (validateMove(ulpos)) {
                    chessMoves.add(new ChessMove(currentPosition, ulpos, null));
                    if (validateCapture(ulpos)) ulopen = false;
                } else ulopen = false;
            }
            if (dlopen) {
                ChessPosition dlpos = new ChessPosition(row - i, col - i);
                if (validateMove(dlpos)) {
                    chessMoves.add(new ChessMove(currentPosition, dlpos, null));
                    if (validateCapture(dlpos)) dlopen = false;
                } else dlopen = false;
            }
        }
    }

    public void addPawnMoves(ArrayList<ChessMove> chessMoves, ChessGame.TeamColor teamColor) {
        ChessPosition forward1 = new ChessPosition(row + (teamColor == ChessGame.TeamColor.WHITE ? 1 : -1), col);
        ChessPosition forward2 = new ChessPosition(row + (teamColor == ChessGame.TeamColor.WHITE ? 2 : -2), col);
        ChessPosition left1 = new ChessPosition(row + (teamColor == ChessGame.TeamColor.WHITE ? 1 : -1), col - 1);
        ChessPosition right1 = new ChessPosition(row + (teamColor == ChessGame.TeamColor.WHITE ? 1 : -1), col + 1);

        for (ChessPosition newPosition : new ChessPosition[]{forward1, forward2}) {
            if (validateMove(newPosition) && !validateCapture(newPosition)) {
                if (newPosition.getRow() != (teamColor == ChessGame.TeamColor.WHITE ? 8 : 1))
                    chessMoves.add(new ChessMove(currentPosition, newPosition, null));
                else {
                    for (ChessPiece.PieceType type : ChessPiece.PieceType.values()) {
                        if (type != ChessPiece.PieceType.PAWN && type != ChessPiece.PieceType.KING)
                            chessMoves.add(new ChessMove(currentPosition, newPosition, type));
                    }
                }
            } else break;if (currentPosition.getRow() != (teamColor == ChessGame.TeamColor.WHITE ? 2 : 7)) break;

        }
        for (ChessPosition newPosition : new ChessPosition[]{left1, right1}) {
            if (validateCapture(newPosition)) {
                if (newPosition.getRow() != (teamColor == ChessGame.TeamColor.WHITE ? 8 : 1))
                    chessMoves.add(new ChessMove(currentPosition, newPosition, null));
                else {
                    for (ChessPiece.PieceType type : ChessPiece.PieceType.values()) {
                        if (type != ChessPiece.PieceType.PAWN && type != ChessPiece.PieceType.KING)
                            chessMoves.add(new ChessMove(currentPosition, newPosition, type));
                    }
                }
            }
        }
    }

    public void addKnightMoves(ArrayList<ChessMove> chessMoves) {
        ChessPosition[] knightPositions =
                {new ChessPosition(row+2,col+1),
                new ChessPosition(row+2,col-1),
                new ChessPosition(row+1,col+2),
                new ChessPosition(row+1,col-2),
                new ChessPosition(row-1,col+2),
                new ChessPosition(row-1,col-2),
                new ChessPosition(row-2,col+1),
                new ChessPosition(row-2,col-1)};
        for (ChessPosition newPosition : knightPositions) {
            if (validateMove(newPosition))
                chessMoves.add(new ChessMove(currentPosition, newPosition, null));
        }
    }

    private boolean validateMove(ChessPosition newPosition) {
        if (newPosition.equals(currentPosition)) return false;
        if (newPosition.getRow() > 8 | newPosition.getColumn() > 8 |
                newPosition.getColumn() < 1 | newPosition.getRow() < 1) {
            return false;
        }
        if (chessBoard.getPiece(newPosition) != null) {
            return chessBoard.getPiece(currentPosition).getTeamColor() != chessBoard.getPiece(newPosition).getTeamColor();
        }
        return true;
    }

    private boolean validateCapture(ChessPosition newPosition) {
        if (newPosition.getRow() > 8 | newPosition.getColumn() > 8 |
                newPosition.getColumn() < 1 | newPosition.getRow() < 1) {
            return false;
        }
        if (chessBoard.getPiece(newPosition) != null) {
            return chessBoard.getPiece(currentPosition).getTeamColor() != chessBoard.getPiece(newPosition).getTeamColor();
        }
        return false;
    }



}
