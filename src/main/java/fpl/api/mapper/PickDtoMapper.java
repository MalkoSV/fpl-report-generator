package fpl.api.mapper;

import fpl.api.dto.PickDto;
import fpl.domain.model.Pick;

public class PickDtoMapper {

    public static Pick fromDto(PickDto dto) {
        return new Pick(
                dto.element(),
                dto.multiplier(),
                dto.isViceCaptain()
        );
    }

}
