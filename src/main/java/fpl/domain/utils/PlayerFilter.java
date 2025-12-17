package fpl.domain.utils;

import fpl.domain.model.PlayerSeasonView;

import java.util.Comparator;
import java.util.List;

public class PlayerFilter {

    public static List<PlayerSeasonView> filter(List<PlayerSeasonView> players, int points, double ppm, double xgi) {
        return players.stream()
                .filter(
                        pe -> pe.totalPoints() > points
                                && pe.pointsPerGame() > ppm
                                && pe.xGI() > xgi
                )
                .sorted(Comparator.comparing(PlayerSeasonView::totalPoints)
                        .thenComparing(PlayerSeasonView::form)
                        .reversed()
                )
                .toList();
    }

}
