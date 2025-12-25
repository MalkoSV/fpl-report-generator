package fpl.output.model;

import fpl.domain.stats.PlayerGameweekSummary;

import java.util.List;

public record GameweekReportData(
        List<PlayerGameweekSummary> all,
        List<PlayerGameweekSummary> captains,
        List<PlayerGameweekSummary> starters,
        List<PlayerGameweekSummary> bench,
        List<PlayerGameweekSummary> doubtful,
        List<PlayerGameweekSummary> highPointsBench
) {}
