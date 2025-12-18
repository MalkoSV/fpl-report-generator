package fpl.app.config;

public final class AppLimits {

    public static final int TEAMS_PER_PAGE = 50;
    public static final int MAX_THREADS = 16;

    public static int getThreadsNumber() {
        return Math.min(
                MAX_THREADS,
                Runtime.getRuntime().availableProcessors() * 2
        );
    }

}
