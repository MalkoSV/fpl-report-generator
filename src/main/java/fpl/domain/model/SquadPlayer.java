package fpl.domain.model;

public record SquadPlayer(
        String name,
        PositionType position,
        boolean inStartingXI,
        boolean captain,
        boolean tripleCaptain,
        boolean viceCaptain,
        int points,
        int availability
) {
    public SquadPlayer(PlayerSeasonView playerSeasonView, Pick pick) {
        this(
                playerSeasonView.fullName(),
                playerSeasonView.position(),
                pick.multiplier() > 0,
                pick.multiplier() >= 2,
                pick.multiplier() == 3,
                pick.viceCaptain(),
                playerSeasonView.lastEventPoints(),
                playerSeasonView.availability()
        );
    }
}
