package box.pandora.common;

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
