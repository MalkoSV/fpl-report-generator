package fpl.domain.model;

import fpl.domain.stats.SquadStats;

import java.util.List;
import java.util.stream.Stream;

public record Team(
        int entryId,
        int points,
        int pointsOnBench,
        int value,
        int bank,
        int tripleCaptain,
        int wildCard,
        int benchBoost,
        int freeHit,
        int transfers,
        int transfersCost,
        List<SquadPlayer> goalkeeper,
        List<SquadPlayer> defenders,
        List<SquadPlayer> midfielders,
        List<SquadPlayer> forwards,
        List<SquadPlayer> bench) {

    public static long countStartPlayersWithZero(Team t) {
        return SquadStats.countStartPlayersWithZero(t.goalkeeper())
                + SquadStats.countStartPlayersWithZero(t.defenders())
                + SquadStats.countStartPlayersWithZero(t.midfielders())
                + SquadStats.countStartPlayersWithZero(t.forwards())
                + SquadStats.countStartPlayersWithZero(t.bench());
    }

    public Stream<SquadPlayer> streamPlayers() {
        return Stream.of(
                        goalkeeper,
                        defenders,
                        midfielders,
                        forwards,
                        bench
                )
                .flatMap(List::stream);
    }
}
