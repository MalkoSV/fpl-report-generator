package fpl.domain.service;

import fpl.api.FplApiEndPoints;
import fpl.api.dto.BootstrapResponse;
import fpl.api.dto.TeamStats;
import fpl.parser.BootstrapParser;
import fpl.parser.StandingsParser;

import java.net.URI;
import java.util.List;

public final class TeamLinkService {

    private TeamLinkService() {}

    public static List<URI> collectTeamLinks(int totalPages) throws Exception {
        List<TeamStats> teams = StandingsParser.of(totalPages).parseStandings();
        BootstrapResponse bootstrapResponse = BootstrapParser.parseBootstrap();
        int event = BootstrapParser.getLastEvent(bootstrapResponse);

        return teams.stream()
                .map(team -> FplApiEndPoints.getUri(
                        FplApiEndPoints.TEAM,
                        team.entry(),
                        event
                ))
                .toList();
    }
}
