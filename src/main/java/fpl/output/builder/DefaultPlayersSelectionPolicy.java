package fpl.output.builder;

import fpl.domain.filters.PlayerSeasonStatsFilter;
import fpl.domain.model.PlayerSeasonView;
import fpl.output.config.ReportDefaults;

import java.util.List;

public class DefaultPlayersSelectionPolicy implements PlayersSelectionPolicy {
    @Override
    public List<PlayerSeasonView> selectTop(List<PlayerSeasonView> players) {
        return PlayerSeasonStatsFilter.filterTopPlayers(
                players,
                ReportDefaults.TOP_PLAYER_MIN_POINTS,
                ReportDefaults.TOP_PLAYER_MIN_PPM,
                ReportDefaults.TOP_PLAYER_MIN_XGI
        );
    }

    @Override
    public List<PlayerSeasonView> selectGoalkeepers(List<PlayerSeasonView> players) {
        return PlayerSeasonStatsFilter.filterGoalkeepers(
                players,
                ReportDefaults.GOALKEEPER_MIN_POINTS
        );
    }
}
