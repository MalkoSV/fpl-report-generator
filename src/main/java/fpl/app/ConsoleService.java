package fpl.app;

import fpl.utils.InputUtils;

import java.util.logging.Logger;

public class ConsoleService {

    private static final Logger logger = Logger.getLogger(ConsoleService.class.getName());

    public static void terminateProgramIfNeeded(int pageNumber) throws InterruptedException {
        if (pageNumber == 0) {
            logger.info("❌ Your choice - program terminated. Good luck!");
            Thread.sleep(1000);
            System.exit(0);
        }
    }

    public static int getEnteredPageCount() {
        int count = InputUtils.getEnteredNumber(InputUtils.DESCRIPTION_FOR_ENTER_PAGE_NUMBER, 0, 202);
        System.out.printf("✅ Your choice - %d%n", count);

        return count;
    }
}
