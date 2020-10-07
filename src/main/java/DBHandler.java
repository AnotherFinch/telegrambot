import java.sql.*;

class DBHandler extends DBConfig {
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName +
                "?serverTimezone=Europe/Moscow&useSSL=FALSE";
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        System.out.println("Бот подключился к базе данных");
        return dbConnection;
    }

    public boolean checkUser (String userId) throws SQLException {
        String queryCheck = "SELECT id FROM sys.telegramBotUsers where userId = '" + userId + "'";
        System.out.println(queryCheck);
        Statement st = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = st.executeQuery(queryCheck);
        if (rs.absolute(1)) {
            System.out.println("работает");
            dbConnection.close();
            return true;
        }
        return false;
    }



//    public void getUser(String userId) {
//        ResultSet resSet = null;
//        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USERID + " = " + userId;
//        try {
//            PreparedStatement prST = getDbConnection().prepareStatement(select);
//
//
//            resSet = prST.executeQuery();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(resSet);
//    }


}