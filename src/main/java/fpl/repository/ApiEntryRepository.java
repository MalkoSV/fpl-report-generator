package fpl.repository;

import fpl.api.FplApiClient;
import fpl.api.dto.EntryResponse;
import fpl.domain.repository.EntryRepository;

public class ApiEntryRepository implements EntryRepository {

    private final FplApiClient api;

    public ApiEntryRepository(FplApiClient api) {
        this.api = api;
    }

    @Override
    public EntryResponse getEntry(int entryId, int eventId) {
        return api.getEntry(entryId, eventId);
    }
}
