package box.pandora.payroll_test_okhttp;

import box.pandora.common.ArchitectureWatchDog;
import org.junit.jupiter.api.Test;

final class OkHttpArchitectureTest {

    @Test
    void checkArchitecture() {
        var watchDog = new ArchitectureWatchDog(getClass().getPackageName());
        watchDog.checkGeneralCodingRules();
        watchDog.checkCommonRules();
    }

}
