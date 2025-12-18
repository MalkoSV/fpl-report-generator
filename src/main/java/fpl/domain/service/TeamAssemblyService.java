package fpl.domain.service;

import fpl.api.mapper.PickDtoMapper;
import fpl.domain.model.Pick;
import fpl.domain.model.SquadPlayer;
import fpl.domain.model.PlayerSeasonView;
import fpl.domain.model.PositionType;
import fpl.domain.model.Team;
import fpl.domain.repository.EntryRepository;
import fpl.domain.repository.PlayerRepository;
import fpl.logging.ProgressBar;
import fpl.utils.BoolUtils;
import fpl.utils.ThreadsUtils;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class TeamAssemblyService {

    private static final Logger logger = Logger.getLogger(TeamAssemblyService.class.getName());

    private TeamAssemblyService() {}

    public static List<Team> collectTeamStats(
            PlayerRepository players,
            EntryRepository entryRepository,
            int eventId,
            List<Integer> entryIds) {

        ProgressBar progressBar = new ProgressBar(entryIds.size());

        int threadCount = ThreadsUtils.getThreadsNumber();
        logger.info("🚀 Starting to fetch picks using " + threadCount + " threads...");

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        List<CompletableFuture<Team>> futures = new ArrayList<>();
        for (int entryId : entryIds) {
            CompletableFuture<Team> currentTeam = CompletableFuture.supplyAsync(() -> {
                try {
                    return processTeam(players, entryRepository, entryId, eventId, progressBar);
                } catch (Exception e) {
                    logger.warning("⚠️ Error for entry " + entryId + ": " + e.getMessage());
                    return null;
                }
            }, executor);
            futures.add(currentTeam);
        }

        List<Team> teams = futures.stream()
                .map(CompletableFuture::join)
                .toList();

        executor.shutdown();
        return teams;
    }

    private static Team processTeam(
            PlayerRepository playerRepository,
            EntryRepository entryRepository,
            int entryId,
            int eventId,
            ProgressBar progressBar) {

        Map<PositionType, List<SquadPlayer>> startSquad = new EnumMap<>(PositionType.class);
        for (PositionType type : PositionType.values()) {
            startSquad.put(type, new ArrayList<>());
        }

        List<SquadPlayer> bench = new ArrayList<>();

        var entryResponse = entryRepository.getEntry(entryId, eventId);
        List<Pick> picks = entryResponse.picks().stream()
                .map(PickDtoMapper::fromDto)
                .toList();

        for (var pick : picks) {
            PlayerSeasonView view = playerRepository.getById(pick.playerId());
            SquadPlayer player = new SquadPlayer(view, pick);
            PositionType pos = view.position();

            if (pick.multiplier() == 0) {
                bench.add(player);
            } else {
                startSquad.get(pos).add(player);
            }
        }

        progressBar.step();

        var history = entryResponse.entryHistory();
        String chip = entryResponse.activeChip();

        return new Team(
                entryId,
                history.points(),
                history.pointsOnBench(),
                history.value(),
                history.bank(),
                chipIsActive(chip, "3xc"),
                chipIsActive(chip, "wildcard"),
                chipIsActive(chip, "bboost"),
                chipIsActive(chip, "freehit"),
                history.eventTransfers(),
                history.eventTransfersCost(),
                startSquad.get(PositionType.GK),
                startSquad.get(PositionType.DEF),
                startSquad.get(PositionType.MID),
                startSquad.get(PositionType.FWD),
                bench
        );
    }

    private static int chipIsActive(String chip, String expected) {
        return BoolUtils.asInt(expected.equalsIgnoreCase(chip));
    }
}
