package box.pandora.functional_test.allure;

import box.pandora.core.config.AllureConfig;
import box.pandora.core.config.LoggingConfig;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Parameter;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import io.qameta.allure.util.ResultsUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

// Utility to provide a very flexible way to mark statements as a test step.
// Prefer however to use the @Step annotation whenever possible.
public final class DynamicStep {

    private static final Logger LOGGER = LogManager.getLogger();

    private DynamicStep() {
    }

    // "Marker" step.
    public static void step(String name) {
        step(name, new ArrayList<>(), () -> {
        });
    }

    public static void step(String name, Runnable runnable) {
        step(name, new ArrayList<>(), runnable);
    }

    public static <T> T step(String name, Supplier<T> supplier) {
        return step(name, new ArrayList<>(), supplier);
    }

    // Update DynamicStepLoggingAspect if you change this method's signature!
    public static <T> T step(String name, List<Parameter> parameters, Supplier<T> supplier) {
        LOGGER.info("{} {}", name, asString(parameters));
        var uuid = startStep(name, parameters);
        T result;
        try {
            result = supplier.get();
            updateStep(uuid);
        } catch (RuntimeException e) {
            handleException(uuid, e);
            throw e;
        } finally {
            Allure.getLifecycle().stopStep(uuid);
        }
        return result;
    }

    private static String startStep(String name, List<Parameter> parameters) {
        var uuid = UUID.randomUUID().toString();
        Allure.getLifecycle()
                .startStep(uuid, new StepResult()
                        .setName(name)
                        .setParameters(parameters));
        return uuid;
    }

    private static void updateStep(String uuid) {
        Allure.getLifecycle().updateStep(uuid, stepResult -> stepResult
                .setStatus(Status.PASSED));
    }

    private static void handleException(String uuid, Exception exception) {
        Allure.getLifecycle().updateStep(uuid, stepResult -> stepResult
                .setStatus(ResultsUtils.getStatus(exception).orElse(Status.BROKEN))
                .setStatusDetails(ResultsUtils.getStatusDetails(exception).orElse(null)));
    }

    // Update DynamicStepLoggingAspect if you change this method's signature!
    public static void step(String name, List<Parameter> parameters, Runnable runnable) {
        LOGGER.info("{} {}", name, asString(parameters));
        var uuid = startStep(name, parameters);
        try {
            runnable.run();
            updateStep(uuid);
        } catch (RuntimeException e) {
            handleException(uuid, e);
            throw e;
        } finally {
            Allure.getLifecycle().stopStep(uuid);
        }
    }

    private static String asString(List<Parameter> parameters) {
        var stringBuilder = new StringBuilder();
        for (var i = 0; i < parameters.size(); i++) {
            var parameter = parameters.get(i);
            if (i == 0) {
                stringBuilder.append(" (");
            }
            stringBuilder.append("%s=%s".formatted(parameter.getName(),
                    StringUtils.abbreviate(parameter.getValue(),
                            LoggingConfig.abbreviationMarker(),
                            AllureConfig.maximumToStringLength())));
            if (i + 1 < parameters.size()) {
                stringBuilder.append(",");
            } else {
                stringBuilder.append(")");
            }
        }
        return stringBuilder.toString();
    }

}
