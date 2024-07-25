package io.github.sakurawald.util;

import io.github.sakurawald.Fuji;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

@UtilityClass
public class LogUtil {

    private static final Logger LOGGER = createLogger(StringUtils.capitalize(Fuji.MOD_ID));

    public static Logger getDefaultLogger() {
        return LOGGER;
    }

    public static void debug(String message, Object... args) {
        LOGGER.debug(message, args);
    }

    public static void info(String message, Object... args) {
        LOGGER.info(message,args);
    }

    public static void warn(String message, Object... args) {
        LOGGER.warn(message,args);
    }

    public static void error(String message, Object... args) {
        LOGGER.error(message,args);
    }

    public static Logger createLogger(String name) {
        Logger logger = LogManager.getLogger(name);
        try {
            String level = System.getProperty("%s.level".formatted(Fuji.MOD_ID));
            Configurator.setLevel(logger, Level.getLevel(level));
        } catch (Exception e) {
            return logger;
        }
        return logger;
    }

    public static void cryLoudly(String message, Exception e) {
        LOGGER.error(message, e);
    }

}
