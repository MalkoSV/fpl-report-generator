package fpl.repository;

import fpl.api.FplApiClient;
import fpl.api.dto.LeagueResponse;
import fpl.api.dto.TransferDto;
import fpl.domain.repository.LeagueRepository;
import fpl.domain.repository.TransferRepository;

import java.util.List;

public class ApiTransferRepository implements TransferRepository {

    private final FplApiClient api;

    public ApiTransferRepository(FplApiClient api) {
        this.api = api;
    }

    @Override
    public List<TransferDto> getTransfers(int entryId) {
        return api.getTransfers(entryId);
    }
}
