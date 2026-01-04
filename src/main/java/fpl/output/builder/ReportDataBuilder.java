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
import fpl.output.model.GameweekReportData;
import fpl.output.model.ReportData;

import java.util.List;

public class ReportDataBuilder {

    private final TransfersDataBuilder transfersBuilder;
    private final PlayersSelectionPolicy playersSelectionPolicy;

    public ReportDataBuilder(
            TransfersDataBuilder transfersBuilder,
            PlayersSelectionPolicy playersSelectionPolicy
    ) {
        this.transfersBuilder = transfersBuilder;
        this.playersSelectionPolicy = playersSelectionPolicy;
    }

    public ReportData build(
            List<Team> teams,
            List<PlayerSeasonView> seasonPlayers,
            List<Transfer> transfers
    ) {
        TeamSummary lastGameweekSummary = TeamStats.calculateSummary(teams);
        SummaryData summaryData = SummaryData.from(teams, lastGameweekSummary);
        TransfersData transfersData = transfersBuilder.build(transfers);
        List<PlayerSeasonView> topSeasonPlayers = playersSelectionPolicy.selectTop(seasonPlayers);
        List<PlayerSeasonView> seasonGoalkeepers = playersSelectionPolicy.selectGoalkeepers(seasonPlayers);

        var gameweekPlayers = lastGameweekSummary.players();

        GameweekReportData gameweek = new GameweekReportData(
                gameweekPlayers,
                PlayerGameweekStatsFilter.captained(gameweekPlayers),
                PlayerGameweekStatsFilter.startersOnly(gameweekPlayers),
                PlayerGameweekStatsFilter.benchOnly(gameweekPlayers),
                PlayerGameweekStatsFilter.doubtful(gameweekPlayers),
                PlayerGameweekStatsFilter.highPointsBench(gameweekPlayers)
        );

        return new ReportData(
                summaryData,
                transfersData,
                gameweek,
                topSeasonPlayers,
                seasonGoalkeepers
        );
    }
}
