package fpl.utils;

import java.net.ConnectException;
import java.util.function.Supplier;

public class RetryUtils {

    private RetryUtils() {}

    public static <T> T retry(Supplier<T> action) {
        int attempts = 5;
        long pauseMs = 800;

        for (int i = 0; i < attempts; i++) {
            try {
                return action.get();

            } catch (RuntimeException re) {
                if (i == attempts - 1) {
                    throw re;
                }

                Throwable cause = re.getCause();

                if (cause instanceof ConnectException) {
                    try {
                        Thread.sleep(pauseMs + 1500);
                    } catch (InterruptedException ignored) {}

                    continue;
                }

                try {
                    Thread.sleep(pauseMs);
                } catch (InterruptedException ignored) {}
           }
        }

        throw new RuntimeException("Unreachable code");
    }
}
