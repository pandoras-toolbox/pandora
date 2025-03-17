package box.pandora.payroll_test_playwright;

import box.pandora.functional_test.allure.AllureEnvironment;
import box.pandora.functional_test.junit.BaseCallback;
import com.google.common.collect.ImmutableMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Map;

public final class PlaywrightTestCallback extends BaseCallback implements BeforeTestExecutionCallback {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        AllureEnvironment.INSTANCE.createEnvironmentXml(buildModuleSpecificProperties());
    }

    private static Map<String, String> buildModuleSpecificProperties() {
        LOGGER.debug("Building module specific properties for Allure");
        return ImmutableMap.<String, String>builder()
                .put("Stand", "Local")
                .build();
    }

}
