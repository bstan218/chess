package client;

import java.util.Arrays;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class ChessClient {
    private ServerFacade facade;
    private SignInState signInState;
    private RequestState requestState;

    public ChessClient(String serverUrl) {
        facade = new ServerFacade(serverUrl);
        signInState = SignInState.SIGNEDOUT;
        requestState = null;

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

            if (requestState.equals(RequestState.LOGIN)) {
                requestState = null;
                return facade.login(params);
            }
            else if (requestState.equals(RequestState.REGISTER)) {
                requestState = null;
                return facade.register(params);
            }
            else if (requestState.equals(RequestState.CREATEGAME)) {
                requestState = null;
                return facade.createGame(params);
            }
            else if (requestState.equals(RequestState.PLAYGAME)) {
                requestState = null;
                return facade.playGame(params);
            }
            else if (requestState.equals(RequestState.OBSERVEGAME)) {
                requestState = null;
                return facade.observeGame(params);
            }


            if (signInState.equals(SignInState.SIGNEDOUT)) {
                return switch (cmd) {
                    case "1" -> loginRequest();
                    case "2" -> registerRequest();
                    case "4" -> "quit";
                    default -> help();
                };
            }
            return switch (cmd) {
                case "1" -> createGameRequest();
                case "2" -> facade.listGames();
                case "3" -> playGameRequest();
                case "4" -> observeGameRequest();
                case "5" -> facade.logout();
                default -> help();
            };
        } catch (Exception ex) {
            return ex.getMessage();
        }
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
        return "Enter the name of the game you would like to create:";
    }

    private String playGameRequest() {
        requestState = RequestState.PLAYGAME;
        return "Enter the listed number of the game you would like to join:";
    }

    private String observeGameRequest() {
        requestState = RequestState.OBSERVEGAME;
        return "Enter the listed number of the game you would like to join:";
    }


}
