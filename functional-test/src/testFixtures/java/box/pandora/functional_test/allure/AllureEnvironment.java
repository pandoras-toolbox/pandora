package box.pandora.functional_test.allure;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

// Needs to be an enum because of thread-safety when executing tests in parallel.
// Prevent SonarQube warning: Classes and enums with private members should have a constructor
@SuppressWarnings("java:S1258")
public enum AllureEnvironment {

    INSTANCE;

    private static final Logger LOGGER = LogManager.getLogger();

    private boolean created;

    public synchronized void createEnvironmentXml() {
        createEnvironmentXml(Map.of());
    }

    public synchronized void createEnvironmentXml(Map<String, String> moduleSpecificProperties) {
        if (created) {
            LOGGER.debug("Not creating Allure environments XML file because it has already been created");
        } else {
            LOGGER.debug("Creating Allure environments XML file for the Allure report");
            var mapBuilder = ImmutableMap.<String, String>builder()
                    .putAll(moduleSpecificProperties);
            var includedTags = System.getProperty("includedTags");
            if (!StringUtils.isEmpty(includedTags)) {
                mapBuilder.put("Included Tags", includedTags);
            }
            var excludedTags = System.getProperty("excludedTags");
            if (!StringUtils.isEmpty(excludedTags)) {
                mapBuilder.put("Excluded Tags", excludedTags);
            }
            EnvironmentWriter.writeAllureEnvironmentXml(mapBuilder.build());
            created = true;
        }
    }

}
