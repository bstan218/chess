package client;

import chess.ChessGame;
import client.state.*;
import client.websocket.ServerMessageObserver;
import model.GameData;
import ui.ChessBoardUi;
import websocket.messages.*;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class ChessClient implements ServerMessageObserver  {
    private final ServerFacade facade;
    private final ChessBoardUi chessBoardUi;

    private SignInState signInState;
    private RequestState requestState;
    private PlayState playState;
    private boolean observerState;

    private String authToken;
    private List<GameData> gameList;
    private ChessGame chessGame = null;
    private int currentGameID;


    public ChessClient(String serverUrl) throws Exception {
        facade = new ServerFacade(serverUrl, this);
        signInState = SignInState.SIGNEDOUT;
        requestState = null;
        playState = PlayState.OUTOFGAME;
        authToken = null;
        chessBoardUi = new ChessBoardUi();
        observerState = false;
    }

    public void run() {
        System.out.println("\uD83D\uDC36 Welcome to Britton's Chess!");
        System.out.print(help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = eval(line);
                System.out.print(SET_TEXT_COLOR_BLUE + result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
                System.out.print(SET_TEXT_COLOR_BLUE + eval("help"));
            }
        }
        System.out.println();
    }

    private void printPrompt() {
        System.out.print("\n" + RESET_TEXT_COLOR + ">>> " + SET_TEXT_COLOR_GREEN);
    }


    public String eval(String input) {
        try {
            var tokens = input.split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 0, tokens.length);

            if (requestState == null) {
                if (playState != PlayState.OUTOFGAME) {
                    return gameUiOptions(cmd);
                } else {
                    return loginUiOptions(cmd);
                }
            }

            return executeRequest(params);

        } catch (Exception e) {
            return e.getMessage() + "\n" + help();
        }
    }

    private String executeRequest(String[] params) throws ResponseException {
        RequestState currentRequestState = requestState;
        requestState = null;

        return switch (currentRequestState) {
            case LOGIN -> {
                this.authToken = facade.login(params);
                signInState = SignInState.SIGNEDIN;
                yield "Successfully logged in!\n" + help();
            }
            case REGISTER -> {
                this.authToken = facade.register(params);
                signInState = SignInState.SIGNEDIN;
                yield "Successfully registered!\n" + help();
            }
            case CREATEGAME -> {
                facade.createGame(params, authToken);
                this.chessGame = new ChessGame();
                yield "created game successfully!\n" + help();
            }
            case PLAYGAME -> {
                currentGameID = facade.playGame(params, gameList, authToken, this.playState);

                printGameBoard(getTeam());
                yield "joined game successfully.";
            }
            case OBSERVEGAME -> {
                currentGameID = facade.observeGame(params, gameList, authToken);
                playState = PlayState.OBSERVER;
                observerState = true;

                printGameBoard(ChessGame.TeamColor.WHITE);
                yield "now observing game.";
            }
            case MAKEMOVE -> {
                facade.makeMove(params, authToken);
                yield "";
            }
            case HIGHLIGHTMOVE -> {
                highlightMove(params, authToken);
                yield "";
            }
        };
    }

    private void highlightMove(String[] params, String authToken) {
    }

    private void printGameBoard(ChessGame.TeamColor teamColor) {
        chessBoardUi.drawGame(chessGame.getBoard(), teamColor);
    }

    private String gameUiOptions(String cmd) {
        return switch(cmd) {
            case "1" -> makeMoveRequest();
            case "2" -> {
                printGameBoard(getTeam());
                yield "";
            }
            case "3" -> highlightMoveRequest();
            case "4" -> {
                facade.leaveGame(authToken, currentGameID);
                currentGameID = 0;
                playState = PlayState.OUTOFGAME;
                observerState = false;
                yield "you have left the game\n" + help();
            }
            case "5" -> {
                if (observerState) {
                    yield "you are an observer, you can't resign";
                }
                facade.resign(authToken, currentGameID);
                yield help();
            }
            default -> help();
        };
    }

    private ChessGame.TeamColor getTeam() {
        if (playState == PlayState.WHITE | playState == PlayState.OBSERVER) {
            return ChessGame.TeamColor.WHITE;
        } else {
            return ChessGame.TeamColor.BLACK;
        }
    }

    private String makeMoveRequest() {
        requestState = RequestState.MAKEMOVE;
        return "enter: <starting position> <ending position>";
    }

    private String highlightMoveRequest() {
        requestState = RequestState.HIGHLIGHTMOVE;
        return "enter: <piece position>";
    }

    private String loginUiOptions(String cmd) throws ResponseException {
        if (signInState == SignInState.SIGNEDOUT) {
            return switch (cmd) {
                case "1" -> loginRequest();
                case "2" -> registerRequest();
                case "4" -> "quit";
                default -> help();
            };
        }

        return switch (cmd) {
            case "1" -> createGameRequest();
            case "2" -> {
                this.gameList = facade.listGames(authToken);
                yield gameListAsString();
            }
            case "3" -> playGameRequest();
            case "4" -> observeGameRequest();
            case "5" -> {
                facade.logout(this.authToken);
                this.signInState = SignInState.SIGNEDOUT;
                yield "logout successful\n" + help();
            }
            default -> help();
        };
    }

    public String help() {
        if (playState != PlayState.OUTOFGAME) {
            return """
                    1. Make Move
                    2. Redraw Chess Board
                    3. Highlight legal moves
                    4. Leave
                    5. Resign
                    6. Help
                    """;
        }

        if (signInState == SignInState.SIGNEDOUT) {
            return """
                    1. Login
                    2. Register
                    3. Help
                    4. Quit
                    """;
        }
        return """
                1. Create Game
                2. List Games
                3. Play Game
                4. Observe Game
                5. Logout
                6. Help
                """;
    }

    private String gameListAsString() {
        StringBuilder returnList = new StringBuilder();
        for (int i = 0; i < gameList.size(); i++) {
            GameData currentGame = gameList.get(i);
            returnList.append(String.format("%d. %s: white- %s, black- %s\n", i + 1, currentGame.gameName(), currentGame.whiteUsername(), currentGame.blackUsername()));
        }
        return returnList.toString();
    }

    private String registerRequest() {
        requestState = RequestState.REGISTER;
        return "Enter: <username> <password> <email>";
    }

    private String loginRequest() {
        requestState = RequestState.LOGIN;
        return "Enter: <username> <password>";
    }

    private String createGameRequest() {
        requestState = RequestState.CREATEGAME;
        return "Enter the name of the game:";
    }

    private String playGameRequest() throws ResponseException {
        requestState = RequestState.PLAYGAME;
        this.gameList = facade.listGames(authToken);
        return "Enter the number of the game you would like to join\n" +
                "and what color you want to play as: <number> <white/black>\n" + gameListAsString();
    }

    private String observeGameRequest() throws ResponseException {
        requestState = RequestState.OBSERVEGAME;
        this.gameList = facade.listGames(authToken);
        return "Enter the number of the game you would like to observe:\n" + gameListAsString();
    }


    @Override
    public void notify(ServerMessage message) {
        switch (message.getServerMessageType()) {
            case NOTIFICATION -> displayNotification(((NotificationMessage) message).getMessage());
            case ERROR -> displayError(((ErrorMessage) message).getErrorMessage());
            case LOAD_GAME -> loadGame(((LoadGameMessage) message).getGame());
        }
    }

    private void displayNotification(String message) {
        System.out.print(SET_TEXT_COLOR_BLUE + message);
    }

    private void displayError(String errorMessage) {
        System.out.print(SET_TEXT_COLOR_RED + errorMessage);
    }

    private void loadGame(ChessGame chessGame) {
        this.chessGame = chessGame;
    }
}
