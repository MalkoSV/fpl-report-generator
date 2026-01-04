package fpl.output.builder;

import fpl.domain.model.PlayerSeasonView;

import java.util.List;

public interface PlayersSelectionPolicy {
    List<PlayerSeasonView> selectTop(List<PlayerSeasonView> players);
    List<PlayerSeasonView> selectGoalkeepers(List<PlayerSeasonView> players);
}
