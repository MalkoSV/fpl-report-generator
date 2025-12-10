package fpl.utils;

public class ThreadsUtils {

    private ThreadsUtils() {}

    public static int getThreadsNumber(int totalUris) {
        int minThreads = 3;
        int cores = Runtime.getRuntime().availableProcessors();

        return totalUris > 6000 ? minThreads : cores;
    }
}
