package fpl.domain.model;

public record EventView(
        int id,
        boolean isCurrent,
        boolean isPrevious
) {}
