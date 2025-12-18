package fpl.domain.transfers;

import fpl.domain.stats.TransferStats;
import fpl.domain.filters.TransfersFilter;

import java.util.List;

public class TransfersDataBuilder {

    public TransfersData build(List<Transfer> transfers) {
        List<Transfer> withoutFreeHit = TransfersFilter.withoutFreeHits(transfers);
        List<Transfer> wildcard = TransfersFilter.wildcards(transfers);
        List<Transfer> freeHit = TransfersFilter.freeHits(transfers);

        return new TransfersData(
                TransferStats.calculateTransfersIn(withoutFreeHit),
                TransferStats.calculateTransfersOut(withoutFreeHit),
                TransferStats.calculateTransfersIn(wildcard),
                TransferStats.calculateTransfersOut(wildcard),
                TransferStats.calculateTransfersIn(freeHit),
                TransferStats.calculateTransfersOut(freeHit)
        );
    }
}
