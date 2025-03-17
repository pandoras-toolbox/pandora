package box.pandora.payroll_test_playwright;

import box.pandora.common.ArchitectureWatchDog;
import box.pandora.common.CommonTag;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(CommonTag.ARCHITECTURE)
final class ArchitectureTest {

    @Test
    void checkArchitecture() {
        var watchDog = new ArchitectureWatchDog(getClass().getPackageName());
        watchDog.checkGeneralCodingRules();
        watchDog.checkCommonRules();
    }

}
