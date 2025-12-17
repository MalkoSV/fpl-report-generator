package fpl.domain.stats;

import fpl.domain.model.Team;

import java.util.List;
import java.util.Map;

public record SummaryData(
        int teamCount,
        int playerCount,
        double minPoints,
        double maxPoints,
        double avgPoints,
        double avgPointsOnBench,
        double avgValue,
        double avgBank,
        int tripleCaptainCount,
        int wildcardCount,
        int benchBoostCount,
        int freeHitCount,

        Map<Long, Long> zeroPointPlayers,
        Map<Integer, Long> transfersCount,
        Map<Integer, Long> transfersCost,
        Map<String, Long> formations
) {

    public static SummaryData from(List<Team> teams, TeamSummary summary) {
        return new SummaryData(
                summary.count(),
                summary.players().size(),
                summary.minPoints(),
                summary.maxPoints(),
                summary.avgPoints(),
                summary.avgPointsOnBench(),
                summary.avgValue(),
                summary.avgBank(),
                summary.tripleCaptain(),
                summary.wildcard(),
                summary.benchBoost(),
                summary.freeHit(),

                TeamStatsService.countZeroPointPlayers(teams),
                TeamStatsService.countTransfers(teams),
                TeamStatsService.sumTransfersCost(teams),
                TeamStatsService.groupFormations(teams)
        );
    }
}
