package fpl.api.mapper;

import fpl.api.dto.PickDto;
import fpl.domain.model.Pick;
import fpl.domain.model.PositionType;

public class PickDtoMapper {

    public static PositionType toPositionType(PickDto pick) {
        return PositionType.fromCode(pick.elementType());
    }

    public static boolean isBench(PickDto pick) {
        return pick.multiplier() == 0;
    }

    public static Pick fromDto(PickDto dto) {
        return new Pick(
                dto.element(),
                dto.multiplier(),
                dto.isViceCaptain()
        );
    }

}
