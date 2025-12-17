package fpl.api.mapper;

import fpl.api.dto.PlayerDto;
import fpl.domain.model.PositionType;
import fpl.domain.model.PlayerSeasonView;

public class PlayerDtoMapper {

    private PlayerDtoMapper() {}

    public static PlayerSeasonView fromDto(PlayerDto dto) {

        return new PlayerSeasonView(
                dto.id(),
                dto.fullName(),
                PositionType.fromCode(dto.elementType()),

                dto.totalPoints(),
                dto.bonus(),
                dto.eventPoints(),
                dto.pointsPerGame(),
                dto.valueSeason(),

                dto.form(),
                dto.formRankType(),
                dto.valueForm(),

                dto.goalsScored(),
                dto.assists(),
                dto.expectedGoals(),
                dto.expectedAssists(),
                dto.expectedGoalInvolvements(),

                dto.starts(),
                dto.minutes(),

                dto.defensiveContribution(),
                dto.defensiveContributionPer90(),
                dto.cleanSheets(),

                dto.penaltiesOrder(),
                dto.directFreekicksOrder(),
                dto.cornersAndIndirectFreekicksOrder(),

                dto.nowCost(),
                dto.selectedByPercent(),
                dto.chanceSafe(),
                dto.news()
        );
    }
}
