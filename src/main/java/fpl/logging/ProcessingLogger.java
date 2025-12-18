package fpl.logging;

public final class ProcessingLogger {

    private ProcessingLogger() {}

    public static void logStart(String message) {
        System.out.println(message);
    }
}
