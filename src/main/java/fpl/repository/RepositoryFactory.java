package fpl.repository;

import fpl.api.FplApiClient;
import fpl.context.BootstrapContext;

public final class RepositoryFactory {

    private RepositoryFactory() {}

    public static Repositories create(
            FplApiClient api,
            BootstrapContext bootstrapContext
    ) {
        return new Repositories(
                bootstrapContext.players(),
                new ApiEntryRepository(api),
                new ApiLeagueRepository(api),
                new ApiTransferRepository(api)
        );
    }
}
