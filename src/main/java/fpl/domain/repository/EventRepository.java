package fpl.domain.repository;

import fpl.domain.model.EventView;

import java.util.List;
import java.util.Optional;

public interface EventRepository {

    List<EventView> all();

    Optional<EventView> current();

    Optional<EventView> last();

    default int lastId() {
        return last()
                .orElseThrow(() -> new IllegalStateException("No active event"))
                .id();
    }
}
