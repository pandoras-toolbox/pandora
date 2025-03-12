package box.pandora.common.config;

import org.apache.commons.configuration2.Configuration;

public final class LoggingConfig {

    private static final Configuration CONFIGURATION = ConfigurationProvider.INSTANCE.getConfiguration();

    private LoggingConfig() {
    }

    public static String abbreviationMarker() {
        return CONFIGURATION.getString("logging.abbreviation-marker");
    }

    public static int maximumLengthRequest() {
        return CONFIGURATION.getInt("logging.maximum-length.request");
    }

    public static int maximumLengthResponse() {
        return CONFIGURATION.getInt("logging.maximum-length.response");
    }

    public static String maskedVariableText() {
        return CONFIGURATION.getString("logging.masked-variable-text");
    }

}
