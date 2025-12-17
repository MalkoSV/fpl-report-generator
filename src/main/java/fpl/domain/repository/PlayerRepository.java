package fpl.domain.repository;

import fpl.domain.model.PlayerSeasonView;

import java.util.List;

public interface PlayerRepository {

    PlayerSeasonView getById(int id);
    List<PlayerSeasonView> all();
}
