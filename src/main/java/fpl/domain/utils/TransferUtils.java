package fpl.domain.utils;

import fpl.domain.stats.TeamStatsService;
import fpl.domain.transfers.Transfer;

import java.util.List;
import java.util.Map;

public class TransferUtils {

    public static Map<String, Long> calculateTransfersIn(List<Transfer> transfers) {
        return TeamStatsService.groupAndSort(transfers, Transfer::playerIn);
    }

    public static Map<String, Long> calculateTransfersOut(List<Transfer> transfers) {
        return TeamStatsService.groupAndSort(transfers, Transfer::playerOut);
    }

}
