package fpl.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EntryResponse(
        String activeChip,
        EntryHistory entryHistory,
        List<Pick> picks
) {}
