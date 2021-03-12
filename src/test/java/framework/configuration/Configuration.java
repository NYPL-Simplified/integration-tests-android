package framework.configuration;

import aquality.appium.mobile.application.AqualityServices;
import org.apache.commons.lang3.RandomUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Configuration {

    private Configuration() {
    }

    public static Credentials getCredentials(String libraryName) {
        Properties systemEnvironments = System.getProperties();
        Map<String, Object> listOfCredentials = new HashMap<>();
        String osName = AqualityServices.getApplication().getPlatformName().toString().toLowerCase();
        String variableNameBeginning = String.format("credentials.%s.%s.", libraryName, osName);
        if (systemEnvironments.keySet().stream().anyMatch(x -> ((String) x).startsWith(variableNameBeginning))) {
            Stream<Object> foundVariables = systemEnvironments.keySet().stream().filter(x -> ((String) x).startsWith(variableNameBeginning));
            foundVariables.forEach(x -> listOfCredentials.put(((String) x).replace(variableNameBeginning, ""), systemEnvironments.get(x)));
        } else {
            listOfCredentials.putAll(Environment.getEnvironment().getMap(String.format("/credentials/%s/%s", libraryName, osName)));
        }
        ArrayList<String> credentialsKeys = new ArrayList<>(listOfCredentials.keySet());
        List<String> listOfAvailableCredentials = credentialsKeys.stream().filter(barcode -> !ConfigurationStorage.areCredentialsLocked(barcode)).collect(Collectors.toList());
        String randomCredentialsKey = listOfAvailableCredentials.get(RandomUtils.nextInt(0, listOfAvailableCredentials.size()));
        Credentials credentials = new Credentials();
        credentials.setBarcode(randomCredentialsKey);
        credentials.setPin((String) listOfCredentials.get(randomCredentialsKey));
        ConfigurationStorage.lockCredentials(randomCredentialsKey);
        return credentials;
    }

    public static String getOpds(String libraryName) {
        Properties systemEnvironments = System.getProperties();
        ArrayList<String> listOfCredentials = new ArrayList<>();
        String variableNameBeginning = "feed.";
        if (systemEnvironments.keySet().stream().anyMatch(x -> ((String) x).startsWith(variableNameBeginning))) {
            Stream<Object> foundVariables = systemEnvironments.keySet().stream().filter(x -> x.equals(variableNameBeginning + libraryName));
            foundVariables.forEach(x -> listOfCredentials.add((String) systemEnvironments.get(x)));
        } else {
            listOfCredentials.add((String) Environment.getEnvironment().getMap("/feed").get(libraryName));
        }
        return listOfCredentials.get(RandomUtils.nextInt(0, listOfCredentials.size()));
    }
}
