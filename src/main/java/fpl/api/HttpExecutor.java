package fpl.api;

import java.net.URI;
import java.util.List;

public interface HttpExecutor {
    <T> T get(URI uri, Class<T> type);
    <T> List<T> getList(URI uri, Class<T> elementType);
}
