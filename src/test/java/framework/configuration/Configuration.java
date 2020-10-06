package framework.configuration;

import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.Map;

public class Configuration {

    private Configuration() {
    }

    public static String getEbookCardValue() {
        return Environment.getEnvironment().getValue("/ebookcard").toString();
    }

    public static String getEbookPinValue() {
        return Environment.getEnvironment().getValue("/ebookpin").toString();
    }

    public static Credentials getCredentials(String libraryName) {
        Map<String, Object> listOfCredentials = Environment.getEnvironment().getMap("/credentials/" + libraryName);
        String randomCredentialsKey = new ArrayList<>(listOfCredentials.keySet()).get(RandomUtils.nextInt(0, listOfCredentials.size()));
        return new Credentials(randomCredentialsKey, (String) listOfCredentials.get(randomCredentialsKey));
    }
}
