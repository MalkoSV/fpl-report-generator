package fpl.context;

import fpl.api.FplApiClient;
import fpl.api.dto.BootstrapResponse;
import fpl.api.dto.PlayerDto;
import fpl.api.mapper.PlayerDtoMapper;
import fpl.domain.model.PlayerSeasonView;
import fpl.domain.repository.EventRepository;
import fpl.domain.repository.PlayerRepository;
import fpl.repository.BootstrapEventRepository;
import fpl.repository.BootstrapPlayerRepository;

import java.util.Map;
import java.util.stream.Collectors;

public class BootstrapContext {

    private final PlayerRepository players;
    private final EventRepository events;

    public BootstrapContext(FplApiClient api) {
        BootstrapResponse response = api.getBootstrap();

        Map<Integer, PlayerSeasonView> playersById = mapPlayers(response);

        this.players = new BootstrapPlayerRepository(playersById);
        this.events = new BootstrapEventRepository(response.events());
    }

    public PlayerRepository players() {
        return players;
    }

    public EventRepository events() {
        return events;
    }

    private Map<Integer, PlayerSeasonView> mapPlayers(BootstrapResponse response) {
        return response.elements().stream()
                .collect(Collectors.toMap(
                        PlayerDto::id,
                        PlayerDtoMapper::fromDto
                ));
    }
}
