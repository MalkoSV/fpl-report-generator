package fpl.output.builder;

import fpl.domain.filters.PlayerGameweekStatsFilter;
import fpl.domain.model.PlayerSeasonView;
import fpl.domain.model.Team;
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
            List<PlayerSeasonView> players,
            List<Transfer> transfers
    ) {
        TeamSummary summary = TeamStats.calculateSummary(teams);
        SummaryData summaryData = SummaryData.from(teams, summary);
        TransfersData transfersData = transfersBuilder.build(transfers);

        var gameweekPlayers = summary.players();

        return new ReportData(
                summaryData,
                transfersData,

                gameweekPlayers,
                PlayerGameweekStatsFilter.captained(gameweekPlayers),
                PlayerGameweekStatsFilter.startersOnly(gameweekPlayers),
                PlayerGameweekStatsFilter.benchOnly(gameweekPlayers),
                PlayerGameweekStatsFilter.doubtful(gameweekPlayers),
                PlayerGameweekStatsFilter.highPointsBench(gameweekPlayers),
                topPlayersSelectionPolicy.select(players));
    }
}
