package fpl.excel.sheets;

import fpl.domain.stats.PlayerGameweekSummary;
import fpl.excel.builder.Col;
import fpl.excel.builder.TableSheetWriter;

import java.util.List;

public class HighPointsBenchSheetWriter
        extends TableSheetWriter<PlayerGameweekSummary> {

    private static final List<Col<PlayerGameweekSummary>> COLUMNS = List.of(
            new Col<>("Name", PlayerGameweekSummary::name),
            new Col<>("Count", PlayerGameweekSummary::count),
            new Col<>("Bench", p -> p.count() - p.starts()),
            new Col<>("Points", PlayerGameweekSummary::minPoints)
    );

    public HighPointsBenchSheetWriter(List<PlayerGameweekSummary> players) {
        super("Bench (>6 points)", players, COLUMNS);
    }
}
