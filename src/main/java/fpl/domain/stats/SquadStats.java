package fpl.domain.stats;

import fpl.domain.model.SquadPlayer;

import java.util.List;

public class SquadStats {

    private SquadStats() {}

    public static long countStartPlayersWithZero(List<SquadPlayer> players) {
        return players.stream()
                .filter(p -> p.inStartingXI() && p.points() <= 0)
                .count();
    }
}
