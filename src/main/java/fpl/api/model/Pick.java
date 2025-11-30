package fpl.api.model;

public record Pick(
       int element,
       int position,
       int multiplier,
       boolean isCaptain,
       boolean isViceCaptain,
       int elementType
) {
}
