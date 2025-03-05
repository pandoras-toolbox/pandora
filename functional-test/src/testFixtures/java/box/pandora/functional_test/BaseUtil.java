package box.pandora.functional_test;

import org.apache.commons.lang3.time.DurationFormatUtils;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public final class BaseUtil {

    private BaseUtil() {
    }

    public static String formatDurationWords(Duration duration) {
        var durationWords = DurationFormatUtils.formatDurationWords(duration.toMillis(), true, true);
        var nanos = duration.get(ChronoUnit.NANOS);
        String result;
        if (nanos == 0) {
            result = durationWords;
        } else {
            // Needed because the 3rd party utility method does not include words for millis.
            if (duration.get(ChronoUnit.SECONDS) == 0) {
                result = "%s millis".formatted(nanos / 1_000_000);
            } else {
                result = "%s and %s millis".formatted(durationWords, nanos / 1_000_000);
            }
        }
        return result;
    }

}
