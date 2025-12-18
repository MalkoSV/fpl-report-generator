package fpl.ui.console;

public final class ConsoleTexts {

    public static final String DESCRIPTION_FOR_ENTER_PAGE_NUMBER = AnsiColors.CYAN + """
            =======================================================
             ⚽ FPL PARSER
            =======================================================
            """ + AnsiColors.RESET + """                         
            Every standings page displays names of 50 teams.
            
            1 - 1-50 positions
            2 - 1-100 positions
             ...
            200 - 1-10 000 positions
            =======================================================
            201 - Mals League
            202 - Prognozilla
            """ + AnsiColors.CYAN + """
            =======================================================
            """ + AnsiColors.RESET + AnsiColors.BOLD + AnsiColors.YELLOW + """
            Enter the number of pages to parse (0 - exit):\s""" + AnsiColors.RESET;

    private ConsoleTexts() {}
}
