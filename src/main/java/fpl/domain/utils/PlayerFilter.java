package fpl.domain.utils;

import fpl.api.dto.PlayerDto;

import java.util.Comparator;
import java.util.List;

public class PlayerFilter {

    public static List<PlayerDto> filter(List<PlayerDto> players, int points, double ppm, double xgi) {
        return players.stream()
                .filter(
                        pe -> pe.totalPoints() > points
                                && pe.pointsPerGame() > ppm
                                && pe.expectedGoalInvolvements() > xgi
                )
                .sorted(Comparator.comparing(PlayerDto::totalPoints)
                        .thenComparing(PlayerDto::form)
                        .reversed()
                )
                .toList();
    }

}
