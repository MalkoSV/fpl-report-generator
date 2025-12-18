package fpl.ui.console;

public final class ConsoleTexts {

    private static final String RESET = "\u001B[0m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN = "\u001B[36m";
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

    private ConsoleTexts() {}
}
