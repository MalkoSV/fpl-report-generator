package fpl.api.parser;

import com.fasterxml.jackson.databind.JsonNode;
import fpl.api.FplApiEndPoints;
import fpl.api.model.TeamStats;

import java.util.ArrayList;
import java.util.List;

public class StandingsParser {

    private StandingsParser() {}

    public static List<TeamStats> parseStandings(int mode) throws Exception {
        int pages = mode <= 20 ? mode : 1;
        List<TeamStats> list = new ArrayList<>();

        for (int i = 1; i <= pages; i++) {
            String json = JsonUtils.loadJsonFromUri(FplApiEndPoints.getUri(
                    FplApiEndPoints.LEAGUE,
                    FplApiEndPoints.getLeagueId(mode),
                    i
            ));
            JsonNode root = JsonUtils.MAPPER.readTree(json);
            JsonNode elements = root.get("elements");

            for (JsonNode el : elements) {
                list.add(JsonUtils.MAPPER.treeToValue(el, TeamStats.class));
            }
        }
        return list;
    }


}
