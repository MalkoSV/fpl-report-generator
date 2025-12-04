package fpl.domain.summary;

import java.util.Map;

public record SummaryData(
        int teamCount,
        int playerCount,
        int tripleCaptainCount,
        int wildcardCount,
        int benchBoostCount,
        int freeHitCount,

        Map<Long, Long> zeroPointPlayers,
        Map<Integer, Long> transfersCount,
        Map<Integer, Long> transfersCost,
        Map<String, Long> formations
) {}
