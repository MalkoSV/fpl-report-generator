package fpl.excel.sheets;

import fpl.domain.model.Player;
import fpl.excel.builder.Col;
import fpl.excel.builder.TableSheetWriter;

import java.util.List;

public class HighPointsBenchSheetWriter
        extends TableSheetWriter<Player> {

    private static final List<Col<Player>> COLUMNS = List.of(
            new Col<>("Name", Player::getName),
            new Col<>("Count", Player::getCount),
            new Col<>("Bench", p -> p.getCount() - p.getStart()),
            new Col<>("Points", Player::getPoints)
    );

    public HighPointsBenchSheetWriter(List<Player> players) {
        super("Bench (>5 points)", players, COLUMNS);
    }
}
