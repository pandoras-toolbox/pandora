package box.pandora;

import java.io.IOException;
import java.util.Properties;

// Prevent SonarQube warning: The Singleton design pattern should be used with care
@SuppressWarnings("java:S6548")
public enum ParallelExecutionConfig {

    INSTANCE;

    private Properties properties;

    public String enabled() {
        return loadProperties().getProperty("junit.jupiter.execution.parallel.enabled");
    }

    public String modeClassesDefault() {
        return loadProperties().getProperty("junit.jupiter.execution.parallel.mode.classes.default");
    }

    public String modeDefault() {
        return loadProperties().getProperty("junit.jupiter.execution.parallel.mode.default");
    }

    public String configStrategy() {
        return loadProperties().getProperty("junit.jupiter.execution.parallel.config.strategy");
    }

    public String configFixedParallelism() {
        return loadProperties().getProperty("junit.jupiter.execution.parallel.config.fixed.parallelism");
    }

    private Properties loadProperties() {
        if (properties == null) {
            properties = new Properties();
            var defaultPropFileName = "parallel.properties";
            var privatePropsFileName = "parallel.%s.properties".formatted(System.getProperty("user.name"));
            load(defaultPropFileName, properties);
            load(privatePropsFileName, properties);
            if (properties.isEmpty()) {
                throw new IllegalStateException("Error loading properties from resource '%s' or '%s'"
                        .formatted(defaultPropFileName, privatePropsFileName));
            }
        }
        return properties;
    }

    private static void load(String resourceName, Properties properties) {
        try {
            var resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
            if (resourceAsStream != null) {
                properties.load(resourceAsStream);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Error loading properties from resource '%s'".formatted(resourceName), e);
        }
    }

}
