package fpl.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PickDto(
        int element,
        int position,
        int multiplier,
        boolean isCaptain,
        boolean isViceCaptain,
        int elementType
) {}
