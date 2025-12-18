package fpl.ui.console;

import java.util.Scanner;

final class ConsoleInput {

    private static final Scanner scanner = new Scanner(System.in);

    private ConsoleInput() {}

    static int readNumber(String description, int min, int max) {
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
