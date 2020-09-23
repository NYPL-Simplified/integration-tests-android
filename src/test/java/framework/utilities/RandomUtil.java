package framework.utilities;

import java.util.Random;

public class RandomUtil {

    private static final Random random = new Random();

    private RandomUtil() {
    }

    public static int getRandomInt(int number) {
        return random.nextInt(number);
    }

    public static int getRandomInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
}
