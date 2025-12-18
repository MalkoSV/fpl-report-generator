package fpl.app.config;

public final class LeagueSelectionPolicy {

    public static final int MAX_PAGES = 200;

    public static final int MALS_MODE = 201;
    public static final int PROGNOZILLA_MODE = 202;

    public static final int OVERALL_LEAGUE_ID = 314;
    public static final int MALS_LEAGUE_ID = 1032011;
    public static final int PROGNOZILLA_LEAGUE_ID = 1031449;

    private LeagueSelectionPolicy() {}

    public static int resolvePages(int mode) {
        return mode <= MAX_PAGES ? mode : 1;
    }

    public static int resolveLeagueId(int mode) {
        return switch (mode) {
            case MALS_MODE -> MALS_LEAGUE_ID;
            case PROGNOZILLA_MODE -> PROGNOZILLA_LEAGUE_ID;
            default -> OVERALL_LEAGUE_ID;
        };
    }
}
