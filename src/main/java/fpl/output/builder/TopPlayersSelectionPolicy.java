package fpl.output.builder;

import fpl.domain.model.PlayerSeasonView;

import java.util.List;

public interface TopPlayersSelectionPolicy {
    List<PlayerSeasonView> select(List<PlayerSeasonView> players);
}
