package fpl.domain.utils;

import fpl.domain.model.SquadPlayer;
import fpl.domain.stats.PlayerGameweekStats;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PlayerUtils {
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

    public static List<PlayerGameweekStats> getOnlyStartPlayers(List<PlayerGameweekStats> players) {
        return players.stream()
                .filter(p -> p.starts() == p.count())
                .toList();
    }

    public static List<PlayerGameweekStats> getOnlyBenchPlayers(List<PlayerGameweekStats> players) {
        return players.stream()
                .filter(p -> p.starts() == 0)
                .toList();
    }

    public static List<PlayerGameweekStats> getDoubtfulPlayers(List<PlayerGameweekStats> players) {
        return players.stream()
                .filter(p -> p.availability() <= 50)
                .toList();
    }

    public static List<PlayerGameweekStats> getBenchPlayersWithHighPoints(List<PlayerGameweekStats> players) {
        return players.stream()
                .filter(p -> p.count() - p.starts() > 0 && p.minPoints() > 5)
                .toList();
    }

    public static List<PlayerGameweekStats> getPlayersWhoCaptain(List<PlayerGameweekStats> players) {
        return players.stream()
                .filter(p -> p.captains() > 0)
                .toList();
    }

    public static long countStartPlayersWithZero(List<SquadPlayer> players) {
        return players.stream()
                .filter(p -> p.inStartingXI() && p.points() <= 0)
                .count();
    }

}
