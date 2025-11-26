package fpl.api;

import java.net.URI;

public class FplApiEndPoints {
    private static final String FPL_API_BASE = "https://fantasy.premierleague.com/api";

    public static final String BOOTSTRAP = "/bootstrap-static/";
    public static final String LEAGUE = "/leagues-classic/%d/standings/?page_standings=%d";
    public static final String TEAM = "/entry/%d/event/%d/picks/";

    public static final int OVERALL_LEAGUE_ID = 314;
    public static final int MALS_LEAGUE_ID = 1032011;
    public static final int PROGNOZILLA_LEAGUE_ID = 1031449;

    public static URI getUri(String endPoint, Object... args) {
        return URI.create(FPL_API_BASE + endPoint.formatted(args));
    }

    public static int getLeagueId(int mode) {
        return switch (mode) {
            case 21 -> MALS_LEAGUE_ID;
            case 22 -> PROGNOZILLA_LEAGUE_ID;
            default -> OVERALL_LEAGUE_ID;
        };
    }
}
