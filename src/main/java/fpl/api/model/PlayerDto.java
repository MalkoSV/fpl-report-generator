package fpl.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PlayerDto(
        String webName,
        double form,
        double pointsPerGame,
        int totalPoints,
        double valueForm,
        double valueSeason,
        int goalsScored,
        int assists,
        int bonus,
        int nowCost,
        double expectedGoals,
        double expectedAssists,
        double expectedGoalInvolvements,
        double selectedByPercent,
        int minutes,
        int starts,
        int defensiveContribution,

        @JsonProperty("defensive_contribution_per_90")
        double defensiveContributionPer90,

        int cleanSheets,
        int cornersAndIndirectFreekicksOrder,
        int directFreekicksOrder,
        int penaltiesOrder,
        int formRankType,
        String news
) {}
