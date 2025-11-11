package fpl.mals;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class FplScraper {
    private static final Logger logger = Logger.getLogger(FplScraper.class.getName());
    private static final String ABSENT_PLAYER = null;

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("PLAYWRIGHT_BROWSERS_PATH", "browsers");

        try (Playwright playwright = Playwright.create()) {
            int standingsPageCount = Utils.getEnteredPageCount();

            if (standingsPageCount == 0) {
                logger.info("Program terminated. Good luck!");
                Thread.sleep(3000);
                System.exit(0);
            }

            logger.info("✅ Start parsing from " + standingsPageCount + " pages (" + standingsPageCount * 50 + " teams)");
            long startTime = System.currentTimeMillis();

            try (Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true))) {
                Page page = browser.newPage();

                List<String> allTeamLinks = IntStream.rangeClosed(1, standingsPageCount)
                        .mapToObj(Utils::getStandingsPageUrl)
                        .map(url -> Utils.getTeamLinks(page, url))
                        .flatMap(Collection::stream)
                        .toList();

                Map<String, Integer> players;
                int threadMode = Utils.getThreadMode();

                players = switch (threadMode) {
                    case 1 -> {
                        System.out.println("🐢 Running in single-threaded mode...");
                        yield Utils.collectPlayers(page, allTeamLinks);
                    }
                    case 2 -> {
                        System.out.println("🚀 Running in multi-threaded mode by Browser pool...");
                        yield Utils.collectPlayersConcurrentlyByBrowserPool(allTeamLinks, ABSENT_PLAYER);
                    }
                    default -> throw new IllegalArgumentException("Unknown thread mode: " + threadMode);
                };

                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmm"));
                String fileName = String.format("FPL_Teams_top%d(%d_players-%ds_duration)_%s.xlsx",
                        allTeamLinks.size(), players.size(), (System.currentTimeMillis() - startTime) / 1000, timestamp);

                File outputDir = Utils.getOutputDir(args);
                File resultFile = new File(outputDir, fileName);
                Utils.saveResultsToExcel(players, resultFile);
                logger.info("💾 Excel file saved successfully: " + outputDir.getAbsolutePath() + "\\" + fileName);
                logger.info("⏱️ Completed in " + (System.currentTimeMillis() - startTime) / 1000 + "s");
                Thread.sleep(5000);
            }
        }
    }
}
