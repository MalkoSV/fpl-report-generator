package fpl.app;

import fpl.api.FplApiClient;
import fpl.api.FplApiFactory;
import fpl.app.config.ExportConfiguration;
import fpl.app.config.LeagueSelection;
import fpl.app.config.LeagueSelectionPolicy;
import fpl.context.BootstrapContext;
import fpl.domain.model.PlayerSeasonView;
import fpl.domain.repository.EntryRepository;
import fpl.domain.repository.LeagueRepository;
import fpl.domain.repository.PlayerRepository;
import fpl.domain.repository.TransferRepository;
import fpl.domain.usecase.FetchTeamIdsUseCase;
import fpl.domain.usecase.AssembleTeamsUseCase;
import fpl.domain.transfers.Transfer;
import fpl.domain.usecase.ParseTransfersUseCase;
import fpl.domain.model.Team;
import fpl.excel.io.FileNameGenerator;
import fpl.logging.ProcessingLogger;
import fpl.output.OutputDirectoryResolver;
import fpl.output.ReportExportService;
import fpl.output.builder.ReportDataBuilder;
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
        try {
            var pagesCountConsole = new PagesCountConsole();
            int input = pagesCountConsole.askPagesCount();
            LeagueSelection selection = LeagueSelectionPolicy.resolve(input);

            ProcessingLogger.logStart(selection.description());

            logger.info("ℹ️ Starting to parse Bootstrap page...");
            long startTime = System.currentTimeMillis();

            FplApiClient api = FplApiFactory.createJackson();

            var bootstrapContext = new BootstrapContext(api);
            int eventId = bootstrapContext.events().lastId();
            PlayerRepository playerRepository = bootstrapContext.players();

            EntryRepository entryRepository = new ApiEntryRepository(api);
            LeagueRepository leagueRepository = new ApiLeagueRepository(api);
            TransferRepository transferRepository = new ApiTransferRepository(api);

            logger.info("ℹ️ Starting to fetch all team IDs...");

            FetchTeamIdsUseCase fetchTeamIdsUseCase = new FetchTeamIdsUseCase(leagueRepository);
            List<Integer> entryIds = fetchTeamIdsUseCase.execute(
                    selection.leagueId(),
                    selection.pages()
            );

            logger.info("✅ Successfully retrieved all team links (in " + (System.currentTimeMillis() - startTime) / 1000 + " sec).");

            logger.info("ℹ️ Collecting data from the pages...");
            var assembleTeamsUseCase = new AssembleTeamsUseCase(playerRepository, entryRepository);
            List<Team> teams = assembleTeamsUseCase.execute(
                    eventId,
                    entryIds
            );

            var parseTransfersUseCase = new ParseTransfersUseCase(playerRepository, transferRepository);
            List<Transfer> transfers = parseTransfersUseCase.execute(
                    eventId,
                    teams
            );

            logger.info("ℹ️ Start to export result...");

            List<PlayerSeasonView> playersData = playerRepository.all();

            var exportService = new ReportExportService(
                    new ReportDataBuilder(),
                    ExportConfiguration.excelWriter(),
                    new OutputDirectoryResolver(),
                    new FileNameGenerator());

            exportService.exportReport(
                    teams,
                    playersData,
                    transfers,
                    eventId,
                    args
            );
            ProcessingLogger.logEnd(startTime);
        } finally {
            AnsiConsole.systemUninstall();
        }
    }
}
