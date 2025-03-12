package box.pandora.common.config;

import org.apache.commons.configuration2.Configuration;

public final class AllureConfig {

    private static final Configuration CONFIGURATION = ConfigurationProvider.INSTANCE.getConfiguration();

    private AllureConfig() {
    }

    public static int maximumToStringLength() {
        return CONFIGURATION.getInt("allure.maximum-to-string-length");
    }

}
