package fpl.api.parser;

import com.fasterxml.jackson.databind.JsonNode;
import fpl.api.FplApiEndPoints;
import fpl.api.model.PlayerApi;

import java.util.ArrayList;
import java.util.List;

public class PlayerParser {

    private PlayerParser() {}

    public static List<PlayerApi> parsePlayers() throws Exception {
        String json = JsonUtils.loadJsonFromUri(FplApiEndPoints.getUri(FplApiEndPoints.BOOTSTRAP));
        JsonNode root = JsonUtils.MAPPER.readTree(json);
        JsonNode elements = root.get("elements");

        List<PlayerApi> list = new ArrayList<>();

        for (JsonNode el : elements) {
            list.add(JsonUtils.MAPPER.treeToValue(el, PlayerApi.class));
        }

        return list;
    }

}
