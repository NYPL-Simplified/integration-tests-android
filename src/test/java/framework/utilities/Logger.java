package framework.utilities;

import aquality.appium.mobile.application.AqualityServices;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Logger {

    private static ThreadLocal<Logger> instance = ThreadLocal.withInitial(Logger::new);
    private RollingFileAppender scenarioAppender;

    private Logger() {
    }

    public static synchronized Logger getInstance() {
        return (Logger) instance.get();
    }


    public void createAppender(String name) {
        PatternLayout layout = new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %-5p - %m%n");
        try {
            scenarioAppender = new RollingFileAppender(
                    layout, String.format("%s%s.log", "target/log/", name), false);
            AqualityServices.getLogger().addAppender(scenarioAppender);
        } catch (IOException e) {
            AqualityServices.getLogger().error("Failed to add appender !!");
        }
    }

    public byte[] getLoggerInfoBytes() {
        byte[] data = new byte[0];
        try {
            data = Files.readAllBytes(Paths.get(scenarioAppender.getFile()));
        } catch (IOException exception) {
            AqualityServices.getLogger().error(exception.toString());
        }
        return data;
    }

    public void removeAppender() {
        if (scenarioAppender != null) {
            AqualityServices.getLogger().removeAppender(scenarioAppender);
        }
    }
}
