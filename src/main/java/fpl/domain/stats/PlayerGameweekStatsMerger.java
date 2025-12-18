package fpl.domain.stats;

import fpl.domain.model.SquadPlayer;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PlayerGameweekStatsMerger {

    private PlayerGameweekStatsMerger() {}

    public static List<PlayerGameweekStats> mergePlayers(List<SquadPlayer> players) {
        return players.stream()
                .map(PlayerGameweekStats::from)
                .collect(Collectors.toMap(
                        PlayerGameweekStats::name,
                        Function.identity(),
                        PlayerGameweekStats::merge)
                )
                .values()
                .stream()
                .sorted(
                        Comparator.comparing(PlayerGameweekStats::count).reversed()
                                .thenComparing(PlayerGameweekStats::name)
                )
                .toList();
    }
}
