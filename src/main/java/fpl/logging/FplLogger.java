package fpl.logging;

public class FplLogger {

    public FplLogger() {}

    public static void writeProcessingLog(int mode) {
        final int TEAMS_PER_PAGE = 50;
        switch (mode) {
            case 201 -> System.out.println("ℹ️  Processing Mals League teams...");
            case 202 -> System.out.println("ℹ️  Processing Prognozilla league teams...");
            default -> System.out.printf("ℹ️  Processing Overall league teams (first %d teams from)...%n%n", mode * TEAMS_PER_PAGE);
        }

    }
}
