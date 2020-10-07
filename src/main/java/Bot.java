import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import java.io.IOException;
import java.sql.SQLException;

public class Bot extends TelegramLongPollingBot {
    String userID;

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
            System.out.println("бот запущен");
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        sendMsg(update.getMessage().getChatId().toString(), message);

        String userName = update.getMessage().getFrom().getUserName();
       // System.out.println(userName);
        userID = userName;

//        DBHandler dbHandler = new DBHandler();
//        try {
//            dbHandler.getDbConnection();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        try {
//            dbHandler.checkUser(userID);
//            System.out.println("Такой юзер есть в базе данных");
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
    }


    public synchronized void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        Buttons.setStartButtons(sendMessage);
        if (s.equals("Я состою в команде")){
            DBHandler dbHandler = new DBHandler();
            try {
                dbHandler.getDbConnection();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                boolean d = dbHandler.checkUser(userID);
                System.out.println(d);
                if (d == true) {
                    System.out.println("Такой юзер есть в базе данных");
                    Buttons.setTeamMembersButtons(sendMessage);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                System.out.println("Такой пользователь не зарегистрирован?");
            }
        }
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // метод передачи картинки, в данный момент работает и отправляет
    public void showMeHowToGo(String chatId) throws TelegramApiException {
        SendPhoto sendPhotoRequest = new SendPhoto();
        sendPhotoRequest.setChatId(chatId);
        sendPhotoRequest.setPhoto("https://sun1-87.userapi.com/_OG-jadMFB6bDIGJHvagGmFCF1j8YTG_laktxw/fLcSMcVXDPo.jpg");
        sendPhoto(sendPhotoRequest);
    }

    public void answerCallbackQuery(String callbackId, String message) {
        AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(callbackId);
        answer.setText(message);
        answer.setShowAlert(true);
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotToken() {
        try {
            return Property.getProperty("TOKEN");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getBotUsername() {
        try {
            return Property.getProperty("BOT_NAME");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}




