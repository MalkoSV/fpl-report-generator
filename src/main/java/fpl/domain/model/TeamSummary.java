package fpl.domain.model;

import java.util.List;

public record TeamSummary(
        int count,
        int minPoints,
        int maxPoints,
        double avgPoints,
        double avgPointsOnBench,
        double avgValue,
        double avgBank,
        int tripleCaptain,
        int wildcard,
        int benchBoost,
        int freeHit,
        List<Player> players
) {
}
