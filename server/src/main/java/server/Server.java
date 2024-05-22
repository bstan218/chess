package server;

import spark.*;
import handler.*;

public class Server {
    private static DeleteHandler deleteHandler;
    private static RegisterHandler registerHandler;

    public Server() {
        deleteHandler = new DeleteHandler();
        registerHandler = new RegisterHandler();
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
                deleteHandler.handleRequest(req, res));
        Spark.post("/user", (req, res))
    }
}
