package fpl.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BootstrapResponse(
        List<PlayerDto> elements,
        List<EventDto> events
) {}
