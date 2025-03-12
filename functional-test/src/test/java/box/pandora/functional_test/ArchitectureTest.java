package box.pandora.functional_test;

import box.pandora.common.ArchitectureWatchDog;
import org.junit.jupiter.api.Test;

final class ArchitectureTest {

    @Test
    void checkArchitecture() {
        var watchDog = new ArchitectureWatchDog(getClass().getPackageName());
        watchDog.checkGeneralCodingRules();
        watchDog.checkCommonRules();
    }

}
