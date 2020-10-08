package framework.configuration;

import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Configuration {

    private Configuration() {
    }

    public static Credentials getCredentials(String libraryName) {
        Map<String, String> systemEnvironments = System.getenv();
        Map<String, Object> listOfCredentials = new HashMap<>();
        String variableNameBeginning = "credentials." + libraryName + ".";
        if (systemEnvironments.keySet().stream().anyMatch(x -> x.startsWith(variableNameBeginning))) {
            Stream<String> foundVariables = systemEnvironments.keySet().stream().filter(x -> x.startsWith(variableNameBeginning));
            foundVariables.forEach(x -> listOfCredentials.put(x.replace(variableNameBeginning, ""), systemEnvironments.get(x)));
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
