package fpl.mals.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import fpl.mals.PlayerElement;

import java.util.ArrayList;
import java.util.List;

public class ParserUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

    public static List<PlayerElement> parsePlayerElements() throws Exception {
        String json = HttpUtils.loadJsonFromUrl(HttpUtils.FPL_API_URL);
        JsonNode root = MAPPER.readTree(json);
        JsonNode elements = root.get("elements");

        List<PlayerElement> list = new ArrayList<>();

        for (JsonNode el : elements) {
            list.add(MAPPER.treeToValue(el, PlayerElement.class));
        }

        return list;
    }

    private static double toDouble(JsonNode node) {
        if (node.isNumber()) {
            return node.doubleValue();
        }
        if (node.isTextual()) {
            String s = node.asText().trim();
            if (s.isEmpty()) return 0.0;
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException ex) {
                return 0.0;
            }
        }
        return 0.0;
    }

}
