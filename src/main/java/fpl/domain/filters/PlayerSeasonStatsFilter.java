package fpl.domain.filters;

import fpl.domain.model.PlayerSeasonView;

import java.util.Comparator;
import java.util.List;

public class PlayerSeasonStatsFilter {

    public static List<PlayerSeasonView> filterTopPlayers(
            List<PlayerSeasonView> players,
            int minPoints,
            double minPpm,
            double minXgi
    ) {
        return players.stream()
                .filter(p -> p.totalPoints() > minPoints)
                .filter(p -> p.pointsPerGame() > minPpm)
                .filter(p -> p.xGI() > minXgi)
                .sorted(
                        Comparator.comparing(PlayerSeasonView::totalPoints)
                                .thenComparing(PlayerSeasonView::form)
                                .reversed()
                )
                .toList();
    }

}
