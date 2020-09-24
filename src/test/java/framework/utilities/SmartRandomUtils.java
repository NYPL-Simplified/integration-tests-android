package framework.utilities;

import io.cucumber.java.ParameterType;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.Validate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class SmartRandomUtils {
    private SmartRandomUtils() {}

    public static int getRandomWithExclusion(int start, int end, int... exclusion) {
        Validate.isTrue(end - start > exclusion.length, "Count of exclusions can not be greater than range");
        if (exclusion.length == 0) {
            return RandomUtils.nextInt(start, end);
        } else {
            List<Integer> sortedExclusion = Arrays.stream(exclusion).sorted().boxed().collect(Collectors.toList());
            int random = RandomUtils.nextInt(start, end - start + 1 - sortedExclusion.size());
            for (int ex : sortedExclusion) {
                if (random < ex) {
                    break;
                }
                random++;
            }
            return random;
        }
    }
}
