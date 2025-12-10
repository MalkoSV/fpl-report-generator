package fpl.utils;

public class RateLimiter {

    private final long minIntervalNs;
    private volatile long nextAllowedTime = 0;

    public RateLimiter(double permitsPerSecond) {
        this.minIntervalNs = (long)(1_000_000_000 / permitsPerSecond);
    }

    public synchronized void acquire() {
        long now = System.nanoTime();
        if (now < nextAllowedTime) {
            long sleepNs = nextAllowedTime - now;
            try {
                Thread.sleep(sleepNs / 1_000_000, (int)(sleepNs % 1_000_000));
            } catch (InterruptedException ignored) {}
        }
        nextAllowedTime = System.nanoTime() + minIntervalNs;
    }
}
