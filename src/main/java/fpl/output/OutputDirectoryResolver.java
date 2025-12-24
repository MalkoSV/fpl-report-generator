package fpl.output;

import java.io.File;

public class OutputDirectoryResolver {

    public File resolve(File baseDir) {
        if (baseDir.exists() && !baseDir.isDirectory()) {
            throw new RuntimeException("Output path is not a directory: " + baseDir.getAbsolutePath()
            );
        }

        if (!baseDir.exists() && !baseDir.mkdirs()) {
            throw new RuntimeException("Cannot create output directory: " + baseDir.getAbsolutePath()
            );
        }

        return baseDir;
    }
}
