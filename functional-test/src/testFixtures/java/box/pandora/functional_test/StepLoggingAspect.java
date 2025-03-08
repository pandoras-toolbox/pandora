package box.pandora.functional_test;

import box.pandora.core.config.AllureConfig;
import box.pandora.core.config.LoggingConfig;
import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public final class StepLoggingAspect {

    private static final Logger LOGGER = LogManager.getLogger();

    @Before("execution(* *(..)) && @annotation(io.qameta.allure.Step)")
    // Prevent SonarQube warning: "private" and "final" methods that don't access instance data should be "static"
    @SuppressWarnings("java:S2325")
    public void logStep(JoinPoint joinPoint) {
        if (joinPoint.getSignature() instanceof MethodSignature methodSignature) {
            var method = methodSignature.getMethod();
            var annotation = method.getAnnotation(Step.class);
            LOGGER.info("{} {}",
                    createStepName(annotation.value(), method.getName()), createParameters(joinPoint.getSignature(), joinPoint.getArgs()));
        }
    }

    private static String createStepName(String stepName, String methodName) {
        String name;
        if (StringUtils.isEmpty(stepName)) {
            name = methodName;
        } else {
            name = stepName;
        }
        return name;
    }

    private static String createParameters(Signature signature, Object[] args) {
        var stringBuilder = new StringBuilder();
        if (signature instanceof CodeSignature codeSignature) {
            var parameterNames = codeSignature.getParameterNames();
            for (var i = 0; i < parameterNames.length; i++) {
                if (i == 0) {
                    stringBuilder.append("(");
                }
                stringBuilder.append("%s=%s".formatted(parameterNames[i],
                        args[i] == null ? args[i]
                                : StringUtils.abbreviate(args[i].toString(),
                                LoggingConfig.abbreviationMarker(),
                                AllureConfig.maximumToStringLength())));
                if (i + 1 < parameterNames.length) {
                    stringBuilder.append(",");
                } else {
                    stringBuilder.append(")");
                }
            }
        }
        return stringBuilder.toString();
    }

}
