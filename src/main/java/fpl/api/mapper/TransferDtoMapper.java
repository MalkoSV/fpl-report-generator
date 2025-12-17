package fpl.api.mapper;

import fpl.api.dto.TransferDto;
import fpl.domain.model.Team;
import fpl.domain.repository.PlayerRepository;
import fpl.domain.transfers.Transfer;

import java.util.Map;

public class TransferDtoMapper {

    private TransferDtoMapper() {}

    public static Transfer fromDto(
            TransferDto dto,
            PlayerRepository players,
            Map<Integer, Team> teamsByEntry
    ) {
        String inName = players.getById(dto.elementIn()).fullName();
        String outName = players.getById(dto.elementOut()).fullName();

        Team team = teamsByEntry.get(dto.entry());

        return new Transfer(
                inName,
                outName,
                team.wildCard() > 0,
                team.freeHit() > 0
        );
    }
}
