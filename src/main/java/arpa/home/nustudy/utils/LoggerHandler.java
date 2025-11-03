package arpa.home.nustudy.utils;

import java.io.UnsupportedEncodingException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LoggerHandler {
    private static final Logger LOGGER = Logger.getLogger("NUStudy");
    private static final LogManager LOG_MANAGER = LogManager.getLogManager();
    private static final ConsoleHandler CONSOLE_HANDLER = new ConsoleHandler();

    static {
        LOG_MANAGER.addLogger(LOGGER);

        CONSOLE_HANDLER.setLevel(Level.OFF);

        try {
            CONSOLE_HANDLER.setEncoding("UTF-8");
        } catch (final UnsupportedEncodingException ignored) {
            // Ignore since UTF-8 is a valid encoding
        }

        LOGGER.setLevel(Level.OFF);
        LOGGER.addHandler(CONSOLE_HANDLER);
        LOGGER.setUseParentHandlers(false);
    }

    public static <T> Logger getLogger(final Class<T> logClass) {
        final Logger newLogger = Logger.getLogger(logClass.getName());

        LOG_MANAGER.addLogger(newLogger);

        newLogger.setLevel(Level.OFF);
        newLogger.addHandler(CONSOLE_HANDLER);
        newLogger.setUseParentHandlers(false);

        return newLogger;
    }
}
