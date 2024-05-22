package server;

import spark.*;
import handler.*;

public class Server {
    private static ClearHandler clearHandler;
    private static RegisterHandler registerHandler;
    private static LoginHandler loginHandler;
    private static LogoutHandler logoutHandler;
    private static ListGameHandler listGameHandler;
    private static CreateGameHandler createGameHandler;
    private  static JoinGameHandler joinGameHandler;

    public Server() {
        clearHandler = new ClearHandler();
        registerHandler = new RegisterHandler();
        loginHandler = new LoginHandler();
        logoutHandler = new LogoutHandler();
        listGameHandler = new ListGameHandler();
        createGameHandler = new CreateGameHandler();
        joinGameHandler = new JoinGameHandler();
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        createRoutes();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private static void createRoutes() {
        //Spark.get("/db", (req, res) -> "Hello BYU!");
        Spark.delete("/db", (req, res) ->
                clearHandler.handleRequest(req, res));
        Spark.post("/user", (req, res) ->
                registerHandler.handleRequest(req, res));
        Spark.post("/session", (req, res) ->
                loginHandler.handleRequest(req, res));
        Spark.delete("/session", (req, res) ->
                logoutHandler.handleRequest(req, res));
        Spark.get("/game", (req, res) ->
                listGameHandler.handleRequest(req, res));
        Spark.post("/game", (req, res) ->
                createGameHandler.handleRequest(req, res));
        Spark.put("/game", (req, res) ->
                joinGameHandler.handleRequest(req, res));
    }
}
