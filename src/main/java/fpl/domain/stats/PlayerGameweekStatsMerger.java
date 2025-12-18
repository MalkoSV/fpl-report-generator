package fpl.domain.stats;

import fpl.domain.model.SquadPlayer;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PlayerGameweekStatsMerger {

    private PlayerGameweekStatsMerger() {}

    public static List<PlayerGameweekSummary> mergePlayers(List<SquadPlayer> players) {
        return players.stream()
                .map(PlayerGameweekSummary::from)
                .collect(Collectors.toMap(
                        PlayerGameweekSummary::name,
                        Function.identity(),
                        PlayerGameweekSummary::merge)
                )
                .values()
                .stream()
                .sorted(
                        Comparator.comparing(PlayerGameweekSummary::count).reversed()
                                .thenComparing(PlayerGameweekSummary::name)
                )
                .toList();
    }
}
