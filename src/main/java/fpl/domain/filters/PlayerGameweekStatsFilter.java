package fpl.domain.filters;

import fpl.domain.stats.PlayerGameweekSummary;

import java.util.List;

public class PlayerGameweekStatsFilter {

    private PlayerGameweekStatsFilter() {}

    public static List<PlayerGameweekSummary> startersOnly(List<PlayerGameweekSummary> players) {
        return players.stream()
                .filter(p -> p.starts() == p.count())
                .toList();
    }

    public static List<PlayerGameweekSummary> benchOnly(List<PlayerGameweekSummary> players) {
        return players.stream()
                .filter(p -> p.starts() == 0)
                .toList();
    }

    public static List<PlayerGameweekSummary> doubtful(List<PlayerGameweekSummary> players) {
        return players.stream()
                .filter(p -> p.availability() <= 50)
                .toList();
    }

    public static List<PlayerGameweekSummary> highPointsBench(List<PlayerGameweekSummary> players) {
        return players.stream()
                .filter(p -> p.count() > p.starts() && p.minPoints() > 6)
                .toList();
    }

    public static List<PlayerGameweekSummary> captained(List<PlayerGameweekSummary> players) {
        return players.stream()
                .filter(p -> p.captains() > 0)
                .toList();
    }
}
