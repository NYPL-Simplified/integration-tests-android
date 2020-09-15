package factories.steps;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.reflections.Reflections;
import org.reflections.ReflectionsException;
import stepdefinitions.BaseSteps;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.stream.Collectors;

public class StepsFactory implements IStepsFactory {

    private static final String STEPS_LOCATION = "stepdefinitions";

    private static volatile StepsFactory stepsFactory = null;

    private StepsFactory() {
    }

    public static StepsFactory getStepsFactory() {
        StepsFactory localInstance = stepsFactory;
        if (localInstance == null) {
            synchronized (StepsFactory.class) {
                localInstance = stepsFactory;
                if (localInstance == null) {
                    stepsFactory = localInstance = new StepsFactory();
                }
            }
        }
        return localInstance;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends BaseSteps> T getSteps(Class<T> clazz) {
        Class<?> tClass = getPlatformClasses().stream()
                .filter(clazz::isAssignableFrom)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Implementation for Screen %s was not found in package %s",
                                clazz.getName(), getPackageWithSteps())));
        try {
            return (T) ConstructorUtils.invokeConstructor(tClass);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new IllegalArgumentException("Something went wrong during steps casting", e);
        }
    }

    private Set<Class<?>> getPlatformClasses() {
        Reflections reflections = new Reflections(getPackageWithSteps());
        try {
            PlatformName platformName = AqualityServices.getApplicationProfile().getPlatformName();
            return reflections.getTypesAnnotatedWith(StepsType.class).stream()
                    .filter(clazz -> clazz.getAnnotation(StepsType.class).platform() == platformName)
                    .collect(Collectors.toSet());
        } catch (ReflectionsException e) {
            throw new IllegalArgumentException(String.format("Could not find package \"%s\" with Screens. " +
                    "Please specify value \"screensLocation\" in settings file.", getPackageWithSteps()), e);
        }
    }

    private String getPackageWithSteps() {
        return STEPS_LOCATION;
    }
}
