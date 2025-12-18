package fpl.domain.stats;

import fpl.domain.model.SquadPlayer;
import fpl.domain.model.Team;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TeamStatsService {

    public static TeamSummary calculateSummary(List<Team> teams) {
        return new TeamSummary(
                teams.size(),
                teams.stream().mapToInt(Team::points).min().orElse(0),
                teams.stream().mapToInt(Team::points).max().orElse(0),
                teams.stream().mapToDouble(Team::points).average().orElse(0.0),
                teams.stream().mapToDouble(Team::pointsOnBench).average().orElse(0.0),
                teams.stream().mapToDouble(Team::value).average().orElse(0.0),
                teams.stream().mapToDouble(Team::bank).average().orElse(0.0),
                teams.stream().mapToInt(Team::tripleCaptain).sum(),
                teams.stream().mapToInt(Team::wildCard).sum(),
                teams.stream().mapToInt(Team::benchBoost).sum(),
                teams.stream().mapToInt(Team::freeHit).sum(),
                PlayerGameweekStatsMerger.mergePlayers(getFullPlayerList(teams))
        );
    }

    public static List<SquadPlayer> getFullPlayerList(List<Team> teams) {
        return teams.stream()
                .flatMap(Team::streamPlayers)
                .toList();
    }

    public static Map<String, Long> groupFormations(List<Team> teams) {
        return groupAndSort(teams,
                t -> "%d-%d-%d".formatted(
                        t.defenders().size(),
                        t.midfielders().size(),
                        t.forwards().size()
                ));
    }

    public static Map<Long, Long> countZeroPointPlayers(List<Team> teams) {
        return groupAndSort(teams, Team::countStartPlayersWithZero);
    }

    public static Map<Integer, Long> countTransfers(List<Team> teams) {
        return groupAndSort(teams, Team::transfers);
    }

    public static Map<Integer, Long> sumTransfersCost(List<Team> teams) {
        return groupAndSort(teams, Team::transfersCost);
    }

    public static <T, K extends Comparable<K>> Map<K, Long> groupAndSort(
            List<T> list,
            Function<T, K> classifier
    ) {
        return list.stream()
                .map(classifier)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<K, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }
}
