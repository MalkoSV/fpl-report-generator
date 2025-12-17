package fpl.domain.model;

public record SquadPlayer(
        String name,
        PositionType position,
        boolean inStartingXI,
        boolean captain,
        boolean tripleCaptain,
        boolean viceCaptain,
        int points,
        int chanceSafe
) {
    public SquadPlayer(PlayerSeasonView view, Pick pick) {
        this(
                view.fullName(),
                view.position(),
                pick.multiplier() > 0,
                pick.multiplier() >= 2,
                pick.multiplier() == 3,
                pick.viceCaptain(),
                view.lastEventPoints(),
                view.availability()
        );
    }
}
