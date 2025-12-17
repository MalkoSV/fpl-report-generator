package fpl.domain.model;

public record PlayerSeasonView(
        int id,
        String fullName,
        PositionType position,

        int totalPoints,
        int totalBonus,
        int lastEventPoints,
        double pointsPerGame,
        double valueSeason,

        double form,
        int formRank,
        double valueForm,

        int goalsScored,
        int assists,
        double xG,
        double xA,
        double xGI,

        int starts,
        int minutes,

        int defensiveContribution,
        double defensiveContributionPer90,
        int cleanSheets,

        int penaltiesOrder,
        int directFreekicksOrder,
        int otherFreekicksOrder,

        int nowCost,
        double selectedByPercent,
        int availability,
        String lastNews
)  implements HasPosition {

    @Override
    public PositionType getPosition() {
        return position;
    }
}
