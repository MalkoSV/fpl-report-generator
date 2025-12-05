package fpl.app;

import fpl.api.dto.BootstrapResponse;
import fpl.api.dto.PlayerDto;
import fpl.parser.BootstrapParser;
import fpl.domain.service.TeamLinkService;
import fpl.domain.service.TeamParsingService;
import fpl.logging.FplLogger;
import fpl.domain.model.Team;
import fpl.output.ReportExportService;
import org.fusesource.jansi.AnsiConsole;

import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

public class FplReportGenerator {
    private static final Logger logger = Logger.getLogger(FplReportGenerator.class.getName());

    public static void main(String[] args) throws Exception {
        AnsiConsole.systemInstall();

        int totalStandingsPages = ConsoleService.getEnteredPageCount();
        ConsoleService.terminateProgramIfNeeded(totalStandingsPages);
        FplLogger.writeProcessingLog(totalStandingsPages);

        logger.info("ℹ️ Starting to parse pages!!");
        long startTime = System.currentTimeMillis();

        logger.info("ℹ️ Fetching all team links...");
        List<URI> allTeamLinks = TeamLinkService.collectTeamLinks(totalStandingsPages);
        logger.info("✅ Successfully retrieved all team links (in " + (System.currentTimeMillis() - startTime) / 1000 + " sec).");

        logger.info("ℹ️ Collecting data from the team pages...");
        List<Team> teams = TeamParsingService.collectStats(allTeamLinks);

        logger.info("ℹ️ Collecting players data from API...");
        BootstrapResponse bootstrapResponse = BootstrapParser.parseBootstrap();
        List<PlayerDto> playersData = BootstrapParser.getPlayers(bootstrapResponse);

        new ReportExportService().exportResults(teams, playersData, args);

        logger.info("⏱️ Completed in " + (System.currentTimeMillis() - startTime) / 1000 + "s");
        AnsiConsole.systemUninstall();
        Thread.sleep(1000);
    }
}
