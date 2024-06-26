package dataaccess;

import model.AuthData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SqlAuthDAO implements AuthDAO {

    public SqlAuthDAO() {
        try {
            DatabaseManager.configureDatabase(createStatements);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  auth (
              `authToken` varchar(256) NOT NULL,
              `username` varchar(256) NOT NULL,
              PRIMARY KEY (`authToken`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    @Override
    public void deleteAllAuths() throws DataAccessException {
        var statement = "TRUNCATE auth";
        DatabaseManager.executeUpdate(statement);
    }

    @Override
    public AuthData createAuth(String username) throws DataAccessException {
        String authToken = UUID.randomUUID().toString();
        var statement = "INSERT INTO auth (authToken, username) VALUES (?, ?)";
        try {
            DatabaseManager.executeUpdate(statement, authToken, username);
            return new AuthData(authToken, username);
        } catch (Exception ne) {
            throw new DataAccessException("Error: Unable to insert user into database");
        }
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT authToken, username FROM auth WHERE authToken=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readAuth(rs);
                    } else {
                        throw new DataAccessException("Error: authorization is invalid");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private AuthData readAuth(ResultSet rs) throws SQLException {
        var authToken = rs.getString("authToken");
        var username = rs.getString("username");
        return new AuthData(authToken, username);
    }


    @Override
    public void deleteAuth(String authToken) {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "DELETE FROM auth WHERE authToken=?";
            try {
                DatabaseManager.executeUpdate(statement, authToken);
            } catch (Exception ne) {
                throw new DataAccessException("Error: Unable to insert user into database");
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
}