package fpl.mals;

import fpl.mals.utils.OutputUtils;
import fpl.mals.utils.Utils;
import org.fusesource.jansi.AnsiConsole;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

public class FplScraper {
    private static final Logger logger = Logger.getLogger(FplScraper.class.getName());

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("PLAYWRIGHT_BROWSERS_PATH", "browsers");
        AnsiConsole.systemInstall();

        int standingsPageCount = Utils.getEnteredPageCount();
        Utils.terminateProgramIfNeeded(standingsPageCount);

        logger.info("ℹ️ Start parsing from " + standingsPageCount + " pages (" + standingsPageCount * 50 + " teams)");
        long startTime = System.currentTimeMillis();

        List<String> allTeamLinks = Utils.getAllTeamLinks(standingsPageCount);
        logger.info("✅ All team links received (in " + (System.currentTimeMillis() - startTime) / 1000 + " sec).");

        logger.info("🚀 Running in multi-threaded mode by Browser pool...");
        List<Team> teams = Utils.collectStats(allTeamLinks);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmm"));
        String fileName = "FPL_Teams_top%d(%ds_duration)_%s.xlsx".formatted(
                allTeamLinks.size(), (System.currentTimeMillis() - startTime) / 1000, timestamp);
        OutputUtils.exportResultsToExcel(teams, fileName, args);

        logger.info("⏱️ Completed in " + (System.currentTimeMillis() - startTime) / 1000 + "s");
        AnsiConsole.systemUninstall();
        Thread.sleep(3000);
    }
}
