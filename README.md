# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5xDAaTgALdvYoALIoAIyY9lAQAK7YMADEaMBUljAASij2SKoWckgQaAkA7r5IYGKIqKQAtAB85JQ0UABcMADaAAoA8mQAKgC6MAD00QZQADpoAN6jlEkAtigANDC46kXQHCso88BICAC+mMJNMHWs7FyUrVMARLNQCyi3zbcwtyu3a6obUBwvbw+7x2ewQAPeRzYnG4sHOJ1ErSgmWy5SgAAoMlkcpQMgBHaJqMAASmOjVEZ3qsnkShU6lagTAAFUxmiHk8SVTFMo1KoKUYdK0yABRAAyQrgvRgbOAixgADMovMpWNMJyaTyznCySpWmhoggEKSRCo+WruXSYCAkXIUMzKKyxk8Vt9flsYCD9hztOr1HzjK0AJIAOWFaUl0sWzv0P02212+xgwd6XWVtHhJvOZtpqlaVpQNoU0TAvgdcxlKC91PNvPO-sTIaFYdTj3LK2ARd8vQgAGt0PXkzB28XVd7q5r6umUK0h52e+gjVRydVzlCri10sjsVAMqp9VhVzDxw1qNcYHcI88YK1AZ8Z13e2g3teIQvKEfKuZWgAmJxOSYzR1W0HDt73QI50A4UxIhiOJ4mgAJDBFCAskKeISjKCpkHMPkTlPToegGYYDHUfI0H-C8o3WTYjlw2F6gPU9z0AxZwSBL5o1dJ93hfBi6OPRcdRgBBkKQNA0SQlC8QJHISUnU1R2zVoqGAZAtHKO10Qo1YOM2SsuWzP0BXIUVxXDZjlm0qi-nlRVmwXJdKQUnlc2tcpC2LUsW0WPSfRreo62DUMzLLSNgOLUDCiTFMZxHKsDOXCdtSnMLZwfeyMxXS4YVaCTRJ3PdMF4o9aJue5zK4m93jvOdHyvbjbhoxo3wS+AsIwb9f3I8y2xAmrwM4KColiBJIhQPsJJiZg0NKcpMA-GpEpPdc2mkEzeiFQYhmI1RSMmaq0tovleNK-b0FYyEsuaxaBOS4T7Em8TkMmqTCVkpL5Li5yYAZdyS1OtAfLHWsjOFMUJRSiKbIgJUYrkzMnItDgUG4NyOzRf7Afi-yjNWsV1ohmqVgvKGYY7dLDBa46YAmot8oQfdLthLUlo6pxXyZ+p5rAVmzAGiIhtgpEOGplEYAAcXLXlpowua2uYZmmlaNoxaFQihnscs9t6g6mr4qm7n+87CsZnCktaZAcglxZVHElErbUF6ZPJj79K+n60Yx2LXd9YHBRM8H-pJlLnfhz6LQtsB7ZtzGNV94ywclDXraDpOxCzWOFYRb7JZFFEQ8y6FT1zy3JbphnC45-jGNTnN2imVOA2kFZ0PU7rJh0BBQG7DSnWzxYg3LA5+kapb3zl1n-xrpX6-LRvm5m2027QDuu57oDU4HxYh-6yD+ZghJsGiKBsG4eBXMMe3igX2Wqnl668O6PpNtTrXwpqyfy03lAR9OAu1xO7WZ1XjvE+BvcsRsiqZ0EnAc+9s0QwPzOUe2jtiQh0cmHWu7sPKe3Tj7bGfsE4EwfEHWG71Q7e0wSgSO5Y0RgO8l7XyhkCGmT7igL+KdywMKBlA5KeoDRoJkAjWueYbRwLoRWLhWN+SBgbE2cR-YUyp0kRna6WcG7SHzvRRmrQEGiPLGXY2Fdiq6xuOon+zVzhcwntMMxvNd7QWGvESwyNhJFBgAAKQgKJcW5YEgrxAN2G+2EFYP0ZGrF+SRAFkWmCfYAzioBwAgMJKAKxbGHT-tlM8txDbALYrE+JiTkmsVAbPaQECTY8NaAAKy8WgOBnjRLIJQPiV6AjcGULAL9dGUSY54OkfHFhgcFTQ2DnDdBFDWixDUigMRpTnSdwKUk6APU34Pl6X5fpgVGyJzmasBZlBCnLKIX2KKrDNEXArq0BpdT9FqAKpAkJ64fxs3SZY8eMBnl2MGvveIkQ4mtXzLAYA2AT6EDyAUK+MsuamxZu0XGqsNrDGMOzI62jvoQDUDASAMhkZUMMLxFYaAIAwCqaoAohi1wwpurmbgeB4G0u3M06SqCxmCIwTSwFaJ1lMJkGtIUKVeTDNJsOdpPL4X4weIK2yDxlF9LrOK-lNcOGLHOVTRAgKDEPPvk838KKWpWI+b+GAXygA)
## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```
