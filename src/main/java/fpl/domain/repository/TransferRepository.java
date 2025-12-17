package fpl.domain.repository;

import fpl.api.dto.TransferDto;

import java.util.List;

public interface TransferRepository {
    List<TransferDto> getTransfers(int entryId);
}
