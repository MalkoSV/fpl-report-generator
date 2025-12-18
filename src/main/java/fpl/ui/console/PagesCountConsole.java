package fpl.ui.console;

import java.util.logging.Logger;

public class PagesCountConsole {

    private final Logger logger = Logger.getLogger(PagesCountConsole.class.getName());

    private static final String RESET = "\u001B[0m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN = "\u001B[36m";
    private static final String RED = "\u001B[31m";
    private static final String BOLD = "\u001B[1m";

    public static final String DESCRIPTION_FOR_ENTER_PAGE_NUMBER = CYAN + """
            =======================================================
             ⚽ FPL PARSER
            =======================================================
            """ + RESET + """                         
            Every standings page displays names of 50 teams.
            
            1 - 1-50 positions
            2 - 1-100 positions
             ...
            200 - 1-10 000 positions
            =======================================================
            201 - Mals League
            202 - Prognozilla
            """ + CYAN + """
            =======================================================
            """ + RESET + BOLD + YELLOW + """
            Enter the number of pages to parse (0 - exit):\s""" + RESET;

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
                DESCRIPTION_FOR_ENTER_PAGE_NUMBER,
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
