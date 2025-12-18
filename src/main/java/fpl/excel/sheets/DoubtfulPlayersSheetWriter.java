package fpl.excel.sheets;

import fpl.domain.stats.PlayerGameweekSummary;
import fpl.excel.builder.Col;
import fpl.excel.builder.TableSheetWriter;

import java.util.List;

public class DoubtfulPlayersSheetWriter extends TableSheetWriter<PlayerGameweekSummary> {

    private static final List<Col<PlayerGameweekSummary>> COLUMNS = List.of(
            new Col<>("Name", PlayerGameweekSummary::name),
            new Col<>("Count", PlayerGameweekSummary::count),
            new Col<>("Start", PlayerGameweekSummary::starts),
            new Col<>("AV", PlayerGameweekSummary::availability),
            new Col<>("Points", PlayerGameweekSummary::minPoints)
    );

    public DoubtfulPlayersSheetWriter(List<PlayerGameweekSummary> players) {
        super("Doubtful", players, COLUMNS);
    }
}
