package box.pandora.functional_test;

import box.pandora.common.ArchitectureWatchDog;
import box.pandora.common.CommonTag;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(CommonTag.ARCHITECTURE)
final class ArchitectureTest {

    @Test
    void check() {
        var watchDog = new ArchitectureWatchDog(getClass().getPackageName());
        watchDog.checkGeneralCodingRules();
        watchDog.checkCommonRules();
    }

}
