import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;


class DBHandler extends DBConfig {
    Connection dbConnection;
    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName +
                "?serverTimezone=Europe/Moscow&useSSL=FALSE";
        Class.forName("com.mysql.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }



    public ResultSet getUser(User user){
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USER_LOGIN + "=? AND " + Const.USER_PASSWORD
                + "=?";
        try {
            PreparedStatement prST = getDbConnection().prepareStatement(select);
            prST.setString(1, user.getLogin());
            prST.setString(2, user.getPassword());

            resSet = prST.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }


}