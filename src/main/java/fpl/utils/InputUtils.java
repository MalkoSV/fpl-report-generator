package fpl.utils;

import java.util.Scanner;

public class InputUtils {
    private static final Scanner scanner = new Scanner(System.in);

    private static final String RESET = "\u001B[0m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String BOLD = "\u001B[1m";

    public static final String DESCRIPTION_FOR_ENTER_PAGE_NUMBER = CYAN + """
            =======================================================
             ⚽ FPL SCRAPER
            =======================================================
            """ + RESET + """                         
            Every standings page displays names of 50 teams.
            
            1 - 1-50 positions
            2 - 1-100 positions
            3 - 1-150 positions
             ...
            20 - 1-1000 positions
            21 - Mals League
            22 - Prognozilla
            """ + CYAN + """
            =======================================================
            """ + RESET + BOLD + YELLOW + """
            Enter the number of pages to parse (0 - exit):\s""" + RESET;

    public static int getEnteredNumber(String description, int min, int max) {
        int result;
        while (true) {
            System.out.print(description);

            if (scanner.hasNextInt()) {
                result = scanner.nextInt();
                scanner.nextLine();
                if (result >= min && result <= max) {
                    System.out.println();
                    break;
                } else {
                    System.out.printf("⚠️ Error: the number must be between %d and %d%n", min, max);
                }
            } else {
                System.out.println("⚠️ Error: a number is required!");
                scanner.nextLine();
            }
            System.out.println();
        }
        return result;
    }

}
