package box.pandora.functional_test.junit;

import box.pandora.core.ThreadLocalsRegistry;
import box.pandora.functional_test.BaseUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class BaseCallback implements BeforeEachCallback, AfterEachCallback {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map<Long, Instant> TEST_START_TIME_MAP = new ConcurrentHashMap<>();

    @Override
    public void beforeEach(ExtensionContext context) {
        LOGGER.trace("Storing start time of test method [{}]",
                context.getRequiredTestMethod().getName());
        TEST_START_TIME_MAP.put(Thread.currentThread().threadId(), Instant.now());
        // https://www.baeldung.com/mdc-in-log4j-2-logback#mdc-in-log4j2
        ThreadContext.put("testName", "%s#%s - "
                .formatted(context.getRequiredTestClass().getSimpleName(), context.getDisplayName()));
    }

    @Override
    public void afterEach(ExtensionContext context) {
        LOGGER.info("Test '{}' took {}",
                JunitUtil.createTestName(context),
                BaseUtil.formatDurationWords(Duration.ofMillis(System.currentTimeMillis() - getTestStartTime().orElseThrow().toEpochMilli())));
        ThreadLocalsRegistry.remove();
    }

    public static Optional<Instant> getTestStartTime() {
        return Optional.ofNullable(TEST_START_TIME_MAP.get(Thread.currentThread().threadId()));
    }

}
