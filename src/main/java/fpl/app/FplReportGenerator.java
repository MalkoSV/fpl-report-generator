package fpl.app;

import fpl.api.FplApiClient;
import fpl.api.FplApiFactory;
import fpl.app.config.LeagueSelectionPolicy;
import fpl.context.BootstrapContext;
import fpl.domain.model.PlayerSeasonView;
import fpl.domain.repository.EntryRepository;
import fpl.domain.repository.LeagueRepository;
import fpl.domain.repository.TransferRepository;
import fpl.domain.usecase.FetchTeamIdsUseCase;
import fpl.domain.usecase.AssembleTeamsUseCase;
import fpl.domain.transfers.Transfer;
import fpl.domain.usecase.ParseTransfersUseCase;
import fpl.logging.FplLogger;
import fpl.domain.model.Team;
import fpl.output.ReportExportService;
import fpl.repository.ApiEntryRepository;
import fpl.repository.ApiLeagueRepository;
import fpl.repository.ApiTransferRepository;
import fpl.ui.console.PagesCountConsole;
import org.fusesource.jansi.AnsiConsole;

import java.util.List;
import java.util.logging.Logger;

public class FplReportGenerator {
    private static final Logger logger = Logger.getLogger(FplReportGenerator.class.getName());

    public static void main(String[] args) throws Exception {
        AnsiConsole.systemInstall();

        var pagesCountConsole = new PagesCountConsole();
        int mode = pagesCountConsole.askPagesCount();

        int pages = LeagueSelectionPolicy.resolvePages(mode);
        int leagueId = LeagueSelectionPolicy.resolveLeagueId(mode);

        FplLogger.writeProcessingLog(pages);

        logger.info("ℹ️ Starting to parse pages!!");
        long startTime = System.currentTimeMillis();

        FplApiClient api = FplApiFactory.createJackson();
        var bootstrapContext = new BootstrapContext(api);
        int eventId = bootstrapContext.events().lastId();

        EntryRepository entryRepository = new ApiEntryRepository(api);
        LeagueRepository leagueRepository = new ApiLeagueRepository(api);
        TransferRepository transferRepository = new ApiTransferRepository(api);

        logger.info("ℹ️ Starting to fetch all team IDs...");

        FetchTeamIdsUseCase fetchTeamIdsUseCase = new FetchTeamIdsUseCase(leagueRepository);
        List<Integer> entryIds = fetchTeamIdsUseCase.execute(
                leagueId,
                pages
        );

        logger.info("✅ Successfully retrieved all team links (in " + (System.currentTimeMillis() - startTime) / 1000 + " sec).");

        logger.info("ℹ️ Collecting data from the pages...");
        List<Team> teams = AssembleTeamsUseCase.collectTeamStats(
                bootstrapContext.players(),
                entryRepository,
                eventId,
                entryIds
        );

        List<Transfer> transfers = ParseTransfersUseCase.collectTransfers(
                bootstrapContext.players(),
                transferRepository,
                eventId,
                teams
        );

        logger.info("ℹ️ Start to export result...");
        List<PlayerSeasonView> playersData = bootstrapContext.players()
                .all().stream()
                .toList();

        new ReportExportService().exportResults(
                teams,
                playersData,
                transfers,
                eventId,
                args
        );

        logger.info("⏱️ Completed in " + (System.currentTimeMillis() - startTime) / 1000 + "s");
        AnsiConsole.systemUninstall();
    }
}
