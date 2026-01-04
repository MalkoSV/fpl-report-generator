package fpl.output.model;

import fpl.domain.model.PlayerSeasonView;
import fpl.domain.stats.SummaryData;
import fpl.domain.transfers.TransfersData;

import java.util.List;

public record ReportData(
        SummaryData summaryData,
        TransfersData transfersData,
        GameweekReportData gameweek,
        List<PlayerSeasonView> topSeasonPlayers,
        List<PlayerSeasonView> seasonGoalkeepers
) {}
