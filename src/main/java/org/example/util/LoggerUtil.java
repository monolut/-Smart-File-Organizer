package org.example.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class LoggerUtil {

    private static final Logger logger =
            LoggerFactory.getLogger(LoggerUtil.class);

    public static void fileMoved(Path source, Path destination) {

        logger.info("Moved: {} -> {}", source, destination);

    }

    public static void error(Exception e) {

        logger.error(e.getMessage(), e);

    }

}