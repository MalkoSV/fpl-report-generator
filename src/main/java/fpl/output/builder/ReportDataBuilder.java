package fpl.output.builder;

import fpl.domain.filters.PlayerGameweekStatsFilter;
import fpl.domain.model.PlayerSeasonView;
import fpl.domain.model.Team;
import fpl.domain.stats.PlayerGameweekSummary;
import fpl.domain.stats.SummaryData;
import fpl.domain.stats.TeamStats;
import fpl.domain.stats.TeamSummary;
import fpl.domain.transfers.Transfer;
import fpl.domain.transfers.TransfersData;
import fpl.domain.transfers.TransfersDataBuilder;
import fpl.output.model.ReportData;

import java.util.List;

public class ReportDataBuilder {

    private final TransfersDataBuilder transfersBuilder;
    private final TopPlayersSelectionPolicy topPlayersSelectionPolicy;

    public ReportDataBuilder(
            TransfersDataBuilder transfersBuilder,
            TopPlayersSelectionPolicy topPlayersSelectionPolicy
    ) {
        this.transfersBuilder = transfersBuilder;
        this.topPlayersSelectionPolicy = topPlayersSelectionPolicy;
    }

    public ReportData build(
            List<Team> teams,
            List<PlayerSeasonView> seasonPlayers,
            List<Transfer> transfers
    ) {
        TeamSummary lastGameweekSummary = TeamStats.calculateSummary(teams);
        SummaryData summaryData = SummaryData.from(teams, lastGameweekSummary);
        TransfersData transfersData = transfersBuilder.build(transfers);

        List<PlayerGameweekSummary> gameweekPlayers = lastGameweekSummary.players();

        List<PlayerGameweekSummary> captains = PlayerGameweekStatsFilter.captained(gameweekPlayers);
        List<PlayerGameweekSummary> starters = PlayerGameweekStatsFilter.startersOnly(gameweekPlayers);
        List<PlayerGameweekSummary> bench = PlayerGameweekStatsFilter.benchOnly(gameweekPlayers);
        List<PlayerGameweekSummary> doubtful = PlayerGameweekStatsFilter.doubtful(gameweekPlayers);
        List<PlayerGameweekSummary> highPointsBench = PlayerGameweekStatsFilter.highPointsBench(gameweekPlayers);

        List<PlayerSeasonView> topSeasonPlayers = topPlayersSelectionPolicy.select(seasonPlayers);

        return new ReportData(
                summaryData,
                transfersData,
                gameweekPlayers,
                captains,
                starters,
                bench,
                doubtful,
                highPointsBench,
                topSeasonPlayers
        );
    }
}
