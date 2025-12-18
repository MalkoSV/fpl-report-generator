package fpl.app.config;

public final class LeagueSelectionPolicy {

    public static final int MALS_MODE = 201;
    public static final int PROGNOZILLA_MODE = 202;

    public static final int OVERALL_LEAGUE_ID = 314;
    public static final int MALS_LEAGUE_ID = 1032011;
    public static final int PROGNOZILLA_LEAGUE_ID = 1031449;

    private LeagueSelectionPolicy() {}

    public static LeagueSelection resolve(int userInput) {
        return switch (userInput) {
            case MALS_MODE -> new LeagueSelection(
                    MALS_LEAGUE_ID,
                    1,
                    "ℹ️ Processing Mals League teams..."

            );
            case PROGNOZILLA_MODE -> new LeagueSelection(
                    PROGNOZILLA_LEAGUE_ID,
                    1,
                    "ℹ️ Processing Prognozilla league teams..."
            );
            default -> new LeagueSelection(
                    OVERALL_LEAGUE_ID,
                    userInput,
                    "ℹ️ Processing Overall league teams (first %d teams)..."
                            .formatted(userInput * AppLimits.TEAMS_PER_PAGE)
            );
        };
    }
}
