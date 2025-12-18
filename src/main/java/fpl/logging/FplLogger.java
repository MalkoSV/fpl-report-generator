package fpl.logging;

public final class FplLogger {

    private static final int TEAMS_PER_PAGE = 50;

    private FplLogger() {}

    public static void writeProcessingLog(int mode) {
        switch (mode) {
            case 201 -> System.out.println("ℹ️  Processing Mals League teams...");
            case 202 -> System.out.println("ℹ️  Processing Prognozilla league teams...");
            default -> System.out.printf("ℹ️  Processing Overall league teams (first %d teams from)...%n%n", mode * TEAMS_PER_PAGE);
        }

    }
}
