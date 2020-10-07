
public class User {

    private int id;
    private String userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public User(int id, String userId) {
        this.id = id;
        this.userId = userId;
    }

    public User() {

    }


//    public void loginUser(String loginText, String passwordText) {
//        DBHandler dbHandler = new DBHandler();
//        User user = new User();
//        user.setLogin(loginText);
//        user.setPassword(passwordText);
//        ResultSet result = dbHandler.getUser(user);
//
//        int counter = 0;
//        while (true) {
//            try {
//                if (!result.next()) break;
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            counter++;
//        }
//        if (counter >= 1) {
//            System.out.println("такой есть ");
//        }
//    }
}