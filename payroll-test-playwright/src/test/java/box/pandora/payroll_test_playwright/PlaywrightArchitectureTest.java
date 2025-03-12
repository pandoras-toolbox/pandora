package box.pandora.payroll_test_playwright;

import box.pandora.common.ArchitectureWatchDog;
import org.junit.jupiter.api.Test;

final class PlaywrightArchitectureTest {

    @Test
    void checkArchitecture() {
        var watchDog = new ArchitectureWatchDog(getClass().getPackageName());
        watchDog.checkGeneralCodingRules();
        watchDog.checkCommonRules();
    }

}
