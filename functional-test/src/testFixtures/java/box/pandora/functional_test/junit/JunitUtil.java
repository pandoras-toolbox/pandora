package box.pandora.functional_test.junit;

import org.junit.jupiter.api.extension.ExtensionContext;

public final class JunitUtil {

    private JunitUtil() {
    }

    public static String createTestName(ExtensionContext context) {
        // If it is a parametrized test then we should consider it for the name.
        var name = context.getRequiredTestClass().getSimpleName() + ".";
        var displayName = context.getDisplayName();
        var testMethodName = context.getRequiredTestMethod().getName() + "()";
        if (displayName.equals(testMethodName)) {
            name += displayName;
        } else {
            name += testMethodName + " '" + displayName + "'";
        }
        return name;
    }

}
