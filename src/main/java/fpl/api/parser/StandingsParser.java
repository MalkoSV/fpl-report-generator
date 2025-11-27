package fpl.api.parser;

import fpl.api.FplApiEndPoints;
import fpl.api.model.LeagueResponse;
import fpl.api.model.TeamStats;

import java.util.ArrayList;
import java.util.List;

public class StandingsParser {

    private final int mode;

    private StandingsParser(int mode) {
        this.mode = mode;
    }

    public List<TeamStats> parseStandings() throws Exception {
        List<TeamStats> list = new ArrayList<>();
        int totalPages = getTotalPages();

        for (int page = 1; page <= totalPages; page++) {
            var uri = FplApiEndPoints.getUri(
                    FplApiEndPoints.LEAGUE,
                    FplApiEndPoints.getLeagueId(mode),
                    page
            );

            String json = JsonUtils.loadJsonFromUri(uri);
            LeagueResponse response = JsonUtils.MAPPER.readValue(json, LeagueResponse.class);

            response.standings().results().forEach(t ->
                    list.add(new TeamStats(
                            t.eventTotal(),
                            t.playerName(),
                            t.rank(),
                            t.total(),
                            t.entry(),
                            t.entryName()
                    ))
            );
        }

        return list;
    }

    private int getTotalPages() {
        return mode <= 20 ? mode : 1;
    }
}
