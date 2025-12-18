package fpl.api;

import java.net.URI;

public class FplApiEndPoints {
    private static final String FPL_API_BASE = "https://fantasy.premierleague.com/api";

    public static final String BOOTSTRAP = "/bootstrap-static/";
    public static final String LEAGUE = "/leagues-classic/%d/standings/?page_standings=%d";
    public static final String PICKS = "/entry/%d/event/%d/picks/";
    public static final String TRANSFERS = "/entry/%d/transfers/";

    public static URI getUri(String endPoint, Object... args) {
        return URI.create(FPL_API_BASE + endPoint.formatted(args));
    }
}
