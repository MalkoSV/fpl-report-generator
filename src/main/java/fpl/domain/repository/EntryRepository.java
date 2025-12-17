package fpl.domain.repository;

import fpl.api.dto.EntryResponse;

public interface EntryRepository {
    EntryResponse getEntry(int entryId, int eventId);
}
