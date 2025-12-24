package fpl.ui.console;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;

public class OutputArgumentParser {

    public Optional<File> parse(String[] args) {
        return Arrays.stream(args)
                .filter(arg -> arg.startsWith("/output=") || arg.startsWith("--output="))
                .map(arg -> arg.substring(arg.indexOf('=') + 1).trim())
                .map(File::new)
                .findFirst();
    }
}
