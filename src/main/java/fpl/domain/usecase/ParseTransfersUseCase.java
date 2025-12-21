package fpl.domain.usecase;

import fpl.api.mapper.TransferDtoMapper;
import fpl.app.config.AppLimits;
import fpl.domain.model.Team;
import fpl.domain.repository.PlayerRepository;
import fpl.domain.repository.TransferRepository;
import fpl.domain.transfers.Transfer;
import fpl.logging.ProgressBar;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ParseTransfersUseCase {

    private static final Logger logger = Logger.getLogger(ParseTransfersUseCase.class.getName());

    private final PlayerRepository playerRepository;
    private final TransferRepository transferRepository;

    public ParseTransfersUseCase(PlayerRepository playerRepository, TransferRepository transferRepository) {
        this.playerRepository = playerRepository;
        this.transferRepository = transferRepository;
    }

    public List<Transfer> execute(
            int eventId,
            List<Team> teams
    ) {
        int totalTeams = teams.size();
        ProgressBar progressBar = new ProgressBar(totalTeams);

        Map<Integer, Team> teamsByEntry = teams.stream()
                .collect(Collectors.toMap(Team::entryId, team -> team));

        int threadCount = AppLimits.getThreadsNumber();

        logger.info("🚀 Starting to fetch transfers (using %s threads)...".formatted(threadCount));
        long startTime = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        List<CompletableFuture<List<Transfer>>> tasks = teams.stream()
                .map(team -> CompletableFuture.supplyAsync(
                        () -> processTransfers(
                                team.entryId(),
                                playerRepository,
                                teamsByEntry,
                                transferRepository,
                                eventId,
                                progressBar
                        ),
                        executorService
                ))
                .toList();

        List<Transfer> transferList = tasks.stream()
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .toList();

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(1, TimeUnit.MINUTES)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

        logger.info("✅ Transfer fetching completed (in %d sec)".formatted((System.currentTimeMillis() - startTime) / 1000));

        return transferList;
    }

    private static List<Transfer> processTransfers(
            int teamId,
            PlayerRepository playerRepository,
            Map<Integer, Team> teamsByEntry,
            TransferRepository transferRepository,
            int eventId,
            ProgressBar progressBar
    ) {
        List<Transfer> transfers = transferRepository.getTransfers(teamId).stream()
                .filter(dto -> dto.event() == eventId)
                .map(dto -> TransferDtoMapper.fromDto(
                        dto,
                        playerRepository,
                        teamsByEntry
                ))
                .toList();

            progressBar.step();

            return transfers;
    }
}
