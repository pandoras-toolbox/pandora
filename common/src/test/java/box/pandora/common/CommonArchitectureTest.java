package box.pandora.common;

import org.junit.jupiter.api.Test;

final class CommonArchitectureTest {

    @Test
    void checkArchitecture() {
        var watchDog = new ArchitectureWatchDog(getClass().getPackageName());
        watchDog.checkGeneralCodingRules();
        watchDog.checkCommonRules();
    }

}
