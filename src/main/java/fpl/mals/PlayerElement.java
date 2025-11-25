package fpl.mals;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PlayerElement(
        String webName,
        double form,
        double pointsPerGame,
        int totalPoints,
        double valueForm,
        double valueSeason,
        int goalsScored,
        int assists,
        int bonus,
        double expectedGoals,
        double expectedAssists,
        double expectedGoalInvolvements
) {}
