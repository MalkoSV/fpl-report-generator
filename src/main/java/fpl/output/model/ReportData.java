package fpl.output.model;

import fpl.domain.model.PlayerSeasonView;
import fpl.domain.stats.PlayerGameweekSummary;
import fpl.domain.stats.SummaryData;
import fpl.domain.transfers.TransfersData;

import java.util.List;

public record ReportData(
        SummaryData summaryData,
        TransfersData transfersData,

        List<PlayerGameweekSummary> allPlayers,
        List<PlayerGameweekSummary> captains,
        List<PlayerGameweekSummary> starters,
        List<PlayerGameweekSummary> bench,
        List<PlayerGameweekSummary> doubtful,
        List<PlayerGameweekSummary> highPointsBench,

        List<PlayerSeasonView> topSeasonPlayers
) {}
