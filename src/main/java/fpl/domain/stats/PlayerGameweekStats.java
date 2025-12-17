package fpl.domain.stats;

import fpl.domain.model.HasPosition;
import fpl.domain.model.SquadPlayer;
import fpl.domain.model.PositionType;

public record PlayerGameweekStats(
        String name,
        PositionType position,
        int count,
        int starts,
        int captains,
        int tripleCaptains,
        int viceCaptains,
        int minPoints,
        int availability
) implements HasPosition {

    public static PlayerGameweekStats from(SquadPlayer p) {
        return new PlayerGameweekStats(
                p.name(),
                p.position(),
                1,
                p.inStartingXI() ? 1 : 0,
                p.captain() ? 1 : 0,
                p.tripleCaptain() ? 1 : 0,
                p.viceCaptain() ? 1 : 0,
                p.points(),
                p.chanceSafe()
        );
    }

    public PlayerGameweekStats merge(PlayerGameweekStats other) {
        return new PlayerGameweekStats(
                name,
                position,
                count + other.count,
                starts + other.starts,
                captains + other.captains,
                tripleCaptains + other.tripleCaptains,
                viceCaptains + other.viceCaptains,
                Math.min(minPoints, other.minPoints),
                availability
        );
    }

    @Override
    public PositionType getPosition() {
        return position;
    }
}
