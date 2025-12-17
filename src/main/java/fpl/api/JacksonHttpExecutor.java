package fpl.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import fpl.utils.RetryUtils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

public class JacksonHttpExecutor implements HttpExecutor {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

    private final HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    @Override
    public <T> T get(URI uri, Class<T> type) {
        return RetryUtils.retry(() -> fetch(uri, type));
    }

    @Override
    public <T> List<T> getList(URI uri, Class<T> elementType) {
        return RetryUtils.retry(() -> fetchList(uri, elementType));
    }

    private <T> T fetch(URI uri, Class<T> type) {
        try {
            String json = sendRequest(uri);
            return MAPPER.readValue(json, type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T> List<T> fetchList(URI uri, Class<T> elementType) {
        try {
            String json = sendRequest(uri);
            var collectionType = MAPPER.getTypeFactory()
                    .constructCollectionType(java.util.List.class, elementType);
            return MAPPER.readValue(json, collectionType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String sendRequest(URI uri) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        return client
                .send(request, HttpResponse.BodyHandlers.ofString())
                .body();
    }
}
