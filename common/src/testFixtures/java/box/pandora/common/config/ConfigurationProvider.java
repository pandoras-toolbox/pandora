package box.pandora.common.config;

import box.pandora.common.JsonUtil;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.interpol.ConfigurationInterpolator;
import org.apache.commons.configuration2.interpol.Lookup;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.MapContext;
import org.apache.commons.jexl3.introspection.JexlPermissions;
import org.apache.commons.jexl3.introspection.JexlSandbox;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.stream.StreamSupport;

// Needs to be a singleton and thread-safe, thus it is an enum.
// Prevent SonarQube warning: The Singleton design pattern should be used with care
@SuppressWarnings("java:S6548")
public enum ConfigurationProvider {

    INSTANCE;

    private final Configuration configuration;

    ConfigurationProvider() {
        configuration = createConfiguration("config/common.properties");
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    // https://www.baeldung.com/apache-commons-configuration#2-using-expressions
    private Configuration createConfiguration(String propertyFileName) {
        var logger = LogManager.getLogger();
        logger.info("Creating properties from file: {}", propertyFileName);
        Configuration config;
        var prefixLookups = new HashMap<>(ConfigurationInterpolator.getDefaultPrefixLookups());
        prefixLookups.put("expr", new ConfigurationProvider.ExpressionLookup());
        var builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                .configure(new Parameters()
                        .properties()
                        .setPrefixLookups(prefixLookups)
                        .setFileName(propertyFileName));
        try {
            config = builder.getConfiguration();
            logger.log(Level.INFO, "Effective {}:{}{}",
                    () -> propertyFileName, System::lineSeparator, () -> asPrettyJson(config));
        } catch (ConfigurationException e) {
            throw new IllegalStateException("Failed to create configuration from file: %s"
                    .formatted(propertyFileName), e);
        }
        return config;
    }

    private String asPrettyJson(Configuration configuration) {
        Iterable<String> iterable = configuration::getKeys;
        var treeMap = new TreeMap<>();
        StreamSupport.stream(iterable.spliterator(), false)
                .toList()
                .forEach(key -> {
                    var value = configuration.getString(key);
                    if (StringUtils.containsIgnoreCase(key, "password")) {
                        value = LoggingConfig.maskedVariableText();
                    }
                    treeMap.put(key, value);
                });
        return JsonUtil.asPrettyJson(treeMap);
    }

    private static final class ExpressionLookup implements Lookup {

        @Override
        public Object lookup(String variable) {
            try {
                var sandbox = new JexlSandbox(false);
                sandbox.allow(System.class.getName())
                        .execute("getProperty");
                var engine = new JexlBuilder()
                        .permissions(JexlPermissions.UNRESTRICTED)
                        .sandbox(sandbox)
                        .safe(true)
                        .strict(true)
                        .create();
                var script = engine.createScript(variable);
                var context = new MapContext();
                context.set("System", System.class);
                return script.execute(context);
            } catch (JexlException e) {
                throw new IllegalStateException("Failed to lookup variable '%s'"
                        .formatted(variable), e);
            }
        }

    }

}
