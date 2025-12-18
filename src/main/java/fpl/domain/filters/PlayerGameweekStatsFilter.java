package fpl.domain.filters;

import fpl.domain.stats.PlayerGameweekStats;

import java.util.List;

public class PlayerGameweekStatsFilter {

    private PlayerGameweekStatsFilter() {}

    public static List<PlayerGameweekStats> startersOnly(List<PlayerGameweekStats> players) {
        return players.stream()
                .filter(p -> p.starts() == p.count())
                .toList();
    }

    public static List<PlayerGameweekStats> benchOnly(List<PlayerGameweekStats> players) {
        return players.stream()
                .filter(p -> p.starts() == 0)
                .toList();
    }

    public static List<PlayerGameweekStats> doubtful(List<PlayerGameweekStats> players) {
        return players.stream()
                .filter(p -> p.availability() <= 50)
                .toList();
    }

    public static List<PlayerGameweekStats> highPointsBench(List<PlayerGameweekStats> players) {
        return players.stream()
                .filter(p -> p.count() > p.starts() && p.minPoints() > 6)
                .toList();
    }

    public static List<PlayerGameweekStats> captained(List<PlayerGameweekStats> players) {
        return players.stream()
                .filter(p -> p.captains() > 0)
                .toList();
    }
}
