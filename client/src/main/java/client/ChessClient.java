package client;

import model.GameData;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class ChessClient {
    private ServerFacade facade;
    private SignInState signInState;
    private RequestState requestState;
    private String authToken;
    private List<GameData> gameList;

    public ChessClient(String serverUrl) {
        facade = new ServerFacade(serverUrl);
        signInState = SignInState.SIGNEDOUT;
        requestState = null;
        authToken = null;

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

            RequestState currentRequestState = requestState;
            if (requestState == null) { return loginUiOptions(cmd); }
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
                    yield "created game successfully!\n" + help();
                }
                case PLAYGAME -> {
                    facade.playGame(params, gameList, authToken);
                    yield "joined game successfully." + printGameBoard();
                }
                case OBSERVEGAME -> printGameBoard();
            };

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private String printGameBoard() {

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

    private String playGameRequest() {
        requestState = RequestState.PLAYGAME;
        return "Enter the number of the game you would like to join\n" +
                "and what color you want to play as: <number> <white/black>\n" + gameListAsString();
    }

    private String observeGameRequest() {
        requestState = RequestState.OBSERVEGAME;
        return "Enter the number of the game you would like to observe:\n" + gameListAsString();
    }


}
