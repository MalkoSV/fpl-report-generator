package fpl.utils;

import java.net.ConnectException;
import java.util.Random;
import java.util.function.Supplier;

public class RetryUtils {

    private static final Random RANDOM = new Random();

    public static <T> T retry(Supplier<T> action) {
        int attempts = 6;
        long baseDelayMs = 500;
        long maxDelayMs = 5000;

        for (int i = 0; i < attempts; i++) {
            try {
                return action.get();

            } catch (RuntimeException re) {
                if (i == attempts - 1) {
                    throw re;
                }
                Throwable cause = re.getCause();

                boolean isConnectError = cause instanceof ConnectException;
                long delay = calculateDelay(i, baseDelayMs, maxDelayMs, isConnectError);

                sleep(delay);
            }
        }

        throw new RuntimeException("Unreachable");
    }


    private static long calculateDelay(
            int attempt,
            long baseDelay,
            long maxDelay,
            boolean connectError
    ) {
        long delay = (long) (baseDelay * Math.pow(2, attempt));

        if (connectError) {
            delay += 1500;
        }

        long jitter = (long) ((delay * 0.4) * (RANDOM.nextDouble() - 0.5));
        delay += jitter;

        return Math.min(delay, maxDelay);
    }


    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }
}
