package fpl.domain.summary;

import fpl.domain.model.Team;
import fpl.domain.model.TeamSummary;
import fpl.domain.utils.TeamUtils;

import java.util.List;

public class SummaryDataBuilder {

    public SummaryData build(List<Team> teams, TeamSummary summary) {

        return new SummaryData(
                summary.count(),
                summary.players().size(),
                summary.tripleCaptain(),
                summary.wildcard(),
                summary.benchBoost(),
                summary.freeHit(),

                TeamUtils.calculateStartPlayersWithZero(teams),
                TeamUtils.calculateTransfers(teams),
                TeamUtils.calculateTransfersCost(teams),
                TeamUtils.calculateFormationType(teams)
        );
    }
}
