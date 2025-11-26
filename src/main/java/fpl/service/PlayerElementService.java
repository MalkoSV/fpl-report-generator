package fpl.service;

import fpl.api.model.PlayerApi;

import java.util.Comparator;
import java.util.List;

public class PlayerElementService {
    public static List<PlayerApi> filter(List<PlayerApi> players, int points, double ppm, double xgi) {
        return players.stream()
                .filter(
                        pe -> pe.totalPoints() > points
                                && pe.pointsPerGame() > ppm
                                && pe.expectedGoalInvolvements() > xgi
                )
                .sorted(Comparator.comparing(PlayerApi::totalPoints)
                        .thenComparing(PlayerApi::form)
                        .reversed()
                )
                .toList();
    }

}
