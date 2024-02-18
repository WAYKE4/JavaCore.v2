import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseWriter {
    /**
     * The method implements jdbc and create/write data to the database
     */
    public static void InsertIntoDB(ArrayList<String> arrOut) {
        Connection connection;
        PreparedStatement preparedStatement;
        try {
            Class.forName(Config.JDBC_DRIVER);
            connection = DriverManager.getConnection(Config.DATABASE_URL, Config.DATABASE_USER, Config.DATABASE_PASSWORD);
            preparedStatement = connection.prepareStatement(Config.TRUNCATE_TABLE);
            preparedStatement.executeUpdate();
            for (int i = 0; i < arrOut.size(); i++) {
                preparedStatement = connection.prepareStatement(Config.INSERT_INTO_DB);
                preparedStatement.setString(1, arrOut.get(i).substring(0, arrOut.get(i).indexOf(":")));
                preparedStatement.setString(2, arrOut.get(i).substring(arrOut.get(i).indexOf(":") + 1));
                preparedStatement.executeUpdate();
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }

    public static void UpdateDB(String lineback, int sumLines) {
        Connection connection;
        PreparedStatement preparedStatement;
        try {
            Class.forName(Config.JDBC_DRIVER);
            connection = DriverManager.getConnection(Config.DATABASE_URL, Config.DATABASE_USER, Config.DATABASE_PASSWORD);
            preparedStatement = connection.prepareStatement(Config.UPDATE_DB);
            preparedStatement.setString(1, String.valueOf(sumLines));
            preparedStatement.setString(2, lineback.substring(0, lineback.length() - 1));
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }
}

