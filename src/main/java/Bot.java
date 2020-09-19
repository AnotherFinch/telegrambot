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
import java.util.*;

public class Bot extends TelegramLongPollingBot {
    final String ddd = "приходи";
    final String ABOUT = "Мы мужская команда по американскому футболу Sechenov Phoenix Moscow." +
            "Мы набираем в команду потенциальных игроков и дебилов, добро пожаловать, будем рады";
    String userID;
    public int phoneNumber;
    final String[] adjective = {"фамбл - потеря мяча", "за 4 попытки надо пройти 10 ярдов", "филд гол дает 3 очка", "на поле может быть одновременно 11 человек"};

    /**
     * процесс инициализации бота , подключение апи и регистрации его
     */
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
    /**
     * Метод для приема сообщений.
     *
     *
     * @param update Содержит сообщение от пользователя.
     *               данный метод обязательный, без него телеглам не будет воспринимать написанный текст
     *               помимо этого данный метод позволяет вытаскивать информацию о пользователе
     */
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        //данный блок позволяет получить никнейм пользователя
        String userName = update.getMessage().getFrom().getUserName();
        System.out.println(userName);
        userID = userName;
        try {
            sendMsg(update.getMessage().getChatId().toString(), message);
        } catch (TelegramApiException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * процесс приемки сообщения от клиента и обратно
     */
    public synchronized void sendMsg(String chatId, String s) throws TelegramApiException, IOException {
        // сделать массив строк и зарандомить его
        Random random = new Random();
        int index = random.nextInt(adjective.length);

        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        Buttons.setStartButtons(sendMessage);

// вместо моего ника надо вставить проверку есть ли такой в базе данных
        if (userID.equals("Another_finch")) {//ldmirandov

            DBHandler dbHandler = new DBHandler();
            try {
                dbHandler.getDbConnection();
                System.out.println("подключился");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (userID.equals("Another_finch")){//ldmirandov

            sendMessage.setText("Привет, надеюсь ты посетишь следующую тренировку?");
            Buttons.setTeamMembersButtons(sendMessage);
//            if (s.toLowerCase().equals("как пройти")) {
//                sendMessage.setText("Стадион буревестник находится по адресу, ближайшее метро и так далее ");
//                showMeHowToGo(chatId);
//            }if (s.toLowerCase().equals("я состою в команде")){
//                Buttons.setTeamMembersButtons(sendMessage);
//            }
                //sendMessage.setText(userID);

        }else if (s.equals("лох2")){
            Buttons.setAboutUsButtons(sendMessage);
            sendMessage.setText("сам ты");
        }else if (s.toLowerCase().equals("я хочу в команду")){
            Buttons.setIWantToBeInTeam(sendMessage);
            sendMessage.setText(ddd);
        }else if (s.toLowerCase().equals("о нас")){
            sendMessage.setText(ABOUT);
        }else if (s.toLowerCase().equals("расскажите о команде")){
            Buttons.setAboutUsButtons(sendMessage);
            sendMessage.setText("что вы хотите узнать?");
        }

        else {
            sendMessage.setText(adjective[index]);
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
//



    public synchronized void answerCallbackQuery(String callbackId, String message) {
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

// почему то не работает токен, вернусь к нему позже
        public String getBotUsername() {
            return PropertyLoader.getProperty("TELEGRAM_BOT_NAME");
        }
        public String getBotToken() {
            //return PropertyLoader.getProperty("TOKEN");
           return "1171841256:AAEM9wt1yyC5XFL-9BQoK7aURFpEeqg7gsA";
        }
    }

