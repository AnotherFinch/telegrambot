import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
public class PropertyLoader {
        static Properties props = new Properties();
        static {
            try {
                props.load(new FileInputStream("src\\main\\resources\\bot.properties"));
            } catch (IOException ignored) {
            }
        }
        static String getProperty(String key) {
            if (props.containsKey(key))
                return props.getProperty(key);
            return "";
        }
    }


