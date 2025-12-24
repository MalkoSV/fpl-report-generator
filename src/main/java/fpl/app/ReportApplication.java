package fpl.app;

import fpl.api.FplApiClient;
import fpl.api.FplApiFactory;
import fpl.app.config.LeagueSelection;
import fpl.app.config.LeagueSelectionPolicy;
import fpl.context.BootstrapContext;
import fpl.domain.model.PlayerSeasonView;
import fpl.domain.model.Team;
import fpl.domain.transfers.Transfer;
import fpl.domain.usecase.AssembleTeamsUseCase;
import fpl.domain.usecase.FetchTeamIdsUseCase;
import fpl.domain.usecase.ParseTransfersUseCase;
import fpl.logging.ProcessingLogger;
import fpl.output.OutputContext;
import fpl.output.OutputDirectoryResolver;
import fpl.output.ReportExportFactory;
import fpl.output.config.ReportDefaults;
import fpl.repository.RepositoryFactory;
import fpl.ui.console.OutputArgumentParser;
import fpl.ui.console.PagesCountConsole;
import org.fusesource.jansi.AnsiConsole;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

public class ReportApplication {
    private static final Logger logger = Logger.getLogger(ReportApplication.class.getName());

    public void run(String[] args) throws Exception{
        AnsiConsole.systemInstall();
        try {
            LeagueSelection selection = resolveSelection();
            ProcessingLogger.logStart(selection.description());

            logger.info("ℹ️ Starting to parse Bootstrap page...");
            long startTime = System.currentTimeMillis();

            FplApiClient api = FplApiFactory.createJackson();
            var bootstrapContext = new BootstrapContext(api);

            int eventId = bootstrapContext.events().lastId();
            var repositories = RepositoryFactory.create(api, bootstrapContext);

            logger.info("ℹ️ Starting to fetch all team IDs...");

            FetchTeamIdsUseCase fetchTeamIdsUseCase = new FetchTeamIdsUseCase(repositories.league());
            List<Integer> entryIds = fetchTeamIdsUseCase.execute(
                    selection.leagueId(),
                    selection.pages()
            );
            logger.info("✅ Successfully retrieved all team links (in " + (System.currentTimeMillis() - startTime) / 1000 + " sec).");

            logger.info("ℹ️ Collecting data from the pages...");
            var assembleTeamsUseCase = new AssembleTeamsUseCase(repositories.players(), repositories.entry());
            List<Team> teams = assembleTeamsUseCase.execute(
                    eventId,
                    entryIds
            );

            var parseTransfersUseCase = new ParseTransfersUseCase(repositories.players(), repositories.transfers());
            List<Transfer> transfers = parseTransfersUseCase.execute(
                    eventId,
                    teams
            );

            logger.info("ℹ️ Start to export result...");
            List<PlayerSeasonView> playersData = repositories.players().all();

            File outputDir = resolveOutputDir(args);
            var outputContext = new OutputContext(outputDir);

            var exportService = ReportExportFactory.createExcel();
            exportService.exportReport(
                    teams,
                    playersData,
                    transfers,
                    eventId,
                    outputContext
            );
            ProcessingLogger.logEnd(startTime);
        } finally {
            AnsiConsole.systemUninstall();
        }
    }

    private LeagueSelection resolveSelection() throws InterruptedException {
        int input = new PagesCountConsole().askPagesCount();

        return LeagueSelectionPolicy.resolve(input);
    }

    private File resolveOutputDir(String[] args) {
        File baseDir = new OutputArgumentParser()
                .parse(args)
                .orElse(ReportDefaults.DEFAULT_BASE_DIR);
        return new OutputDirectoryResolver().resolve(baseDir);
    }
}
