package fpl.mals;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import static org.apache.commons.collections4.ListUtils.partition;

public class Utils {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Logger logger = Logger.getLogger(Utils.class.getName());

    private static final String BASE_URL = "https://fantasy.premierleague.com";
    private static final String BASE_OVERALL_LEAGUE_PATH = "/leagues/314/standings/c";
    private static final String RECORD_LINK_SELECTOR = "a._1jqkqxq4";

    private static final String ALL_PLAYERS_SELECTOR = "._2j6lqn7";
    private static final String FOR_100PC_PLAYER_SELECTOR = "._174gkcl5";
    private static final String FOR_75PC_PLAYER_SELECTOR = "._174gkcl4";
    private static final String FOR_50PC_PLAYER_SELECTOR = "._174gkcl3";
    private static final String FOR_25PC_PLAYER_SELECTOR = "._174gkcl2";
    private static final String FOR_0PC_PLAYER_SELECTOR = "._174gkcl1";
    private static final String GOALKEAPER_SELECTOR = "._1k6tww12 ._2j6lqn6";
    private static final String DEFENDER_SELECTOR = "._1k6tww13 ._2j6lqn6";
    private static final String MIDFIELDER_SELECTOR = "._1k6tww14 ._2j6lqn6";
    private static final String OFFENDER_SELECTOR = "._1k6tww15 ._2j6lqn6";
    private static final String START_SQUAD_SELECTOR = String.join(", ",
            GOALKEAPER_SELECTOR,
            DEFENDER_SELECTOR,
            MIDFIELDER_SELECTOR,
            OFFENDER_SELECTOR
    );
    private static final String BENCH_SELECTOR = ".tczxyc5";
    private static final String NAME_SELECTOR = "._174gkcl0";
    private static final String CAPTAIN_ICON_SELECTOR = "svg[aria-label='Captain']";
    private static final String GW_SCORE_SELECTOR = "._63rl0j3._63rl0j0 span:nth-of-type(2)";

    private static final String RESET = "\u001B[0m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String BOLD = "\u001B[1m";

    private static final String DESCRIPTION_FOR_ENTER_PAGE_NUMBER = CYAN + """
            ===================================================
             ⚽ FPL SCRAPER
            ===================================================
            """ + RESET + """                         
            Every standings page displays names of 50 teams.
            
            1 - 1-50 positions
            2 - 1-100 positions
            3 - 1-150 positions
             ...
            """ + CYAN + """
            ===================================================
            """ + RESET + BOLD + """
            Enter the number of pages to parse (0 - exit):\s""" + RESET;

    private static final String DESCRIPTION_FOR_CHOOSE_PLAYER_SELECTOR = CYAN + """
            =======================================================
                                  PLAYERS FILTER
            =======================================================
            """ + RESET +
            GREEN + " 1 " + RESET + "- ALL players\n" +
            GREEN + " 2 " + RESET + "- START SQUAD\n" +
            GREEN + " 3 " + RESET + "- CAPTAIN\n" +
            GREEN + " 4 " + RESET + "- BENCH\n" +
            GREEN + " 5 " + RESET + "- Doubtful, unlikely or unavailable to play (0-50%)\n" +
            CYAN + "=======================================================\n" + RESET +
            BOLD + "Choose a filter: " + RESET;

    public static String getFullUrl(String urlEnd) {
        return BASE_URL + urlEnd;
    }

    public static String getStandingsPageUrl(int pageNumber) {
        return getFullUrl(BASE_OVERALL_LEAGUE_PATH + "?page_standings=" + pageNumber);
    }

    public static String filterSelectorByChild(String selector, String child) {
        return ":is(%s):has(%s)".formatted(selector, child);
    }

    public static String getSelectorChild(String selector, String child) {
        return ":is(%s) %s".formatted(selector, child);
    }

    public static void terminateProgramIfNeeded(int pageNumber) throws InterruptedException {
        if (pageNumber == 0) {
            logger.info("❌ Your choice - program terminated. Good luck!");
            Thread.sleep(3000);
            System.exit(0);
        }
    }

    public static int getEnteredNumber(String description, int min, int max) {
        int result;
        while (true) {
            System.out.print(description);

            if (scanner.hasNextInt()) {
                result = scanner.nextInt();
                scanner.nextLine();
                if (result >= min && result <= max) {
                    System.out.println();
                    break;
                } else {
                    System.out.printf("⚠️ Error: the number must be between %d and %d%n", min, max);
                }
            } else {
                System.out.println("⚠️ Error: a number is required!");
                scanner.nextLine();
            }
            System.out.println();
        }
        return result;
    }

    public static int getEnteredPageCount() {
        return getEnteredNumber(DESCRIPTION_FOR_ENTER_PAGE_NUMBER, 0, 20);
    }

    public static String getPlayerSelector() {
        int filterType = getEnteredNumber(DESCRIPTION_FOR_CHOOSE_PLAYER_SELECTOR, 1, 5);

        return switch (filterType) {
            case 1 -> {
                System.out.println("""
                        ✅ Your choice - All players!
                        ℹ️  Includes all players regardless of status.
                        """);
                yield ALL_PLAYERS_SELECTOR;
            }
            case 2 -> {
                System.out.println("""
                        ✅ Your choice - START SQUAD!
                        ℹ️  Collect 11 players from start squad.
                        """);
                yield START_SQUAD_SELECTOR;
            }
            case 3 -> {
                System.out.println("""
                        ✅ Your choice - CAPTAIN!
                        ℹ️  Collect players with CAPTAIN role.
                        """);
                yield filterSelectorByChild(ALL_PLAYERS_SELECTOR, CAPTAIN_ICON_SELECTOR);
            }
            case 4 -> {
                System.out.println("""
                        ✅ Your choice - BENCH!
                        ℹ️  Collect 4 players from bench.
                        """);
                yield BENCH_SELECTOR;
            }
            case 5 -> {
                System.out.println("""
                        ✅ Your choice - Doubtful, unlikely or unavailable to play (0-50%)!
                        ℹ️  Questionable or unavailable players.
                        """);
                yield String.join(", ",
                        filterSelectorByChild(ALL_PLAYERS_SELECTOR, FOR_50PC_PLAYER_SELECTOR),
                        filterSelectorByChild(ALL_PLAYERS_SELECTOR, FOR_25PC_PLAYER_SELECTOR),
                        filterSelectorByChild(ALL_PLAYERS_SELECTOR, FOR_0PC_PLAYER_SELECTOR)
                );
            }
            default -> throw new IllegalArgumentException("Unknown filter type: " + filterType);
        };
    }

    public static List<String> getAllTeamLinks(int pageCount) {
        return IntStream.rangeClosed(1, pageCount)
                .mapToObj(Utils::getStandingsPageUrl)
                .map(Utils::getTeamLinks)
                .flatMap(Collection::stream)
                .toList();
    }

    public static List<String> getTeamLinks(String url) {
        try (Playwright playwright = Playwright.create();
             Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
             BrowserContext context = browser.newContext();
             Page page = context.newPage())
        {
            page.navigate(url);
            Locator links = page.locator(RECORD_LINK_SELECTOR);
            links.first().waitFor();

            return links.all().stream()
                    .map(el -> getFullUrl(el.getAttribute("href")))
                    .toList();
        }
    }

    public static Map<String, Integer> collectPlayers(List<String> teamLinks, String playerSelector, String absentPlayer) {
        System.out.println("🚀 Running in multi-threaded mode by Browser pool...");
        Map<String, Integer> players = new ConcurrentHashMap<>();
        AtomicInteger counter = new AtomicInteger(0);
        int total = teamLinks.size();
        String playerNameSelector = getSelectorChild(playerSelector, NAME_SELECTOR);

        int browserCount = Math.min(5, Runtime.getRuntime().availableProcessors());
        logger.info("⏱️ Using " + browserCount + " browser threads!");

        ExecutorService executorServicePool = Executors.newFixedThreadPool(browserCount);
        List<List<String>> partitions = partition(teamLinks, browserCount);
        List<CompletableFuture<Void>> tasks = new ArrayList<>();

        for (var teamSublist : partitions) {
            CompletableFuture<Void> task = CompletableFuture.runAsync(() -> {
                try (Playwright playwright = Playwright.create();
                     Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
                     BrowserContext context = browser.newContext())
                {
                    Page page = context.newPage();
                    for (String link : teamSublist) {
                        try {
                            page.navigate(link);
                            page.locator(NAME_SELECTOR).last().waitFor();

                            Locator player = page.locator(playerNameSelector);
                            List<Locator> teamPlayers = player.all();

                            boolean hasPlayer = absentPlayer == null;

                            for (Locator el : teamPlayers) {
                                String name = el.innerText().trim();
                                players.merge(name, 1, Integer::sum);

                                if (!hasPlayer && name.equalsIgnoreCase(absentPlayer)) {
                                    hasPlayer = true;
                                }
                            }

                            int done = counter.incrementAndGet();
                            System.out.printf("✅ %d players, [%d/%d] %s%n", teamPlayers.size(), done, total, link);

                            if (!hasPlayer) {
                                System.out.printf("❌ %s is absent in this team%n", absentPlayer);
                            }
                        } catch (Exception e) {
                            logger.warning("⚠️ Error on " + link + ": " + e.getMessage());
                        }
                    }
                } catch (Exception e) {
                    logger.severe("❌ Browser cluster thread failed: " + e.getMessage());
                }
            }, executorServicePool).exceptionally(e -> {
                logger.severe("❌ Exception in browser thread: " + e.getMessage());
                return null;
            });

            tasks.add(task);
        }

        CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0])).join();
        executorServicePool.shutdown();
        try {
            if (!executorServicePool.awaitTermination(1, TimeUnit.MINUTES)) {
                executorServicePool.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorServicePool.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.printf("📊 Found %d unique players%n", players.size());

        return players;
    }

    public static List<Player> collectStats(List<String> teamLinks, String playerSelector) {
        System.out.println("🚀 Running in multi-threaded mode by Browser pool...");

        AtomicInteger counter = new AtomicInteger(0);
        int total = teamLinks.size();

        int browserCount = Math.min(5, Runtime.getRuntime().availableProcessors());
        logger.info("⏱️ Using " + browserCount + " browser threads!");

        ExecutorService executorServicePool = Executors.newFixedThreadPool(browserCount);
        List<List<String>> partitions = partition(teamLinks, browserCount);

        List<CompletableFuture<List<Player>>> tasks = new ArrayList<>();

        for (var teamSublist : partitions) {
            CompletableFuture<List<Player>> task = CompletableFuture.supplyAsync(() -> {
                List<Player> localList = new ArrayList<>();

                try (Playwright playwright = Playwright.create();
                     Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
                     BrowserContext context = browser.newContext())
                {
                    Page page = context.newPage();
                    for (String link : teamSublist) {
                        try {
                            page.navigate(link);
                            page.locator(NAME_SELECTOR).last().waitFor();

                            Locator player = page.locator(playerSelector);
                            List<Locator> teamPlayers = player.all();

                            for (Locator el : teamPlayers) {
                                String name = el.locator(NAME_SELECTOR).innerText().trim();
                                boolean hasCaptain = el.locator(CAPTAIN_ICON_SELECTOR).count() > 0;
                                boolean hasStart = el.locator(START_SQUAD_SELECTOR).count() > 0;
                                int score = Integer.parseInt(el.locator(GW_SCORE_SELECTOR).innerText());
                                Player currentPlayer = new Player(name, 1);
                                if (hasStart) {
                                    currentPlayer.setStart(1);
                                }
                                if (hasCaptain) {
                                    currentPlayer.setCaptain(1);
                                }

                                localList.add(currentPlayer);
                            }

                            int done = counter.incrementAndGet();
                            System.out.printf("✅ %d players, [%d/%d] %s%n", teamPlayers.size(), done, total, link);
                        } catch (Exception e) {
                            logger.warning("⚠️ Error on " + link + ": " + e.getMessage());
                        }
                    }
                } catch (Exception e) {
                    logger.severe("❌ Browser cluster thread failed: " + e.getMessage());
                }
                return localList;
            }, executorServicePool);

            tasks.add(task);
        }

        List<Player> allPlayers = tasks.stream()
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .toList();

        executorServicePool.shutdown();
        try {
            executorServicePool.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            executorServicePool.shutdownNow();
        }

        List<Player> mergedPlayers = PlayerUtils.mergePlayers(allPlayers);
        System.out.printf("📊 Found %d unique players%n", mergedPlayers.size());

        return mergedPlayers;
    }
}
