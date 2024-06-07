package dataaccess;

import model.UserData;

import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;


public class SqlUserDAO implements UserDAO {

    public SqlUserDAO() {
        try  {
            DatabaseManager.configureDatabase(createStatements);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  user (
              `username` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(256) NOT NULL,
              PRIMARY KEY (`username`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };


    @Override
    public void deleteAllUsers() throws DataAccessException {
        var statement = "TRUNCATE user";
        DatabaseManager.executeUpdate(statement);
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password, email FROM user WHERE username=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                try(var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readUser(rs);
                    } else {
                        throw new DataAccessException("Error: No user with that username");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private UserData readUser(ResultSet rs) throws SQLException {
        var username = rs.getString("username");
        var password = rs.getString("password");
        var email = rs.getString("email");
        return new UserData(username, password, email);
    }

    @Override
    public void createUser(UserData userData) throws DataAccessException {
        try {
            getUser(userData.username());
        } catch (DataAccessException e) {
            var statement = "INSERT INTO user (username, password, email) VALUES (?, ?, ?)";
            try {
                DatabaseManager.executeUpdate(statement, userData.username(), userData.password(), userData.email());
                return;
            } catch (Exception ne) {
                throw new DataAccessException("Error: Unable to insert user into database");
            }
        } throw new DataAccessException("Error: User already in database");
    }

    @Override
    public boolean usersIsEmpty() {
        return false;
    }
}
