package fpl.logging;

public final class ProcessingLogger {

    private ProcessingLogger() {}

    public static void logStart(String message) {
        System.out.println(message);
    }

    public static void logEnd(long startTime) {
        System.out.printf("⏱️ Completed in %d sec%n", (System.currentTimeMillis() - startTime) / 1000);
    }
}
