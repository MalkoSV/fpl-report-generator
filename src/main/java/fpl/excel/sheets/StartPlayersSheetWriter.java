package fpl.excel.sheets;

import fpl.domain.model.Player;
import fpl.excel.builder.Col;
import fpl.excel.builder.TableSheetWriter;

import java.util.List;

public class StartPlayersSheetWriter
        extends TableSheetWriter<Player> {

    private static final List<Col<Player>> COLUMNS = List.of(
            new Col<>("Name", Player::getName),
            new Col<>("Count", Player::getCount),
            new Col<>("Start", Player::getStart),
            new Col<>("Points", Player::getPoints)
    );

    public StartPlayersSheetWriter(List<Player> players) {
        super("Only start", players, COLUMNS);
    }
}
