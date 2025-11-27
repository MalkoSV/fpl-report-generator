package fpl.api.parser;

import fpl.api.FplApiEndPoints;
import fpl.api.model.PlayerDto;
import fpl.api.model.PlayersResponse;

import java.util.List;

public class PlayerParser {

    private PlayerParser() {}

    public static List<PlayerDto> parsePlayers() throws Exception {
        var uri = FplApiEndPoints.getUri(FplApiEndPoints.BOOTSTRAP);
        String json = JsonUtils.loadJsonFromUri(uri);

        PlayersResponse response = JsonUtils.MAPPER.readValue(json, PlayersResponse.class);

        return response.elements();
    }

}
