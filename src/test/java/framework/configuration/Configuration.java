package framework.configuration;

import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

public class Configuration {

    private Configuration() {
    }

    public static Credentials getCredentials(String libraryName) {
        Properties systemEnvironments = System.getProperties();
        Map<String, Object> listOfCredentials = new HashMap<>();
        String variableNameBeginning = "credentials." + libraryName + ".";
        if (systemEnvironments.keySet().stream().anyMatch(x -> ((String)x).startsWith(variableNameBeginning))) {
            Stream<Object> foundVariables = systemEnvironments.keySet().stream().filter(x -> ((String)x).startsWith(variableNameBeginning));
            foundVariables.forEach(x -> listOfCredentials.put(((String)x).replace(variableNameBeginning, ""), systemEnvironments.get(x)));
        } else {
            listOfCredentials.putAll(Environment.getEnvironment().getMap("/credentials/" + libraryName));
        }
        String randomCredentialsKey = new ArrayList<>(listOfCredentials.keySet()).get(RandomUtils.nextInt(0, listOfCredentials.size()));
        Credentials credentials = new Credentials();
        credentials.setBarcode(randomCredentialsKey);
        credentials.setPin((String) listOfCredentials.get(randomCredentialsKey));
        return credentials;
    }
}
