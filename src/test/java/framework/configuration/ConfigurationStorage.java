package framework.configuration;

import aquality.appium.mobile.application.AqualityServices;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigurationStorage {
    private static Map<Long, String> map = new ConcurrentHashMap<>();

    public static synchronized void lockCredentials(String barcode) {
        AqualityServices.getLogger().info("Locking credential with barcode " + barcode);
        map.put(Thread.currentThread().getId(), barcode);
    }

    public static synchronized void unlockCredentials() {
        AqualityServices.getLogger().info("Unlocking credential for current thread");
        map.remove(Thread.currentThread().getId());
    }

    public static synchronized boolean areCredentialsLocked(String barcode) {
        return map.containsValue(barcode);
    }
}
