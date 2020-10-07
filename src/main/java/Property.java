import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Property {

    public static String getProperty(String key) throws IOException {
        File file = new File("/Users/user/IdeaProjects/untitled/src/main/resources/bot.properties");
        Properties properties = new Properties();
        properties.load(new FileReader(file));
        properties.containsKey(key);
        return properties.getProperty(key);
    }
}
