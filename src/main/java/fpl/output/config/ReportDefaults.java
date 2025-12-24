package fpl.output.config;

import java.io.File;

public final class ReportDefaults {

    public static final int TOP_PLAYER_MIN_POINTS = 25;
    public static final double TOP_PLAYER_MIN_PPM = 2.75;
    public static final double TOP_PLAYER_MIN_XGI = 0.1;

    public static final File DEFAULT_BASE_DIR = new File(
            System.getProperty("user.home"),
            "Documents/FPL-reports"
    );

    private ReportDefaults() {}
}
