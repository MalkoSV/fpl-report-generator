package fpl.excel.sheets;

import fpl.domain.stats.PlayerGameweekSummary;
import fpl.excel.builder.Col;
import fpl.excel.builder.TableSheetWriter;

import java.util.List;

public class CaptainPlayersSheetWriter extends TableSheetWriter<PlayerGameweekSummary> {

    private static final List<Col<PlayerGameweekSummary>> COLUMNS = List.of(
            new Col<>("Name", PlayerGameweekSummary::name),
            new Col<>("Captain", PlayerGameweekSummary::captains),
            new Col<>("Points", player -> player.minPoints() * 2)
    );

    public CaptainPlayersSheetWriter(List<PlayerGameweekSummary> players) {
        super("Captain", players, COLUMNS);
    }
}
