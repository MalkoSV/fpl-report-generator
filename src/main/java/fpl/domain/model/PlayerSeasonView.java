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
        double xGC,

        int starts,
        int minutes,

        int defensiveContribution,
        double defensiveContributionPer90,
        int cleanSheets,
        int goalsConceded,
        int penaltiesSaved,
        int saves,

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

    public int goalsPlusAssists() {
        return goalsScored() + assists();
    }

    public double realityAndXgiDelta() {
        return goalsPlusAssists() - xGI();
    }

    public double eventPointsAndFormDelta() {
        return lastEventPoints() - form();
    }

    public double cost() {
        return nowCost()  / 10.0;
    }

    public double goalConcededAndXgcDelta() {
        return goalsConceded() - xGC();
    }

}
