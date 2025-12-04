package fpl.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EntryHistory(
        int event,
        int points,
        int eventTransfers,
        int eventTransfersCost
) {}
