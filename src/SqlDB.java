import java.sql.*;

public class SqlDB {
    private Connection connection = null;
    private Statement statement = null;
    ResultSet resultSet = null;
    private String dbUrl, dbUser, dbPasswd;

    SqlDB(String url, String user, String passwd) {
        dbUrl = url;
        dbUser = user;
        dbPasswd = passwd;
    }

    boolean sqlConnect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPasswd);
            statement = connection.createStatement();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    boolean sqlClose() {
        if (connection == null) {
            System.out.println("Sql未连接");
            return false;
        }
        try {
            statement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Sql连接关闭失败");
            return false;
        }
    }

    ResultSet sqlExecuteQueue(String sqlExecute) {
        boolean states;
        try {
            resultSet = statement.executeQuery(sqlExecute);
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    int sqlExecuteUpdate(String sqlExecute) {
        try {
            return statement.executeUpdate(sqlExecute);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
