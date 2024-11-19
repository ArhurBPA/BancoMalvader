package dao;

import java.sql.SQLException;

public class CustomDatabaseException extends Throwable {
    public CustomDatabaseException(String s, SQLException rollbackEx) {
    }
}
