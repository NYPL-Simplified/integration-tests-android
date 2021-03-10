package factories.steps;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.reflections.Reflections;
import org.reflections.ReflectionsException;
import stepdefinitions.BaseSteps;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class StepsFactory implements IStepsFactory {

    private static final String[] STEPS_LOCATION = new String[]{"stepdefinitions", "hooks"};

    private static volatile ThreadLocal<StepsFactory> stepsFactory = ThreadLocal.withInitial(StepsFactory::new);

    private StepsFactory() {
    }

    public static StepsFactory getStepsFactory() {
        return stepsFactory.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends BaseSteps> T getSteps(Class<T> clazz, Object... args) {
        Class<?> tClass = getPlatformClasses().stream()
                .filter(clazz::isAssignableFrom)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Implementation for Screen %s was not found in package %s",
                                clazz.getName(), String.join(", ", getPackageWithSteps()))));
        try {
            return (T) ConstructorUtils.invokeConstructor(tClass, args);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new IllegalArgumentException("Something went wrong during steps casting", e);
        }
    }

    private Set<Class<?>> getPlatformClasses() {
        Set<Class<?>> classes = new HashSet<>();
        Arrays.stream(getPackageWithSteps()).forEach(packageName -> {
            Reflections reflections = new Reflections(packageName);
            try {
                PlatformName platformName = AqualityServices.getApplicationProfile().getPlatformName();
                classes.addAll(reflections.getTypesAnnotatedWith(StepsType.class).stream()
                        .filter(clazz -> clazz.getAnnotation(StepsType.class).platform() == platformName)
                        .collect(Collectors.toSet()));
            } catch (ReflectionsException e) {
                throw new IllegalArgumentException(String.format("Could not find package \"%s\" with Steps. ",
                        String.join(", ", getPackageWithSteps())), e);
            }
        });
        return classes;
    }

    private String[] getPackageWithSteps() {
        return STEPS_LOCATION;
    }
}
