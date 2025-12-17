package fpl.api;

import fpl.api.dto.BootstrapResponse;
import fpl.api.dto.EntryResponse;
import fpl.api.dto.LeagueResponse;
import fpl.api.dto.TransferDto;

import java.util.List;

public class FplApiClient {

    private final HttpExecutor http;

    public FplApiClient(HttpExecutor http) {
        this.http = http;
    }

    public BootstrapResponse getBootstrap() {
        var uri = FplApiEndPoints.getUri(FplApiEndPoints.BOOTSTRAP);

        return http.get(uri, BootstrapResponse.class);
    }

    public EntryResponse getEntry(int entryId, int eventId) {
        var uri = FplApiEndPoints.getUri(FplApiEndPoints.PICKS, entryId, eventId);

        return http.get(uri, EntryResponse.class);
    }

    public LeagueResponse getLeaguePage(int leagueId, int page) {
        var uri = FplApiEndPoints.getUri(FplApiEndPoints.LEAGUE, leagueId, page);

        return http.get(uri, LeagueResponse.class);
    }

    public List<TransferDto> getTransfers(int entryId) {
        var uri = FplApiEndPoints.getUri(FplApiEndPoints.TRANSFERS, entryId);

        return http.getList(uri, TransferDto.class);
    }
}
