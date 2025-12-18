package fpl.ui.console;

import java.util.logging.Logger;

public class PagesCountConsole {

    private final Logger logger = Logger.getLogger(PagesCountConsole.class.getName());

    public int askPagesCount() throws InterruptedException {
        int count = readPagesCount();

        if (count == 0) {
            terminate();
        }

        System.out.printf("✅ Your choice - %d%n", count);
        return count;
    }

    private int readPagesCount() {
        return ConsoleInput.readNumber(
                ConsoleTexts.DESCRIPTION_FOR_ENTER_PAGE_NUMBER,
                0,
                202
        );
    }

    private void terminate() throws InterruptedException {
        logger.info("❌ Your choice - program terminated. Good luck!");
        Thread.sleep(1000);
        System.exit(0);
    }
}
