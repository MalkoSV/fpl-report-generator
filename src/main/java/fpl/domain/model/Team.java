package fpl.domain.model;

import fpl.domain.utils.PlayerUtils;

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
        return PlayerUtils.countStartPlayersWithZero(t.goalkeeper())
                + PlayerUtils.countStartPlayersWithZero(t.defenders())
                + PlayerUtils.countStartPlayersWithZero(t.midfielders())
                + PlayerUtils.countStartPlayersWithZero(t.forwards())
                + PlayerUtils.countStartPlayersWithZero(t.bench());
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
