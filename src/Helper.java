import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Helper {

    public static <T extends Number> String toScientificNotation(T number) {
        return String.format("%.2e", number.doubleValue());
    }

    public static double getExecutionTime(Runnable function) {
        double startTime = System.nanoTime();
        function.run();
        return (System.nanoTime() - startTime) / 1000000;
    }

    public static int[] getRandomizedIntegersByLimit(int limit) {
        Random rnd = ThreadLocalRandom.current();

        int[] integers = getIntegersByLimit(limit);

        for (int i = integers.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = integers[index];
            integers[index] = integers[i];
            integers[i] = a;
        }

        return integers;
    }

    public static int[] getIntegersByLimit(int limit) {
        int[] integers = new int[limit];
        for (int i = 0; i < limit; i++) {
            integers[i] = i;
        }

        return integers;
    }

}
