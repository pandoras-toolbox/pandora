package box.pandora.core.config;

import org.apache.commons.configuration2.Configuration;

public final class OkHttpConfig {

    private static final Configuration CONFIGURATION = ConfigurationProvider.INSTANCE.getConfiguration();

    private OkHttpConfig() {
    }

    public static int payloadMaximumLengthRequest() {
        return CONFIGURATION.getInt("okhttp.payload-maximum-length.request");
    }

    public static int payloadMaximumLengthResponse() {
        return CONFIGURATION.getInt("okhttp.payload-maximum-length.response");
    }

}
