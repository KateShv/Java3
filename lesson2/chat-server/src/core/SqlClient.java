package core;

import java.sql.*;

public class SqlClient {

    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement ps;

    synchronized static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:chat-server/chat.db");
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    synchronized static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    synchronized static String getNickname(String login, String password) {
        String query = String.format("select nickname from users where login='%s' and password='%s'", login, password);
        try (ResultSet set = statement.executeQuery(query)) {
            if (set.next())
                return set.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    //подготовку можно вынести в коннект в принципе, хотя надо подумать
    synchronized static boolean changeNickname(String currentNick, String newNick) throws SQLException {
        ps = connection.prepareStatement("select nickname from users where nickname = ?");
        ps.setString(1, newNick);
        try (ResultSet set = ps.executeQuery()) {
            if (!set.next()) {
                ps = connection.prepareStatement("update users set nickname = ? where nickname = ?");
                ps.setString(1, newNick);
                ps.setString(2, currentNick);
                ps.executeUpdate();
                return true;
            }
            return false;
        }
    }

}
