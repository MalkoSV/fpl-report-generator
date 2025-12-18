package fpl.domain.filters;

import fpl.domain.transfers.Transfer;

import java.util.List;

public class TransfersFilter {

    public static List<Transfer> wildcards(List<Transfer> transfers) {
        return transfers.stream()
                .filter(Transfer::wildcard)
                .toList();
    }

    public static List<Transfer> freeHits(List<Transfer> transfers) {
        return transfers.stream()
                .filter(Transfer::freeHit)
                .toList();
    }

    public static List<Transfer> withoutFreeHits(List<Transfer> transfers) {
        return transfers.stream()
                .filter(tr -> !tr.freeHit())
                .toList();
    }

}
