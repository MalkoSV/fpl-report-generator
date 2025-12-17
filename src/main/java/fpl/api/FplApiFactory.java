package fpl.api;

public class FplApiFactory {

    public static FplApiClient createJackson() {
        return new FplApiClient(new JacksonHttpExecutor());
    }
}
