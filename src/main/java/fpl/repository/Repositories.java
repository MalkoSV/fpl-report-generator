package fpl.repository;

import fpl.domain.repository.EntryRepository;
import fpl.domain.repository.LeagueRepository;
import fpl.domain.repository.PlayerRepository;
import fpl.domain.repository.TransferRepository;

public record Repositories(
        PlayerRepository players,
        EntryRepository entry,
        LeagueRepository league,
        TransferRepository transfers
) {}
