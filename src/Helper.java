public class Helper {

    public static <T extends Number> String toScientificNotation(T number) {
        return String.format("%.2e", number.doubleValue());
    }

    public static long getExecutionTime(Runnable function) {
        long startTime = System.currentTimeMillis();
        function.run();
        return System.currentTimeMillis() - startTime;
    }

}
