package fpl.utils;

public class ThreadsUtils {

    private ThreadsUtils() {}

    public static int getThreadsNumber() {
        return Math.min(16, Runtime.getRuntime().availableProcessors() * 2);
    }
}
