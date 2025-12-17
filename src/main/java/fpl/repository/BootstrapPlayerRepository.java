package fpl.repository;

import fpl.domain.model.PlayerSeasonView;
import fpl.domain.repository.PlayerRepository;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class BootstrapPlayerRepository implements PlayerRepository {

    private final Map<Integer, PlayerSeasonView> playersById;

    public BootstrapPlayerRepository(Map<Integer, PlayerSeasonView> playersById) {
        this.playersById = Map.copyOf(playersById);
    }

    @Override
    public PlayerSeasonView getById(int id) {
        PlayerSeasonView playerView = playersById.get(id);
        if (playerView == null) {
            throw new NoSuchElementException("Player not found: " + id);
        }
        return playerView;
    }

    @Override
    public List<PlayerSeasonView> all() {
        return playersById.values()
                .stream()
                .toList();
    }
}
