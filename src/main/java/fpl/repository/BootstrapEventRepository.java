package fpl.repository;

import fpl.api.dto.EventDto;
import fpl.domain.model.EventView;
import fpl.domain.repository.EventRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class BootstrapEventRepository implements EventRepository {

    private final List<EventView> events;

    public BootstrapEventRepository(List<EventDto> dtoEvents) {
        this.events = dtoEvents.stream()
                .map(dto -> new EventView(
                        dto.id(),
                        dto.isCurrent(),
                        dto.isPrevious()
                ))
                .toList();
    }

    @Override
    public List<EventView> all() {
        return events;
    }

    @Override
    public Optional<EventView> current() {
        return events.stream()
                .filter(EventView::isCurrent)
                .findFirst();
    }

    @Override
    public Optional<EventView> last() {
        return events.stream()
                .filter(e -> e.isCurrent() || e.isPrevious())
                .max(Comparator.comparingInt(EventView::id));
    }
}
